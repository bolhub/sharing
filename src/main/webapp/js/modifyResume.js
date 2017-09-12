$(function () {
    var modifyOpt = {
        url:{
            resumeDetail: host + "resume/resumeDetail",
            updateResume: host + "resume/resumeInfo/update"
        },
        pageTool: {flag: true, counts: 0},
        modify: {flag : true, cFlag: true},
        getList: function (start, size) {
            start = start ? start : 1;
            size = size ? size : 8;
            util.post({
                url: host + "resume/owenRes/" + start + "/" + size,
                data: {keyword: $(".resume-modify .keyword").val()},
                success: function (data) {
                    var result = data ? JSON.parse(data) : null;
                    if (result && result.success) {
                        var list = result.target.data;
                        modifyOpt.pageTool.counts = result.target.counts;
                        $(".modify-contain").html("");
                        for (var i = 0; i < list.length; i++) {
                            $(".modify-contain").append('<div class="roadmap-item" resumeId="' + list[i].id + '">\n' +
                                '            <span class="roadmap-ico"><span class="glyphicon glyphicon-edit pull-right"></span>' +
                                '            <span class="pull-left"><span class="glyphicon glyphicon-eye-open"></span></span></span>\n' +
                                '            <span class="roadmap-title">' + list[i].owner + '</span>\n' +
                                '        </div>');
                        }

                        while (modifyOpt.pageTool.flag) {
                            $(".box").jqPaginator({
                                first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                                prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                                next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                                last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                                page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                                totalCounts: modifyOpt.pageTool.counts,
                                pageSize: 8,
                                currentPage: 1,
                                visiblePages: 7,
                                disableClass: 'disabled',
                                activeClass: 'active',
                                onPageChange: function (n) {
                                    modifyOpt.getList(n, this.pageSize)
                                }
                            });
                            modifyOpt.pageTool = false;
                        }

                        modifyOpt.editOpt();
                    }
                }
            })
        },
    editOpt: function () {
            $(".modify-contain .glyphicon-edit").unbind().bind("click", function () {
                event.stopPropagation();
                var workerId = $(this).parent(".roadmap-ico").parent(".roadmap-item").attr("resumeId");
                //简历修改
                tip.tipMod({mod: "modify"}, function () {
                    tip.mod.find(".form-group .col-lg-5:not('.update-tar')").attr("hidden", false).find("input").attr("readonly", "readonly");
                    tip.mod.find(".update-tar").attr("hidden", true);
                    util.post({
                        url: modifyOpt.url.resumeDetail,
                        data: {workerId: workerId},
                        success: function (data) {
                            var result = data ? JSON.parse(data) : null;
                            if (result && result.success && result.target) {
                                var info = result.target;
                                tip.mod.find(".form-group").eq(0).find("input").val(info.owner)
                                tip.mod.find(".form-group").eq(1).find("input").eq(0).val(info.education)
                                tip.mod.find(".form-group").eq(1).find("select").eq(0).find("option[value=" + info.major + "]").attr("selected", true);
                                tip.mod.find(".form-group").eq(2).find("input").eq(0).val(info.major)
                                tip.mod.find(".form-group").eq(2).find("input[name='major'][value=" + info.major + "]").attr("checked", "checked")
                                tip.mod.find(".form-group").eq(3).find("input").val(util.formatDate(info.graduateTime))
                                tip.mod.find(".form-group").eq(4).find("input").eq(0).val(info.dptName);
                                while (modifyOpt.modify.flag) {
                                    tip.mod.find(".form-group:eq(1)").find("select").eq(0).searchableSelect();
                                    modifyOpt.modify.flag = false;
                                }

                                tip.mod.find(".glyphicon-pencil").unbind().bind("click", function () {
                                    $(this).parent(".col-lg-5").attr("hidden", true).next(".col-lg-5").removeAttr("hidden").children("input").focus();
                                });
                                tip.mod.find(".glyphicon-remove").unbind().bind("click", function () {
                                    $(this).parent(".col-lg-5").attr("hidden", true).prev(".col-lg-5").removeAttr("hidden").children("input").focus();
                                });

                                util.post({
                                    url: host + 'organize/dpsTotal/searchList',
                                    data: {dptName: null},
                                    success: function (data) {
                                        if (data) {
                                            var result = data ? JSON.parse(data).target : null;
                                            if (!result)
                                                return;
                                            $("select[name='resumeDpt']").html("");
                                            for (var i = 0; i < result.length; i++) {
                                                tip.mod.find(".form-group").eq(4).find("select").eq(0).append('<option value="' + result[i].id + '">' + result[i].dptName + '</option>')
                                            }
                                            tip.mod.find(".form-group").eq(4).find("select").eq(0).find("option[value=" + info.dptId + "]").attr("selected", true);
                                        }
                                        while (modifyOpt.modify.cFlag) {
                                            tip.mod.find(".form-group:eq(4)").find("select").eq(0).searchableSelect();
                                            modifyOpt.modify.cFlag = false;
                                        }
                                    }
                                });


                                tip.mod.find(".btn-primary").unbind().one("click", function () {
                                    var tag = $(".update-tar");
                                    var modify = {
                                        owner: ctrFn.checkUpdate(tag.eq(0), tag.eq(0).find("input").val()) ? tag.eq(0).find("input").val() : null,
                                        education: ctrFn.checkUpdate(tag.eq(1), tag.eq(1).find("select").find("option:selected").text()) ? tag.eq(1).find("select").find("option:selected").val() : null,
                                        major: ctrFn.checkUpdate(tag.eq(2), tag.eq(2).find("[name='major']:checked").val()) ? tag.eq(2).find("[name='major']:checked").val() : null,
                                        graduateTime: ctrFn.checkUpdate(tag.eq(3), tag.eq(3).find("input").val()) ? tag.eq(3).find("input").val() : null,
                                        ctrId: ctrFn.checkUpdate(tag.eq(4), tag.eq(4).find("select").find("option:selected").text()) ? tag.eq(4).find("select").find("option:selected").val() : null,
                                        workerId: workerId
                                    };
                                    var flag = false;
                                    if (!$.isEmptyObject(modify)) {
                                        for (x in modify) {
                                            if (modify[x]) {
                                                console.info(modify[x])
                                                flag = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (flag) {
                                        util.post({
                                            url: modifyOpt.url.updateResume,
                                            data: modify,
                                            success: function (data) {
                                                var result = data ? JSON.parse(data) : null;
                                                if (result && result.success) {
                                                    tip.tipBox({text: "修改成功"}, true);
                                                    tip.mod.modal("hide");
                                                } else {
                                                    tip.tipBox({text: "修改失败"}, true);
                                                    tip.mod.modal("hide");
                                                }
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    })
                })
            })
        }
    };

    modifyOpt.getList(null, 1, 10);


})