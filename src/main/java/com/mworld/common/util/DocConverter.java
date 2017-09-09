package com.mworld.common.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.ConnectException;

public class DocConverter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int environment = 1;//1->windows,2->linux
    private static final int DEFAULT_PORT = 8100;
    private static final String DEFAULT_SWFTOOLPATH = "D:/SWFTools/";
    private int port;
    private String swfToolsPath;
    private String docPath;
    private String outPath;
    private String fileName;
    private File docFile;
    private File pdfFile;
    private File swfFile;

    public DocConverter(String docPath) {
        init(docPath);
    }

    public void init(String docPath) {
        this.docPath = docPath;
        port = DEFAULT_PORT;
        swfToolsPath = DEFAULT_SWFTOOLPATH;
        fileName = docPath.replaceAll("\\\\", "/").substring(docPath.lastIndexOf("/"), docPath.lastIndexOf("."));
        docFile = new File(docPath);
        pdfFile = new File(fileName + ".pdf");
        swfFile = new File(fileName + ".swf");
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * @param swfToolsPath SWFTOOLS工具的安装目录
     */
    public void setSwfToolsPath(String swfToolsPath) {
        this.swfToolsPath = swfToolsPath;
    }

    private void docToPdf() throws Exception {
        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);

                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
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
            if (environment == 1) {
                if (swfToolsPath.charAt(swfToolsPath.length() - 1) == '/')
                    command = swfToolsPath + "pdf2swf.exe ";
                else
                    command = swfToolsPath + "/pdf2swf.exe ";
            }
            if (environment == 2)
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
