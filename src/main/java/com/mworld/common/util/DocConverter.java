package com.mworld.common.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.mworld.common.Config;
import com.mworld.common.exception.CopyDocException;
import com.mworld.common.exception.MistakeDocTypeException;
import com.mworld.common.exception.OutOfCapacityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.ConnectException;

@Component
public class DocConverter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int port;
    private String swfToolsPath;
    private String docPath;
    private String outPath;
    private String fileName;
    private File docFile;
    private File pdfFile;
    private File swfFile;
    @Autowired
    private Config config;

    public DocConverter() {
        super();
    }

    public DocConverter(String docPath) {
        init(docPath);
    }

    public void init(String docPath) {
        this.docPath = docPath;
        port = config.OPEN_OFFICE_PORT;
        swfToolsPath = config.SWF_TOOL_PATH;
        fileName = docPath.replaceAll("\\\\", "/").substring(docPath.lastIndexOf("/"), docPath.lastIndexOf("."));
        docFile = new File(docPath);
        pdfFile = new File(config.PDF_TMP_PATH + fileName + ".pdf");
        swfFile = new File(config.SWF_FILE_PATH + fileName.substring(fileName.lastIndexOf("/")) + ".swf");
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * @param swfToolsPath SWFTOOLS工具的安装目录
     */
    public void setSwfToolsPath(String swfToolsPath) {
        this.swfToolsPath = swfToolsPath;
    }

    private void docToPdf() throws Exception {
        if (docFile.exists()) {
            String type = docFile.getName().substring(docFile.getName().lastIndexOf(".") + 1);
            if (!"doc".equals(type) && !"docx".equals(type)) {
                throw new MistakeDocTypeException("文件类型异常！文件格式为：" + docFile.getName().substring(docFile.getName().lastIndexOf(".")), new Throwable("目前文档转码只支持.doc格式"));
            }
            if (docFile.length() > 1024 << 12)
                throw new OutOfCapacityException("文件大小超出限制，当前文件大小为：" + docFile.length(), new Throwable("当前最大支持4M文档转换" + (1024 << 12)));
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);

                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                    if ("docx".equals(type)) {
                        File docFileDest = new File(docFile.getAbsolutePath().substring(0, docFile.getAbsolutePath().length() - 1));
                        int copy = FileCopyUtils.copy(docFile, docFileDest);
                        if (copy <=0)
                            throw new CopyDocException("doc转成docx格式文档失败");
                        else {
                            converter.convert(docFileDest, pdfFile);
                            docFileDest.delete();
                        }
                    } else
                        converter.convert(docFile, pdfFile);
                    connection.disconnect();
                    logger.info(docFile.getName() + "成功转换成" + pdfFile.getPath());
                } catch (ConnectException e) {
                    logger.error("OpenOffice 服务未启动", e);
                    throw e;
                } catch (OpenOfficeException e) {
                    logger.error("pdf转换异常，读取转换文件失败", e);
                    throw e;
                }
            } else {
                logger.info(pdfFile.getName() + "已经存在，无需再次转换");
            }
        } else {
            logger.info("doc文件不存在");
        }
    }

    private void pdfToSwf() {
        Runtime r = Runtime.getRuntime();
        if (pdfFile.exists()) {
            String command = "";
            if (config.ENVIRONMENT == 1) {
                if (swfToolsPath.charAt(swfToolsPath.length() - 1) == '/')
                    command = swfToolsPath + "pdf2swf.exe ";
                else
                    command = swfToolsPath + "/pdf2swf.exe ";
            }
            if (config.ENVIRONMENT == 2)
                command = "pdf2swf ";
            try {
                Process process = r.exec(command + pdfFile.getPath() + " -o" + swfFile.getPath() + " -T 9");
                logger.info(loadStream(process.getInputStream()));
                logger.info(loadStream(process.getErrorStream()));
                logger.info("swf文件转换成功");
                if (pdfFile.exists())
                    pdfFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String loadStream(InputStream is) throws IOException {
        int ptr = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while ((ptr = br.read()) != -1) {
            sb.append((char) ptr);
        }
        return sb.toString();
    }

    public String getSwfPath() {
        if (swfFile.exists()) {
            return swfFile.getPath().replaceAll("\\\\", "/");
        }
        return null;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
        if (!StringUtils.isEmpty(outPath)) {
            if (outPath.charAt(outPath.length() - 1) == '/')
                swfFile = new File(outPath + fileName + ".swf");
            else
                swfFile = new File(outPath + "/" + fileName + ".swf");
        }
    }

    public boolean convert() {
        if (swfFile.exists()) {
            logger.info(swfFile.getName() + "已存在");
            return true;
        }
        try {
            docToPdf();
            pdfToSwf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (swfFile.exists()) {
            logger.info(docFile.getName() + "已转换为" + swfFile.getName());
            return true;
        }
        return false;
    }
}
