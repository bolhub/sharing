/**
 * Created by bolom on 2017/8/4.
 */

$(function () {
    var home = {
        checkEmpty: function (dom) {
            if (!dom.val()) {
                dom.parent("div").next(".info-tip").html('<span class="glyphicon glyphicon-exclamation-sign">不可为空</span>');
                return false;
            }
            dom.parent("div").next(".info-tip").html("");
            return true;
        },
        checkAllowPwd: function (dom) {
            if (dom.val().length < 3 || dom.val().length > 12) {
                dom.parent("div").next(".info-tip").html('<span class="glyphicon glyphicon-exclamation-sign">密码至少在3位以上，最高12位</span>');
                return false;
            }
            dom.parent("div").next(".info-tip").html("");
            return true;
        },
        checkSame: function (arg1, arg2, isSameOld) {
            var flag = arg1.val() == arg2.val();
            if (isSameOld) {
                if (flag) {
                    arg1.parent("div").next(".info-tip").html('<span class="glyphicon glyphicon-exclamation-sign">新设密码不可与原密码一致</span>');
                    return false
                }
                arg1.parent("div").next(".info-tip").html("");
                return true;
            } else {
                if (flag) {
                    arg1.parent("div").next(".info-tip").html("");
                    return true;
                }
                arg1.parent("div").next(".info-tip").html('<span class="glyphicon glyphicon-exclamation-sign">两次密码输入不一致</span>');
                argq.focus();
                return false;
            }
        },
        initMenu: function () {
            var menu = loginInfo.menus;
            var mainTitle = [];
            var n = 0;
            for (var i = 0; i < menu.length; i++) {
                if (menu[i].pMenuNo == 0) {
                    console.log(menu[i].menuName);
                    mainTitle.push(menu[i]);
                    mainTitle[n].childs = [];
                    n++
                }
            }

            for (var j = 0; j < menu.length; j++) {
                if (menu[j].pMenuNo == 0)
                    continue;
                for (var m = 0; m < mainTitle.length; m++) {
                    if (menu[j].pMenuNo != mainTitle[m].menuNo)
                        continue;
                    mainTitle[m].childs.push(menu[j]);
                }
            }

            var mDom = "";
            for (var t = 0; t < mainTitle.length; t++) {

                var cDom = "";
                for (var s = 0; s < mainTitle[t].childs.length; s++) {
                    // cDom += '<li><a href="' + host + mainTitle[t].childs[s].url + '">' + mainTitle[t].childs[s].menuName + '</a> </li>';
                    cDom += '<li class="menu-list-item" menuUrl="' + mainTitle[t].childs[s].url + '"><a href="javascript:void(0);">' + mainTitle[t].childs[s].menuName + '</a> </li>';
                }
                mDom += '<li>' + mainTitle[t].menuName + '</li><li><ul class="nav nav-pills nav-stacked"> ' + cDom + '</ul></li>';
            }

            $(".menu").html(mDom);
        },
        initLoginInfo: function () {
            var roleNum = parseInt(loginInfo.role), roleName;
            if (roleNum == 1) {
                $(".main-contain").html('<span class="loading"></span>').load(host + "authorization/view");
                roleName = "系统管理员";
                $("[menuUrl='authorization']").focus();
            } else if (roleNum == 2) {
                $(".main-contain").html('<span class="loading"></span>').load(host + "resumeList/view");
                roleName = "管理员";
            } else if (roleNum == 3) {
                $(".main-contain").html('<span class="loading"></span>').load(host + "sourceList/view");
                roleName = "用户";
            }

            $(".login-acct").text(loginInfo.acct);
            $(".login-role").text(roleName);
        }
    }

    home.initMenu();
    home.initLoginInfo();

    $("#login-down").unbind().bind("click", function () {
        $("#login-info").slideToggle(100);
    });

    $("#login-info").mouseleave(function () {
        $("#login-info").slideUp(100);
    })

    $(".menu-list-item").click(function () {
        var url = $(this).attr("menuUrl");
        if (url)
            $(".main-contain").html('<span class="loading"></span>').load(host + url + "/view");
    })

    $("#reset-pwd-btn").click(function () {
        tip.tipMod({
            mod: 'tipMod',
            title: '修改密码',
            text: '<div class="input-group"><span class="input-group-addon">原设密码</span> <input type="password" class="form-control" name="old-pwd"></div>' +
            '<div class="info-tip"></div>' +
            '<div class="input-group"><span class="input-group-addon">新设密码</span> <input type="password" class="form-control" name="new-pwd"></div>' +
            '<div class="info-tip"></div>' +
            '<div class="input-group"><span class="input-group-addon">确认密码</span> <input type="password" class="form-control" name="check-pwd"></div>' +
            '<div class="info-tip"></div>'
        });

        $("#tipMod [type='password']").unbind().bind("keyup", function () {
            home.checkEmpty($(this));
        });
        $("#tipMod [type='password']:not([name='old-pwd'])").unbind().bind("keyup blur", function () {
            home.checkAllowPwd($(this));
        });
        $("#sure-btn").unbind().bind("click", function () {
            var oldPwd = $("#tipMod").find("[name='old-pwd']").val(),
                newPwd = $("#tipMod").find("[name='new-pwd']").val(),
                checkPwd = $("#tipMod").find("[name='check-pwd']").val();
            if (home.checkEmpty($("#tipMod [type='password']")) && home.checkAllowPwd($("#tipMod [type='password']:not([name='old-pwd'])"))
                && home.checkSame($("#tipmod [name='new-pwd']"), $("#tipMod [name='check-pwd']")) && home.checkSame($("#tipmod [name='new-pwd']"), $("#tipMod [name='old-pwd']"), true)) {
                util.post({
                    url: host + "resetPwd",
                    data: {
                        oldPwd: oldPwd,
                        newPwd: newPwd
                    },
                    success: function (data) {
                        var result = JSON.parse(data);
                        if (result && result.success)
                            tip.tipBox({text: '密码更新成功！'}, true)
                        else
                            tip.tipBox({text: '密码更新失败！原密码可能有误'}, true);
                        tip.mod.modal('hide');
                    }
                }, function () {
                    tip.mod.modal('hide');
                })
            }
        })
    })

    $("#exist-btn").unbind().bind("click", function () {
        document.location.href = host + "logout";
    })
})
