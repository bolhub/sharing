package com.mworld.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.mworld.common.BaseController;
import com.mworld.common.Message;
import com.mworld.common.ResponseVo;
import com.mworld.common.ValidMsg;
import com.mworld.common.annotation.Mark;
import com.mworld.common.exception.NotLoginException;
import com.mworld.login.po.User;
import com.mworld.login.service.UserService;
import com.mworld.login.util.Const;
import com.mworld.login.util.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by qi on 2017/8/3.
 */
@Controller
@RequestMapping("/")
public class UserController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Mark("登录/注册页面")
    @RequestMapping("/login")
    public String toLogin() {
        return "/login";
    }

    @Mark("登录首页")
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpSession session, @RequestParam("acct") String acct, @RequestParam("pwd") String pwd) {
        if (StringUtils.isEmpty(acct) || StringUtils.isEmpty(pwd))
            return "redirect:/login";
        else {
//            User user = userService.findUserByAcct(acct, pwd);
            User user = userService.initUser(acct, pwd);

            if (null == user)
                return "redirect:/login";

            session.setAttribute("login_state", Const.LOGIN_NOW);
            session.setAttribute("login_user", JSONObject.toJSONString(user));
            session.setAttribute("login_userId", user.getId());
            logger.info("Login_USER-------->{}", JSONObject.toJSONString(user));
            return "/home";
        }
    }

    @Mark("登出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("login_state");
        session.removeAttribute("user_role");
        return "redirect:/login";
    }

    @Mark("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(HttpServletRequest request, HttpServletResponse response, @RequestParam("acct") String acct, @RequestParam("pwd") String pwd,
                         @RequestParam("name") String name) {
        if (StringUtils.isEmpty(acct) || StringUtils.isEmpty(pwd)) {
            responseMsg(response, new Message(false, Const.LOGIN_INFO_INCOMPLETE));
            return;
        }

        if (userService.checkUser(acct) > 0) {
            responseMsg(response, new Message(false, Const.ACCT_CANNOT_REGISTER));
            return;
        }

        User user = new User();
        user.setAcct(acct.trim());
        user.setPwd(pwd.trim());
        user.setRole(3);
        if (!StringUtils.isEmpty(name))
            user.setName(name.trim());

        boolean sucSave = userService.register(user);
        if (sucSave) {
            responseMsg(response, new Message(true, Const.ACCT_REGISTER_SUCCESS));
        }
    }

    @Mark("页面跳转")
    @RequestMapping(value = "/{target}/view", method = RequestMethod.GET)
    public String goView(@PathVariable("target") String target) {
        return target;
    }

    @Mark("用户查询")
    @RequestMapping(value = "{opt}/{start}/{size}", method = RequestMethod.POST)
    public void userList(HttpServletRequest request, HttpServletResponse response, @PathVariable("opt") String opt, @PathVariable("start") Integer start, @PathVariable("size") Integer size) throws NotLoginException {
        if (!"userList".equals(opt) && !"reset".equals(opt)) {
            responseMsg(response, new Message(false, Const.UNKNOWN_OPERATION));
            return;
        }
        User loginUser = getLoginUser(request);
        if (loginUser.getRole() > 2) {
            responseMsg(response, new Message(false, Const.NO_PRIVILEGE));
            return;
        }
        String keyword = StringUtils.isEmpty(request.getParameter("keyword")) ? null : request.getParameter("keyword");
        Integer findRole = null;
        if ("userList".equals(opt))
            findRole = loginUser.getRole() == RoleType.ADMIN.getValue() ? RoleType.USER.getValue() : null;
        if ("reset".equals(opt))
            findRole = loginUser.getRole() + 1;
        Integer counts = userService.findUsersCnt(keyword, findRole);
        if (counts == null || counts < 1) {
            responseMsg(response, new Message(false, Const.NO_USER_DATA));
            return;
        }
        List<User> list = userService.findUsers(keyword, findRole);
        if (!CollectionUtils.isEmpty(list)) {
            
            responseMsg(response, new Message<>(new ResponseVo<>(list, counts), true, "Success"));
            return;
        }
        responseMsg(response, new Message(false, Const.NO_USER_DATA));
    }

    @Mark("管理员职位和密码")
    @RequestMapping(value = "{targetOpt}/update", method = RequestMethod.POST)
    @ResponseBody
    public void modifyIdentify(HttpServletRequest request, HttpServletResponse response, @PathVariable("targetOpt") String targetOpt) throws NotLoginException {
        String userId = request.getParameter("userId");
        if (StringUtils.isEmpty(userId)) {
            responseMsg(response, new Message(false, Const.LOGIN_INFO_INCOMPLETE));
            return;
        }
        boolean hasUpdate = false;
        if ("password".equals(targetOpt)) {
            String newPwd = request.getParameter("newPwd");
            if (StringUtils.isEmpty(newPwd)) {
                responseMsg(response, new Message(false, Const.LOGIN_INFO_INCOMPLETE));
                return;
            }
            if (getLoginUser(request).getId().equals(userId))
                hasUpdate = userService.updatePwdById(newPwd, userId, getLoginUser(request).getRole());
            else
                hasUpdate = userService.updatePwdById(newPwd, userId, getLoginUser(request).getRole() + 1);
        } else if ("grade".equals(targetOpt) || "degrade".equals(targetOpt)) {
            if (getLoginUser(request).getRole() != RoleType.SYSTEM.getValue()) {
                responseMsg(response, new Message(false, Const.NO_PRIVILEGE));
                return;
            }
            hasUpdate = "grade".equals(targetOpt) ? userService.updateUserRole(RoleType.ADMIN.getValue(), userId, RoleType.USER.getValue()) : userService.updateUserRole(RoleType.USER.getValue(), userId, RoleType.ADMIN.getValue());
        }
        if (!hasUpdate) {
            responseMsg(response, new Message(false, Const.UPDATE_FAIL));
            return;
        }
        responseMsg(response, new Message(true, Const.UPDATE_SUCCESS));
    }

    @RequestMapping(value = "validAcct", method = RequestMethod.POST)
    @ResponseBody
    public void acctValid(HttpServletRequest request, HttpServletResponse response) {
        String acct = request.getParameter("account");
        if ("bolom".equals(acct))
            if (userService.checkUser(acct) > 0) {
//                responseMsg(response, new Message(false, Const.ACCT_CANNOT_REGISTER));
                responseMsg(response, new ValidMsg(false));
                return;
            }
        responseMsg(response, new ValidMsg(true));
    }

    @Mark("管理员职位和密码")
    @RequestMapping(value = "resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws NotLoginException {
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            responseMsg(response, new Message(false, Const.LOGIN_INFO_INCOMPLETE));
            return;
        }
        Integer cnt = userService.updatePwd(getLoginUser(request).getId(), oldPwd, newPwd);
        if (cnt == null || cnt <= 0){
            responseMsg(response, new Message(false, Const.UPDATE_FAIL));
            return;
        }
        responseMsg(response, new Message(true, Const.UPDATE_SUCCESS));
    }
}
