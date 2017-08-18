package com.mworld.resume.controller;

import com.mworld.common.BaseController;
import com.mworld.common.Message;
import com.mworld.common.NoticeConst;
import com.mworld.resume.po.Department;
import com.mworld.resume.po.Project;
import com.mworld.resume.service.DeptProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bolom on 2017/8/14.
 */
@Controller
@RequestMapping("/organize")
public class DeptProjController extends BaseController {

    @Autowired
    private DeptProjService deptProjService;

    @RequestMapping(value = "{option}/searchList")
    public void getSearchList(HttpServletRequest request, HttpServletResponse response, @PathVariable("option") String option) {


    }

    @RequestMapping(value = "{option}/checkExist", method = RequestMethod.POST)
    public void checkExists(HttpServletRequest request, HttpServletResponse response, @PathVariable("option") String option) {
        String checkName = request.getParameter("checkName");
        if (StringUtils.isEmpty(checkName)) {
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        int cnt = -1;
        switch (option) {
            case "dpt":
                cnt = deptProjService.findDptCntByName(checkName);
                break;
            case "pro":
                cnt = deptProjService.findPrjCntByName(checkName);
                break;
            default:
                responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
                return;
        }

        if (cnt > 0) {
            responseMsg(response, new Message(false, NoticeConst.REPEAT_DATA));
            return;
        }
        responseMsg(response, new Message(true, NoticeConst.CAN_BE_USE));
    }

    @RequestMapping(value = "{option}/add", method = RequestMethod.POST)
    public void addDptPrj(HttpServletRequest request, HttpServletResponse response, @PathVariable("option") String option) {
        String saveTag = request.getParameter("saveName");

        if (StringUtils.isEmpty(saveTag)) {
            responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
            return;
        }
        Integer saveCnt = -1;
        switch (option) {
            case "dpt":
                saveCnt = deptProjService.saveDpt(saveTag);
                break;
            case "pro":
                saveCnt = deptProjService.savePrj(saveTag);
                break;
            default:
                responseMsg(response, new Message(false, NoticeConst.LACK_PARAMETERS));
                return;
        }
        if (saveCnt > 1) {
            responseMsg(response, new Message(true, NoticeConst.DATA_SAVE_SUCCESS));
            return;
        }
        responseMsg(response, new Message(false, NoticeConst.DATA_SAVE_FAIL));
    }


    public void unionShips(HttpServletRequest request, HttpServletResponse response){
        String proName = request.getParameter("proName");
        String dptName = request.getParameter("dptName");

        Integer dptId = deptProjService.findDptIdByName(dptName);
        Integer proId = deptProjService.findProIdByName(proName);
        if (dptId == null || dptId < 1) {
            Department dpt = new Department(dptName);
            Integer depInCnt = deptProjService.saveDptPo(dpt);
            if (depInCnt == null || depInCnt < 1) {
                responseMsg(response, new Message(false, NoticeConst.DATA_SAVE_FAIL));
                return;
            }
            dptId = dpt.getId();
        }
        if (proId == null || proId < 1){
            Project pro = new Project(proName);
            Integer proInCnt = deptProjService.savePrjPo(pro);
            if (proInCnt == null || proInCnt < 1){
                responseMsg(response, new Message(false, NoticeConst.DATA_SAVE_FAIL));
                return;
            }
            proId = pro.getId();
        }
    }
}
