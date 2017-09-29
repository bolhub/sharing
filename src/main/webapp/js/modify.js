/**
 * Created by bolom on 2017/9/24.
 */

var resModify = {
    url: {

    },
    // 简历修改数据详情
    wrkDtGet: function (data, flag) {
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
            while (flag) {
                tip.mod.find(".form-group:eq(1)").find("select").eq(0).searchableSelect();
                flag = false;
            }
        }
    },
    //开发中心下拉框
    dptTtGet: function (data, info, cFlag) {
        var result = data ? JSON.parse(data).target : null;
        if (!result || result.target)
            return;
        $("select[name='resumeDpt']").html("");
        for (var i = 0; i < result.length; i++) {
            tip.mod.find(".form-group").eq(4).find("select").eq(0).append('<option value="' + result[i].id + '">' + result[i].dptName + '</option>')
        }
        tip.mod.find(".form-group").eq(4).find("select").eq(0).find("option[value=" + info.dptId + "]").attr("selected", true);
        while (cFlag) {
            tip.mod.find(".form-group:eq(4)").find("select").eq(0).searchableSelect();
            cFlag = false;
        }
    },
    //修改取消按钮操作
    wrkMdBtnFn: function () {
        tip.mod.find(".glyphicon-pencil").unbind().bind("click", function () {
            $(this).parent(".col-lg-5").attr("hidden", true).next(".col-lg-5").removeAttr("hidden").children("input").focus();
        });
        tip.mod.find(".glyphicon-remove").unbind().bind("click", function () {
            $(this).parent(".col-lg-5").attr("hidden", true).prev(".col-lg-5").removeAttr("hidden").children("input").focus();
        });
    },
    //简历修改数据操作
    wrkUpFn: function (workerId) {
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
                    url: ctrFn.url.updateResume,
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
    },
    wrkModify: function () {
        //修改视图
        var flag = true, cFlag = true;
        $("#worker-center .glyphicon-edit").unbind().bind("click", function () {
            event.stopPropagation();
            var workerId = $(this).parent(".roadmap-ico").parent(".roadmap-item").attr("resumeId");
            //简历修改
            tip.tipMod({mod: "modify"}, function () {
                tip.mod.find(".form-group .col-lg-5:not('.update-tar')").attr("hidden", false).find("input").attr("readonly", "readonly");
                tip.mod.find(".update-tar").attr("hidden", true);
                $.when($.post(ctrFn.url.resumeDetail, {workerId: workerId}), $.post(host + 'organize/dpsTotal/searchList', {}))
                    .then(function (data1, data2) {
                        ctrFn.wrkDtGet(data1[0], flag);
                        ctrFn.dptTtGet(data2[0], JSON.parse(data1[0]).target, cFlag);

                    }, function () {
                        tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
                    }).done(function () {
                    ctrFn.wrkMdBtnFn();
                    ctrFn.wrkUpFn(workerId);

                })
            })
        })
    }
}
