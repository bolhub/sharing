package com.mworld.resume.controller;

import com.mworld.common.*;
import com.mworld.common.exception.NotLoginException;
import com.mworld.resume.po.Gender;
import com.mworld.resume.po.Privilege;
import com.mworld.resume.po.Resume;
import com.mworld.resume.service.ResumeService;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tools.ant.taskdefs.Ant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/resume")
public class ResumeController extends BaseController {
    final static String RESUME_DOC_UPLOAD_PATH = "resumeFiles";

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private Config config;

    @RequestMapping(value = "uploadResume", method = {RequestMethod.POST, RequestMethod.GET})
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        uploadOpt(request, RESUME_DOC_UPLOAD_PATH);
    }

    @RequestMapping(value = "storeResume", method = RequestMethod.POST)
    @ResponseBody
    public void resumeStore(HttpServletRequest request, HttpServletResponse response) {
        Message msg = new Message();
        msg.setSuccess(false);
        try {
            String name = request.getParameter("name");
            String education = request.getParameter("education");
            String graduate = request.getParameter("graduate");
            String major = request.getParameter("major");
            String fileName = request.getParameter("fileName");
            String dptId = request.getParameter("dptId");
//            String destFileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
            String destFileName = getDestName(fileName);
            ResumeRequestVo resume = new ResumeRequestVo();
            if (!StringUtils.isEmpty(dptId))
                resume.setDptId(Integer.valueOf(dptId));
            resume.setOwner(name.trim());
            resume.setMajor(major.trim());
            resume.setEducation(education.trim());
            resume.setGraduateTime(new SimpleDateFormat("yyyy-MM-dd").parse(graduate));
            resume.setUploaderId(request.getSession().getAttribute("login_userId").toString());
            resume.setFilePath(getTempFilePath(RESUME_DOC_UPLOAD_PATH));
            resume.setFileName(fileName);
            if (!StringUtils.isEmpty(getFileType(fileName)))
                resume.setFileType(getFileType(fileName));

            resume.setDestName(destFileName);
            int insert = resumeService.addResume(resume);
            if (insert > 0) {
                msg.setSuccess(true);
                msg.setMsg("Save Success");
                File file = new File(getTempFilePath(RESUME_DOC_UPLOAD_PATH) + File.separator + fileName);
                if (file.exists()) {
                    file.renameTo(new File(getTempFilePath(RESUME_DOC_UPLOAD_PATH + File.separator + destFileName)));
                }
            }
        } catch (Exception e) {
            logger.info("Response Error", e);
            msg.setMsg("Some error happened when save the resume");
        }
        responseMsg(response, msg);
    }

    @RequestMapping(value = "resumeList/{start}/{size}", method = RequestMethod.POST)
    public void getResumes(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable("start") int start, @PathVariable("size") int size) {
        ResumeRequestVo options = new ResumeRequestVo();
        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String keyword = request.getParameter("keyword");
            if (!StringUtils.isEmpty(keyword))
                options.setKeyword(keyword);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isEmpty(from)) {
                options.setFrom(sdf.parse(from));
                logger.info("Date Format Error");
            }
            if (!StringUtils.isEmpty(to)) {
                options.setTo(sdf.parse(to));
            }

            Integer t = resumeService.findResumeDetailCnt(options);

            if (null == t || t < 1) {
                responseMsg(response, new Message<>(false, NoticeConst.NO_DATA_NOTICE));
                return;
            }

            List<ResumeMapVo> list = resumeService.findResumeDetail(options, start, size);
            if (!CollectionUtils.isEmpty(list)) {

                responseMsg(response, new Message(new ResponseVo<>(list, t), true, NoticeConst.GET_DATA_NOTICE));
                return;
            }

//            logger.info("-------|||||----------{}", t);
//            Integer count = resumeService.getResumesCnt(options);
//            List<Resume> resumes = resumeService.getResumes(options, start, size);
//            if (!CollectionUtils.isEmpty(resumes)) {
//                logger.info("Resume List:{}", JSON.toJSON(resumes));
//                
//                responseMsg(response, new Message(new ResponseVo<>(resumes, count), true, NoticeConst.NO_DATA_NOTICE));
//                return;
//            }
        } catch (Exception e) {
            logger.info("Error :", e);
        }
        responseMsg(response, new Message<>(false, NoticeConst.NO_DATA_NOTICE));
    }

    @RequestMapping(value = "{id}/resumeDown", method = RequestMethod.GET)
    public void resumeDown(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) throws NotLoginException {
        Message message = new Message();
        message.setSuccess(false);
        if (getLoginUser(request).getRole() > 2) {
            message.setMsg("Sorry, you can't access to download");
            responseMsg(response, message);
            return;
        }
        if (!StringUtils.isEmpty(id)) {
            Resume resume = resumeService.findResume(id.trim());
            if (!Objects.isNull(resume)) {
                File file = new File(resume.getFilePath() + File.separator + resume.getDestName());
                String downName = resume.getOwner() + "_简历" + new Date().getTime() + (StringUtils.isEmpty(resume.getFileType()) ? "" : "." + resume.getFileType());
                download(response, file, downName);
            }
        }
    }

    //    @RequestMapping(value = "{id}/previewDoc", method = RequestMethod.GET)
//    public void previewDoc(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id){
//        if (!StringUtils.isEmpty(id)) {
//            Resume resume = resumeService.findResume(id.trim());
//            if (!Objects.isNull(resume)) {
//
//            }
//        }
//    }
    @RequestMapping(value = "/previewDoc", method = RequestMethod.GET)
    public void previewDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resumeId = request.getParameter("resId");
        if (StringUtils.isEmpty(resumeId)){
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        Resume destResume = resumeService.findResumeById(resumeId);
        if (destResume == null || StringUtils.isEmpty(destResume.getDestName())){
           
        }
        File file = new File("D:/test.swf");

        responseMsg(response, new Message<>(true, file.getCanonicalPath()));
    }

    @RequestMapping(value = "findResums", method = RequestMethod.POST)
    public void findResumes(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        String dptId = request.getParameter("ctrId");
        String proId = request.getParameter("proId");
        Integer start = StringUtils.isEmpty(request.getParameter("start")) ? 1 : Integer.valueOf(request.getParameter("start"));
        Integer size = StringUtils.isEmpty(request.getParameter("size")) ? 16 : Integer.valueOf(request.getParameter("size"));
        ResumeRequestVo options = new ResumeRequestVo();
        if (!StringUtils.isEmpty(dptId))
            options.setDptId(Integer.valueOf(dptId.trim()));
        if (!StringUtils.isEmpty(proId))
            options.setProId(Integer.valueOf(proId));
        if (!StringUtils.isEmpty(keyword))
            options.setKeyword(keyword);
        Integer cnt = resumeService.findAllowResumesCnt(options);
        if (cnt == null || cnt <= 0) {
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }

        List<ResumeMapVo> list = resumeService.findAllowResumes(options, start, size);
        if (CollectionUtils.isEmpty(list)) {
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }

        responseMsg(response, new Message(new ResponseVo<>(list, cnt), true, NoticeConst.GET_DATA_NOTICE));
    }

    @RequestMapping(value = "resumeDetail", method = RequestMethod.POST)
    @ResponseBody
    public void resumeDetail(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("workerId");
        if (StringUtils.isEmpty(id)) {
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        ResumeMapVo resume = resumeService.findResumeById(id.trim());
        if (Objects.isNull(resume)) {
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }

        responseMsg(response, new Message(resume, true, NoticeConst.GET_DATA_NOTICE));
    }

    @RequestMapping(value = "resumeInfo/update", method = RequestMethod.POST)
    public void updateResume(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String owner = StringUtils.isEmpty(request.getParameter("owner")) ? null : request.getParameter("owner").trim();
        String education = StringUtils.isEmpty(request.getParameter("education")) ? null : request.getParameter("education");
        String major = StringUtils.isEmpty(request.getParameter("major")) ? null : request.getParameter("major");
        Date graduateTime = StringUtils.isEmpty(request.getParameter("graduateTime")) ? null : sdf.parse(request.getParameter("graduateTime"));
        Integer dptId = StringUtils.isEmpty(request.getParameter("ctrId")) ? null : Integer.valueOf(request.getParameter("ctrId"));
        String id = StringUtils.isEmpty(request.getParameter("workerId")) ? null : request.getParameter("workerId");
        if (id == null || owner == null && education == null && major == null && graduateTime == null && dptId == null) {
            responseMsg(response, new Message<>(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        ResumeRequestVo vo = new ResumeRequestVo();
        vo.setOwner(owner);
        vo.setEducation(education);
        vo.setMajor(major);
        vo.setGraduateTime(graduateTime);
        vo.setDptId(dptId);
        vo.setId(id);
        Integer update = resumeService.updateResume(vo);
        if (update == null || update <= 0) {
            responseMsg(response, new Message(false, NoticeConst.UPDATE_FAIL));
            return;
        }
        responseMsg(response, new Message(true, NoticeConst.UPDATE_SUCCESS));
    }

    @RequestMapping(value = "uploadRes", method = RequestMethod.POST)
    public void uploadRe(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> list = upload.parseRequest(request);
            String resName = null, major = null, education = null;
            Integer dptId = null;
            Date graduateTime = null;
            FileItem tmpFileItem = null;
            Gender gender = null;
            Privilege privilege = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (FileItem item : list) {
                if ("resumeFile".equals(item.getFieldName())) {
                    tmpFileItem = item;
                } else if ("graduateTime".equals(item.getFieldName())) {
                    graduateTime = StringUtils.isEmpty(item.getString()) ? null : format.parse(item.getString());
                } else if ("major".equals(item.getFieldName())) {
                    major = StringUtils.isEmpty(item.getString()) ? null : new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                } else if ("resName".equals(item.getFieldName())) {
                    resName = StringUtils.isEmpty(item.getString()) ? null : new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                } else if ("resumeDpt".equals(item.getFieldName())) {
                    dptId = StringUtils.isEmpty(item.getString()) ? null : NumberUtils.toInt(item.getString());
                } else if ("education".equals(item.getFieldName())) {
                    education = StringUtils.isEmpty(item.getString()) ? null : new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                } else if ("gender".equals(item.getFieldName())) {
                    gender = StringUtils.isEmpty(item.getString()) ? null : Gender.valueOf(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                } else if ("privilege".equals(item.getFieldName())) {
                    privilege = StringUtils.isEmpty(item.getString()) ? null : Privilege.valueOf(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                }
            }

            if (resName == null || major == null || education == null || graduateTime == null || tmpFileItem == null) {
                responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
                return;
            }

            Calendar calendar = Calendar.getInstance();
            String tmpFileDir = getTempFilePath("resumeCob" + File.separator + getLoginUser(request).getId()) + File.separator + (calendar.get(Calendar.YEAR)) + (calendar.get(Calendar.MONTH) + 1);
            File tmpFile = new File(tmpFileDir);
            if (!tmpFile.exists()) {
                tmpFile.mkdirs();
            }
            String originName = new String(tmpFileItem.getName().getBytes("ISO-8859-1"), "UTF-8");
            String destName = getLoginUser(request).getId() + "_" + new Date().getTime() + originName.substring(tmpFileItem.getName().lastIndexOf("."));
            File destFile = new File(tmpFileDir, destName);
            FileUtils.copyInputStreamToFile(tmpFileItem.getInputStream(), destFile);
            ResumeRequestVo requestVo = new ResumeRequestVo();
            requestVo.setOwner(resName);
            requestVo.setDptId(dptId);
            requestVo.setDestName(destName);
            requestVo.setFileName(originName);
            requestVo.setFileType(originName.substring(originName.lastIndexOf(".")));
            requestVo.setFilePath(tmpFileDir);
            requestVo.setEducation(education);
            requestVo.setMajor(major);
            requestVo.setGraduateTime(graduateTime);
            requestVo.setGender(gender);
            requestVo.setPrivilege(privilege);
            Integer updateCnt = resumeService.addResume(requestVo);
            if (updateCnt == null || updateCnt <= 0) {
                responseMsg(response, new Message(false, NoticeConst.DATA_SAVE_FAIL));
                return;
            }
            responseMsg(response, new Message(true, NoticeConst.DATA_SAVE_SUCCESS));
        }


    }

    @RequestMapping(value = "owenRes/{start}/{size}", method = RequestMethod.POST)
    public void ModifyResumeList(HttpServletRequest request, HttpServletResponse response, @PathVariable("start") Integer start, @PathVariable("size") Integer size) throws NotLoginException {
        String keyword = request.getParameter("keyword");
        Integer cnt = resumeService.findUploadResumesCnt(getLoginUser(request).getId(), keyword);
        if (cnt == null || cnt <= 0) {
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        List<ResumeMapVo> resumeMapVos = resumeService.findUploadResumes(getLoginUser(request).getId(), keyword, start, size);
        responseMsg(response, new Message(new ResponseVo<>(resumeMapVos, cnt), true, NoticeConst.GET_DATA_NOTICE));
    }

    public void batchDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = StringUtils.isEmpty(request.getParameter("resumeIds")) ? request.getParameter("resumeIds").split(",") : null;
        if (ids == null) {
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        List<ResumeMapVo> list = resumeService.findResFiles(ids);
        if (CollectionUtils.isEmpty(list)){
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        byte[] bt = new byte[1024];
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(config.TMP_ZIP_DIR));
        for (ResumeMapVo vo : list){
            if (StringUtils.isEmpty(vo.getDestName()))
                continue;
            File file = new File(config.FILES_COB + File.separator + vo.getDptName());
            if (!file.exists())
                continue;
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len;
            while ((len = fis.read(bt)) > 0){
                out.write(bt, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();

    }
}

