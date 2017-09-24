/**
 * Created by bolom on 2017/8/11.
 */

$(function () {
    var userList = {
        pageTool : {count: 0, flag: true},
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
        //列表数据请求
        fillList: function (start, size) {
            start = start ? start : 1;
            size = size ? size : 8;
            $(".user-list").html("");
            $.post(
                host + "userList/" + start + "/" + size,
                userList.userOpts()
            ).then(function (data) {
                userList.usrGet(data);
            },function () {
                tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
            }).done(function () {
                userList.autChge();
            })
        },
        //用户列表及分页
        usrGet: function (data) {
            var result = data ? JSON.parse(data) : null;
            if (result && result.success) {
                var list = result.target.data;
                userList.pageTool.count = result.target.counts;
                for (var i = 0; i < list.length; i++) {
                    var item = list[i];
                    var clone = userList.listTmp.clone();
                    $("td:eq(0)", clone).text(i + 1);
                    $("td:eq(1)", clone).text(item.name);
                    $("td:eq(2)", clone).text(userList.checkRole(item.role));
                    $("td:eq(3)", clone).text(util.formatDate(item.rgtTime));
                    if (loginInfo.role == 1 && item.role == 3)
                        $("td:eq(4)", clone).html('<span class="update-user-btn" uid="' + item.id + '" uname="' + item.name + '" opt="grade">升级</span>');
                    if (loginInfo.role == 1 && item.role == 2)
                        $("td:eq(4)", clone).html('<span class="update-user-btn" uid="' + item.id + '" uname="' + item.name + '" opt="degrade">降级</span>');
                    $(".user-list").append(clone);
                }
                while (userList.pageTool.flag) {
                    $(".box").jqPaginator({
                        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                        // totalPages: 5,
                        totalCounts: userList.pageTool.count,
                        pageSize: 8,
                        currentPage: 1,
                        visiblePages: 7,
                        disableClass: 'disabled',
                        activeClass: 'active',
                        onPageChange: function (n) {
                            userList.fillList(n, this.pageSize);
                        }
                    });
                    userList.pageTool.flag = false;
                }
            }
        },
        //职位变更操作
        autChge: function () {
            $("span.update-user-btn").click(function () {
                var userId = $(this).attr("uid"),
                    uname = $(this).attr("uname"),
                    opt = $(this).attr("opt");
                tip.tipMod({
                    title: "提示",
                    text: '<span class="glyphicon glyphicon-info-sign"> </span>您是否将' + uname + (opt == "grade" ? "提升为管理员！" : "降级为用户"),
                    closeBtnName: "取消",
                    sureBtnName: "确定"
                }, function () {
                    tip.mod.find("#sure-btn").unbind().one("click", function () {
                        tip.mod.find(".modal-body").html("正在执行" + (opt == "grade" ? "升级" : "降级") + "操作！………………")
                        util.post({
                            url: host + opt + '/update',
                            data: {userId: userId},
                            success: function (result) {
                                if (result && JSON.parse(result).success) {
                                    $("span[uid='" + userId + "']").parent("td").prev().prev().text(opt == "grade" ? "管理员" : "用户");
                                    $(".user-list span[uid='" + userId + "']").attr("opt", opt == "grade" ? "degrade" : "grade").text(opt == "grade" ? "降级" : "升级");
                                    tip.mod.find(".modal-body").html(uname + (opt == "grade" ? "已升级为管理员！" : "已降为普通用户！"));
                                } else {
                                    tip.mod.find(".modal-body").html((opt == "grade" ? "升级" : "降级") + "失败！");
                                }
                                setTimeout(function () {
                                    tip.mod.modal('hide');
                                }, 2000)
                            }
                        }, function () {
                            tip.mod.modal('hide');
                        })
                    })
                })
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
