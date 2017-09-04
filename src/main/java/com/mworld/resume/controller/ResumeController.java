package com.mworld.resume.controller;

import com.alibaba.fastjson.JSON;
import com.mworld.common.BaseController;
import com.mworld.common.NoticeConst;
import com.mworld.common.exception.NotLoginException;
import com.mworld.resume.po.Resume;
import com.mworld.resume.service.ResumeService;
import com.mworld.common.Message;
import com.mworld.common.ResponseVo;
import com.mworld.resume.vo.ResumeMapVo;
import com.mworld.resume.vo.ResumeRequestVo;
import com.mworld.util.DocConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/resume")
public class ResumeController extends BaseController {
    final static String RESUME_DOC_UPLOAD_PATH = "resumeFiles";

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ResumeService resumeService;

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
                response.setContentType("text/html;charset=UTF-8");
                responseMsg(response, new Message(new ResponseVo<>(list, t), true, NoticeConst.GET_DATA_NOTICE));
                return;
            }

//            logger.info("-------|||||----------{}", t);
//            Integer count = resumeService.getResumesCnt(options);
//            List<Resume> resumes = resumeService.getResumes(options, start, size);
//            if (!CollectionUtils.isEmpty(resumes)) {
//                logger.info("Resume List:{}", JSON.toJSON(resumes));
//                response.setContentType("text/html;charset=UTF-8");
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
    public void previewDoc(HttpServletRequest request, HttpServletResponse response) {
        String output = this.getClass().getClassLoader().getResource("/").getPath().replace("WEB-INF/classes/", "js/swf");
        logger.info(output + "-------------------");
        DocConverter docConverter = new DocConverter("D:/test2.doc");
        docConverter.setOutPath(output);
        docConverter.convert();
        if (docConverter.convert())
            responseMsg(response, new Message<>(true, "js/swf/test2.swf"));
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
        response.setContentType("text/html;charset=UTF-8");
        responseMsg(response, new Message(new ResponseVo<>(list, cnt), true, NoticeConst.GET_DATA_NOTICE));
    }

    @RequestMapping(value = "resumeDetail", method = RequestMethod.POST)
    @ResponseBody
    public void resumeDetail(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("workerId");
        if (StringUtils.isEmpty(id)){
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        ResumeMapVo resume = resumeService.findResumeById(id.trim());
        if (Objects.isNull(resume)){
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        responseMsg(response, new Message(resume, true, NoticeConst.GET_DATA_NOTICE));
    }

    @RequestMapping(value = "resumeInfo/update", method = RequestMethod.POST)
    public void updateResume(HttpServletRequest request, HttpServletResponse response) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String owner = request.getParameter("owner");
        String education = request.getParameter("education");
        String major = request.getParameter("major");
        Date graduateTime = !StringUtils.isEmpty(request.getParameter("graduateTime")) ? sdf.parse(request.getParameter("graduateTime")) : null;
        Integer dptId = !StringUtils.isEmpty(request.getParameter("ctrId")) ? Integer.valueOf(request.getParameter("ctrId")) : null;
    }
}

