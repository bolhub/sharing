package com.mworld.common;

import com.alibaba.fastjson.JSON;
import com.mworld.common.exception.NotLoginException;
import com.mworld.login.po.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by bolom on 2017/8/9.
 */
public class BaseController {
    private final static String DEFAULT_CONTEXT_ENCODING = "text/html;charset=UTF-8";
    private String contextEncoding = DEFAULT_CONTEXT_ENCODING;

    public void setContextEncoding(String contextEncoding) {
        contextEncoding = StringUtils.isEmpty(contextEncoding) ? DEFAULT_CONTEXT_ENCODING : contextEncoding;
        this.contextEncoding = contextEncoding;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Config Config;

    public User getLoginUser(HttpServletRequest request) throws NotLoginException {
        Object obj = request.getSession().getAttribute("login_user");
        if (Objects.isNull(obj))
            throw new NotLoginException(NoticeConst.NO_LOGIN_USER);
        return JSON.parseObject(obj.toString(), User.class);
    }

    public void responseMsg(HttpServletResponse response, Message message) {
        response.setContentType(this.contextEncoding);
        logger.info(response.getContentType());
        try {
            response.getWriter().write(JSON.toJSONString(message));
        } catch (Exception e) {
            logger.warn("Response Error", e);
        }
    }

    public void responseMsg(HttpServletResponse response, ValidMsg validMsg) {
        try {
            response.getWriter().write(JSON.toJSONString(validMsg));
        } catch (Exception e) {
            logger.warn("Response Error", e);
        }
    }

    public void uploadOpt(HttpServletRequest request, String dir) {
        String id = "";
        String fileName = "";

        int chunks = 1;
        int chunk = 0;

        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                List<FileItem> fileItems = upload.parseRequest(request);

                FileItem tmpFileItem = null;

                for (FileItem fileItem : fileItems) {
                    if (fileItem.getFieldName().equals("id")) {
                        id = fileItem.getString();
                    } else if (fileItem.getFieldName().equals("name")) {
                        fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    } else if (fileItem.getFieldName().equals("chunks")) {
                        chunks = NumberUtils.toInt(fileItem.toString());
                    } else if (fileItem.getFieldName().equals("chunk")) {
                        chunk = NumberUtils.toInt(fileItem.getString());
                    } else if (fileItem.getFieldName().equals("file")) {
                        tmpFileItem = fileItem;
                    }
                }

                String tmpFileDir = getTempFilePath(dir) + File.separator + id;
                File parentFileDir = new File(tmpFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }

                File tmpPartFile = new File(parentFileDir, fileName + "_" + chunk + ".part");
                FileUtils.copyInputStreamToFile(tmpFileItem.getInputStream(), tmpPartFile);

                boolean uploadDone = true;
                for (int i = 0; i < chunks; i++) {
                    File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
                    if (!partFile.exists())
                        uploadDone = false;
                }

                if (uploadDone) {
                    File destTmpFile = new File(getTempFilePath(dir), fileName);
                    for (int i = 0; i < chunks; i++) {
                        File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
                        FileOutputStream fos = new FileOutputStream(destTmpFile, true);
                        FileUtils.copyFile(partFile, fos);
                        fos.close();
                        if (i == chunks - 1)
                            FileUtils.deleteDirectory(parentFileDir);
                    }
                } else {
                    if (chunk == chunks - 1) {
                        FileUtils.deleteDirectory(parentFileDir);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("ERROR FILE UPLOAD: {}", e);
        }
    }

    public String getTempFilePath(String dir) {
        return Config.FILES_COB + dir;
    }

    public void download(HttpServletResponse response, File file, String downName) {
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                logger.info("mimetype is not detectable, will take default");
                mimeType = "application/octet-stream";
            }
            try {
                response.setContentType(mimeType);
//                    response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", resume.getFileName()));
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(downName, "UTF-8"));
                response.setContentLength((int) file.length());
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(is, response.getOutputStream());
                return;
            } catch (Exception e) {
                logger.info("IO Error", e);
            }
        }
        responseMsg(response, new Message(false, "Something wrong when download the file"));
    }

    public String getDestName(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
    }

    public String getFileType(String fileName) {
        if (fileName.indexOf(".") == -1)
            return null;
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
