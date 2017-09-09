package com.mworld.shared.controller;

import com.mworld.common.BaseController;
import com.mworld.common.NoticeConst;
import com.mworld.common.exception.NotLoginException;
import com.mworld.shared.po.ShareFile;
import com.mworld.login.po.User;
import com.mworld.shared.service.ShareFileService;
import com.mworld.common.Message;
import com.mworld.common.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Created by bolom on 2017/8/10.
 */
@Controller
@RequestMapping("/share")
public class ShareFileController extends BaseController {
    final static String SHARE_FILE_UPLOAD_PATH = "shareFile";
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShareFileService shareFileService;

    @RequestMapping(value = "uploadShare", method = {RequestMethod.POST, RequestMethod.GET})
    public void uploadShare(HttpServletRequest request, HttpServletResponse response) {
        uploadOpt(request, SHARE_FILE_UPLOAD_PATH);
    }

    @RequestMapping(value = "saveShareInfo", method = RequestMethod.POST)
    public void saveShareInfo(HttpServletRequest request, HttpServletResponse response) {
        String cnt = request.getParameter("cnt");
        String mark = request.getParameter("mark");
        String dpt = request.getParameter("dpt");
        String fn = request.getParameter("fn");

        if (StringUtils.isEmpty(cnt) || StringUtils.isEmpty(fn)) {
            responseMsg(response, new Message(false, NoticeConst.INFO_INCOMPLETE));
            return;
        }
        try {
            User user = getLoginUser(request);
            ShareFile shareFile = new ShareFile();
            shareFile.setUploaderId(user.getId());
            shareFile.setContent(cnt);
            shareFile.setFileName(fn);
            String dtn = getDestName(fn);
            shareFile.setDestName(dtn);
            shareFile.setFilePath(getTempFilePath(SHARE_FILE_UPLOAD_PATH));
            if (!StringUtils.isEmpty(fn))
                shareFile.setFileType(getFileType(fn));
            if (!StringUtils.isEmpty(mark))
                shareFile.setMark(mark);
            if (!StringUtils.isEmpty(dpt))
                shareFile.setDescription(dpt);
            Integer rows = shareFileService.saveShareFile(shareFile);
            if (null != rows && rows > 0) {
                File file = new File(getTempFilePath(SHARE_FILE_UPLOAD_PATH) + File.separator + fn);
                if (file.exists()) {
                    file.renameTo(new File(getTempFilePath(SHARE_FILE_UPLOAD_PATH) + File.separator + dtn));
                    responseMsg(response, new Message(true, NoticeConst.DATA_SAVE_SUCCESS));
                    return;
                }
            }
            responseMsg(response, new Message(false, NoticeConst.DATA_SAVE_FAIL));
        } catch (Exception e){
            logger.info("捕获异常：{}", e);
        }

    }

    @RequestMapping(value = "{id}/downShare", method = RequestMethod.GET)
    public void downShare(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        ShareFile shareFile = shareFileService.getShareFileById(id.trim());
        File file = new File(shareFile.getFilePath() + File.separator + shareFile.getDestName());
        download(response, file, shareFile.getFileName());
    }

    @RequestMapping(value = "shareList/{start}/{size}")
    public void shareList(HttpServletRequest request, HttpServletResponse response, @PathVariable("start") Integer start, @PathVariable("size") Integer size){
        String keyword = request.getParameter("keyword");
        Integer counts = shareFileService.getShareFilesCnt(keyword);
        if (counts == null || counts < 1){
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        List<ShareFile> shareFiles = shareFileService.getShareFiles(keyword, start, size);
        if (CollectionUtils.isEmpty(shareFiles)){
            responseMsg(response, new Message(false, NoticeConst.NO_DATA_NOTICE));
            return;
        }
        
        responseMsg(response, new Message(new ResponseVo<>(shareFiles, counts),true, NoticeConst.GET_DATA_NOTICE));

    }

}
