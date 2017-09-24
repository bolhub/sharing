/**
 * Created by bolom on 2017/8/15.
 */
$(function () {
    var count;
    var flag = true;
    var userList = {
        listTmp: $('<tr>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '</tr>'),
        userOpts: function () {
            return {
                keyword: $(".keyword-input").val() ? $(".keyword-input").val().trim() : null
            }
        },
        usrGet: function (data) {
            var result = data ? JSON.parse(data) : null;
            if (result && result.success) {
                var list = result.target.data;
                count = result.target.counts;
                for (var i = 0; i < list.length; i++) {
                    var item = list[i];
                    var clone = userList.listTmp.clone();
                    $("td:eq(0)", clone).text(i + 1);
                    $("td:eq(1)", clone).text(item.name);
                    $("td:eq(2)", clone).text(userList.checkRole(item.role));
                    $("td:eq(3)", clone).text(util.formatDate(item.rgtTime));
                    if (loginInfo.role == 1 && item.role == 2 || loginInfo.role == 2 && item.role == 3)
                        $("td:eq(4)", clone).html('<span class="update-user-btn" uid="' + item.id + '" uname="' + item.name + '" opt="reset">重置密码</span>');
                    $(".user-list").append(clone);
                }
                while (flag) {
                    $(".box").jqPaginator({
                        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                        totalCounts: count,
                        pageSize: 8,
                        currentPage: 1,
                        visiblePages: 7,
                        disableClass: 'disabled',
                        activeClass: 'active',
                        onPageChange: function (n) {
                            userList.fillList(n, this.pageSize);
                        }
                    });
                    flag = false;
                }
            }
        },
        pwdBtnOpt: function () {
            $("span.update-user-btn").click(function () {
                var userId = $(this).attr("uid"),
                    uname = $(this).attr("uname");
                tip.tipMod({
                    title: "提示",
                    text: '<span class="glyphicon glyphicon-info-sign"> </span>您是否重置' + uname + "的密码",
                    closeBtnName: "取消",
                    sureBtnName: "确定"
                }, function () {
                    tip.mod.find("#sure-btn").unbind().one("click", function () {
                        tip.mod.find(".modal-body").html('<span class="loading"></span>');
                        util.post({
                            url: host + 'password/update',
                            data: {
                                userId: userId,
                                newPwd: '000000'
                            },
                            success: function (result) {
                                if (result && JSON.parse(result).success) {
                                    tip.mod.find(".modal-body").html("重置成功！初如密码为：000000");
                                } else {
                                    tip.mod.find("重置失败！");
                                }
                                setTimeout(function () {
                                    tip.mod.modal('hide')
                                }, 1000)
                            }
                        }, function () {
                            tip.mod.modal('hide')
                        })
                    })
                })
            })
        },
        fillList: function (start, size) {
            start = start ? start : 1;
            size = size ? size : 8;
            $(".user-list").html("");
            $.post( host + "reset/" + start + "/" + size, userList.userOpts())
                .then(function (data) {
                    userList.usrGet(data);
                }, function () {
                    tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
                }).done(function () {
                userList.pwdBtnOpt();
            })
        },
        checkRole: function (role) {
            switch (role) {
                case 1:
                    return "系统管理员";
                case 2:
                    return "管理员";
                case 3:
                    return "用户";
            }
        }
    }

    userList.fillList();
    $(".user-search-btn").click(function () {
        userList.fillList();
    })

})