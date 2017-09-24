var ctrFn = {
    opt: {ctrId: null, oprId: null, searchType: null},
    ctrPage: {flag: true, cnt: 0},
    proPage: {flag: true, cnt: 0},
    resPage: {flag: true, cnt: 0, cellFlag: true, cellCnt: 0},
    resArray: [],
    tmpPage: {currentPage: 1, pageSize: 9, totalPages: 0},
    url: {
        dptList: host + "organize/dpt/searchList",
        proList: host + "organize/proOfDpt/searchList",
        resumeList: host + "organize/proOfResume/searchList",
        dptCheck: host + 'organize/dpt/checkExist',
        proCheck: host + 'organize/pro/checkExist',
        dptAdd: host + 'organize/dpt/add',
        proAdd: host + 'organize/pro/add',
        getResumes: host + 'resume/findResums',
        resumeAdd: host + 'organize/createShip',
        ctrRename: host + "organize/dpt/rename",
        proRename: host + "organize/pro/rename",
        resumeDetail: host + "resume/resumeDetail",
        updateResume: host + "resume/resumeInfo/update"
    },
    addOpt: function (options, optionCtr) {
        options.dom.unbind().bind("click", function () {
            if (optionCtr && !ctrFn.opt.ctrId) {
                tip.tipBox({type: 'warn', text: "请先选择中心"});
                return;
            }
            tip.tipMod(options.init, function () {
                tip.mod.find("[name='checkName']").val('');
                tip.mod.find(".help-block").html('');
                if (options.init.label)
                    tip.mod.find(".label-name").html(options.init.label);
                tip.mod.find("[name = 'checkName']").unbind().bind("keyup", function () {
                    if (!$(this).val()) {
                        tip.mod.find(".help-block").html("数据为空");
                        return;
                    }
                    options.ajax.checkData.checkName = $(this).val();
                    if (optionCtr) {
                        options.ajax.checkData.ctrId = ctrFn.opt.ctrId;
                    }
                    $.post(options.ajax.url, options.ajax.checkData)
                        .then(function (data) {
                            var result = data ? JSON.parse(data) : null;
                            if (result && !result.valid) {
                                tip.mod.find(".help-block").text("已添加，不可使用");
                                return;
                            }
                            tip.mod.find(".help-block").text("可以使用");
                        }, function () {
                            tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
                        }).done(function () {
                        tip.mod.find(".commit-btn").unbind().one("click", function () {
                            var saveData = {
                                saveName: tip.mod.find("[name='checkName']").val(),
                                ctrId: ctrFn.opt.ctrId
                            };

                            util.post({
                                url: options.ajax.saveUrl,
                                data: saveData,
                                success: function (result) {
                                    var data = JSON.parse(result)
                                    if (data && data.success == true) {
                                        tip.tipBox({text: tip.mod.find("[name = 'checkName']").val() + "添加成功"}, true);
                                        tip.mod.modal('hide');
                                    } else {
                                        tip.tipBox({
                                            type: 'warn',
                                            text: tip.mod.find("[name = 'checkName']").val() + "添加失败"
                                        }, true);
                                        tip.mod.modal('hide');
                                    }
                                }
                            }, function () {
                                tip.mod.modal('hide');
                            })
                        })
                    })
                })
            })
        })
    },
    initSearchBox: function () {
        $("#search").hide();
        $(".tar-center .panel-title .glyphicon-search").unbind().bind("click", function () {
            $("#search").find("strong").text($(this).attr("search-content"));
            $("#search").find("[type='search']").attr("placeholder", "搜索" + $(this).attr("search-content")).focus();
            ctrFn.opt.searchType = $(this).attr("search-content");
            $("#search").hide().removeAttr("hidden").show(500);
        })
        $(document).keydown(function (e) {
            if (e.keyCode == 27)
                $("#search").hide(500).attr("hidden", "hidden");
        });
    },
    pageTool: function (init, callback) {
        init.box.jqPaginator({
            first: init.first ? init.first : null,
            prev: init.prev ? init.prev : '<li class="prev">&lt;</li>',
            next: init.next ? init.next : '<li class="next">&gt;</li>',
            last: init.last ? init.last : null,
            page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
            totalCounts: init.totalCounts,
            pageSize: init.pageSize ? init.pageSize : 9,
            currentPage: init.currentPage ? init.currentPage : 1,
            visiblePages: init.visiblePages ? init.visiblePages : 0,
            disableClass: 'disabled',
            activeClass: 'active',
            onPageChange: function (n) {
                ctrFn.tmpPage.currentPage = n;
                ctrFn.tmpPage.pageSize = this.pageSize;
                ctrFn.tmpPage.totalPages = this.totalPages;
                if (typeof  callback == "function")
                    callback();
            }
        });
    },
    initCtrSpace: function () {
        $("#ctr-center").find(".panel-heading").find("strong").find(".badge").text('0/0');
        $("#worker-center .panel-body").text("未查询到数据");
        ctrFn.ctrPage.flag = true;
        $(".ctr-box").html('');
    },
    initProSpace: function () {
        $("#pro-center").find(".panel-heading").find("strong").find(".badge").text('0/0');
        $("#worker-center .panel-body").text("请选择中心");
        ctrFn.proPage.flag = true;
        $(".pro-box").html('');
    },
    initWorkerSpace: function () {
        $("#worker-center").find(".panel-heading").find("strong").find(".badge").text('0/0');
        $("#worker-center .panel-body").text("未选择项目");
        ctrFn.resPage.flag = true;
        ctrFn.resArray = [];
        $(".res-box").html('');
    },
    fillData: function (data) {
        var result = JSON.parse(data);
        $("#ctr-center .panel-body").html("");
        if (result.success) {
            var list = result.target.data;
            ctrFn.ctrPage.cnt = result.target.counts;
            for (var i = 0; i < list.length; i++) {
                $("#ctr-center .panel-body").append('<div class="roadmap-item" ctrId="' + list[i].id + '" ctrName="' + list[i].dptName + '">\n' +
                    '            <span class="roadmap-ico"><span class="glyphicon glyphicon-edit pull-right"></span></span>\n' +
                    // '            <span class="glyphicon glyphicon-minus pull-right"></span>\n' +
                    '            <span class="roadmap-title">' + list[i].dptName + '</span>\n' +
                    '        </div>');
            }
            if (ctrFn.opt.ctrId) {
                $("#ctr-center .roadmap-item[ctrId='" + ctrFn.opt.ctrId + "']").addClass("active");
            }
            while (ctrFn.ctrPage.flag) {
                ctrFn.pageTool({box: $(".ctr-box"), totalCounts: ctrFn.ctrPage.cnt}, function () {
                    ctrFn.initCtrList(ctrFn.tmpPage.currentPage, ctrFn.tmpPage.pageSize);
                    $("#ctr-center").find(".panel-heading").find("strong").find(".badge").text(ctrFn.tmpPage.currentPage + "/" + ctrFn.tmpPage.totalPages);
                })
                ctrFn.ctrPage.flag = false;
            }
        }
    },
    changeName: function () {
        $("#ctr-center .glyphicon-edit").unbind().bind("click", function () {
            event.stopPropagation();
            var ctrId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("ctrId");
            // tip.tipMod({text: '你确定要删除'+$(this).parent(".roadmap-ico").parent(".roadmap-item").attr("ctrName")+"吗？" });
            tip.tipMod({text: '<div class="input-group"><span class="input-group-addon">更新名称</span><input name="changeName" type="text" class="form-control"/></div>'}, function () {
                tip.mod.find("#sure-btn").unbind().one("click", function () {
                    var changeName = tip.mod.find("[name='changeName']").val();
                    if (changeName) {
                        $.ajax({
                            url: ctrFn.url.ctrRename,
                            type: 'post',
                            data: {ctrId: ctrId, newName: changeName},
                            success: function (data) {
                                var result = data ? JSON.parse(data) : null;
                                if (result && result.success) {
                                    tip.tipBox({text: "修改成功，请刷新后查看"}, true);
                                    tip.mod.modal('hide');
                                } else {
                                    tip.tipBox({type: "warn", text: "修改失败"}, true);
                                    tip.mod.modal('hide');
                                }

                            },
                            error: function () {
                                tip.tipBox({type: 'err', text: "添加失败，服务器故障"}, true);
                                tip.mod.modal('hide');
                            }
                        })
                    }
                });
            })

        })
    },
    tabTag: function () {
        $("#ctr-center .roadmap-item").unbind().bind("click", function () {
            if (!$(this).hasClass("active")) {
                $(this).addClass("active").siblings(".roadmap-item").removeClass("active");
                $("#pro-center .process,#worker-center .process:eq(0)").text(">>" + $(this).attr("ctrName"));
                $("#worker-center .process:eq(1)").text("");
                $("#pro-center .panel-body").html("请选择中心");
                $("#worker-center .panel-body").text("未选择项目");
                $("#pro-center .pro-box, #worker-center .res-box").html("");
                $("#pro-center, #worker-center").find(".panel-heading").find("strong").find(".badge").text("0/0");
                ctrFn.proPage.flag = true;
                ctrFn.resPage.flag = true;
                ctrFn.findPrt($(this).attr("ctrId"));
                ctrFn.opt.ctrId = $(this).attr("ctrId");
            }
        })
    },
    initCtrList: function (start, size) {
        $.post(ctrFn.url.dptList,{
            keyword: ctrFn.opt.searchType && ctrFn.opt.searchType == "中心" ? $("#search input").val() : null,
            start: start ? start : 1,
            size: size ? size : 9
        }).then(function (data) {
            ctrFn.fillData(data);
        }, function () {
            tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
        }).done(function () {
            ctrFn.changeName();
            ctrFn.tabTag();
        })
    },
    prtList: function (data) {
        var result = JSON.parse(data);
        $("#pro-center .panel-body").html("");
        if (result.success) {
            var list = result.target.data;
            ctrFn.proPage.cnt = result.target.counts;
            for (var i = 0; i < list.length; i++) {
                $("#pro-center .panel-body").append('<div class="roadmap-item" pCtrId="' + id + '" iProId="' + list[i].id + '" iProName="' + list[i].proName + '">\n' +
                    '            <span class="roadmap-ico"><span class="glyphicon glyphicon-edit pull-right"></span></span>\n' +
                    '            <span class="roadmap-title">' + list[i].proName + '</span>\n' +
                    '        </div>')
            }
            if (ctrFn.opt.proId) {
                $("#pro-center .roadmap-item[iProId='" + ctrFn.opt.proId + "']").addClass("active");
            }

            while (ctrFn.proPage.flag) {
                ctrFn.pageTool({box: $(".pro-box"), totalCounts: ctrFn.proPage.cnt}, function () {
                    ctrFn.findPrt(id, ctrFn.tmpPage.currentPage, ctrFn.tmpPage.pageSize);
                    $("#pro-center").find(".panel-heading").find("strong").find(".badge").text(ctrFn.tmpPage.currentPage + "/" + ctrFn.tmpPage.totalPages);
                });

                ctrFn.proPage.flag = false;
            }
            $("#pro-center .glyphicon-edit").unbind().bind("click", function () {
                event.stopPropagation();
                var ctrId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("pCtrId");
                var proId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("iProId");
                // tip.tipMod({text: '你确定要删除'+$(this).parent(".roadmap-ico").parent(".roadmap-item").attr("ctrName")+"吗？" });
                tip.tipMod({text: '<div class="input-group"><span class="input-group-addon">更新名称</span><input name="changeName" type="text" class="form-control"/></div>'}, function () {
                    tip.mod.find("#sure-btn").unbind().one("click", function () {
                        var changeName = tip.mod.find("[name='changeName']").val();
                        if (changeName) {
                            $.ajax({
                                url: ctrFn.url.proRename,
                                type: 'post',
                                data: {ctrId: ctrId, proId: proId, newName: changeName},
                                success: function (data) {
                                    var result = data ? JSON.parse(data) : null;
                                    if (result && result.success) {
                                        tip.tipBox({text: "修改成功，请刷新后查看"}, true);
                                        tip.mod.modal('hide');
                                    } else {
                                        tip.tipBox({type: "warn", text: "修改失败"}, true);
                                        tip.mod.modal('hide');
                                    }

                                },
                                error: function () {
                                    tip.tipBox({type: 'err', text: "添加失败，服务器故障"}, true);
                                    tip.mod.modal('hide');
                                }
                            })
                        }
                    });
                })

            })

            $("#pro-center .roadmap-item").unbind().bind("click", function () {
                $(this).addClass("active").siblings(".roadmap-item").removeClass("active");
                $("#worker-center .process:eq(1)").text(">>" + $(this).attr("iProName"));
                $("#worker-center .panel-body").html("请选择项目");
                $("#worker-center .res-box").html("");
                $("#worker-center").find(".panel-heading").find("strong").find(".badge").text("0/0");
                ctrFn.resPage.flag = true;
                ctrFn.findWorker($(this).attr("pCtrId"), $(this).attr("iProId"));
                ctrFn.opt.proId = $(this).attr("iProId");
            });
        } else {
            $("#pro-center .panel-body").text("未查询到数据");
        }
    },
    editBtn: function(){
        $("#pro-center .glyphicon-edit").unbind().bind("click", function () {
            event.stopPropagation();
            var ctrId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("pCtrId");
            var proId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("iProId");
            // tip.tipMod({text: '你确定要删除'+$(this).parent(".roadmap-ico").parent(".roadmap-item").attr("ctrName")+"吗？" });
            tip.tipMod({text: '<div class="input-group"><span class="input-group-addon">更新名称</span><input name="changeName" type="text" class="form-control"/></div>'}, function () {
                tip.mod.find("#sure-btn").unbind().one("click", function () {
                    var changeName = tip.mod.find("[name='changeName']").val();
                    if (changeName) {
                        $.ajax({
                            url: ctrFn.url.proRename,
                            type: 'post',
                            data: {ctrId: ctrId, proId: proId, newName: changeName},
                            success: function (data) {
                                var result = data ? JSON.parse(data) : null;
                                if (result && result.success) {
                                    tip.tipBox({text: "修改成功，请刷新后查看"}, true);
                                    tip.mod.modal('hide');
                                } else {
                                    tip.tipBox({type: "warn", text: "修改失败"}, true);
                                    tip.mod.modal('hide');
                                }

                            },
                            error: function () {
                                tip.tipBox({type: 'err', text: "添加失败，服务器故障"}, true);
                                tip.mod.modal('hide');
                            }
                        })
                    }
                });
            })

        })
    },
    contextTab: function(){
        $("#pro-center .roadmap-item").unbind().bind("click", function () {
            $(this).addClass("active").siblings(".roadmap-item").removeClass("active");
            $("#worker-center .process:eq(1)").text(">>" + $(this).attr("iProName"));
            $("#worker-center .panel-body").html("请选择项目");
            $("#worker-center .res-box").html("");
            $("#worker-center").find(".panel-heading").find("strong").find(".badge").text("0/0");
            ctrFn.resPage.flag = true;
            ctrFn.findWorker($(this).attr("pCtrId"), $(this).attr("iProId"));
            ctrFn.opt.proId = $(this).attr("iProId");
        });
    },
    /*findPrt: function (id, start, size) {
        $.post( ctrFn.url.proList,
           {
                ctrId: id,
                keyword: ctrFn.opt.searchType && ctrFn.opt.searchType == "项目" ? $("#search input").val() : null,
                start: start ? start : 1,
                size: size ? size : 9
            }).then(function (data) {
            ctrFn.prtList(data);
        }, function () {
            tip.tipBox({type: 'err', title: '错误', text: "服务器故障！"}, true);
        }).done(function () {
            ctrFn.editBtn();
            ctrFn.contextTab();
        })
    },*/
    findPrt: function (id, start, size) {
        util.post({
            url: ctrFn.url.proList,
            data: {
                ctrId: id,
                keyword: ctrFn.opt.searchType && ctrFn.opt.searchType == "项目" ? $("#search input").val() : null,
                start: start ? start : 1,
                size: size ? size : 9
            },
            success: function (data) {
                var result = JSON.parse(data);
                $("#pro-center .panel-body").html("");
                if (result.success) {
                    var list = result.target.data;
                    ctrFn.proPage.cnt = result.target.counts;
                    for (var i = 0; i < list.length; i++) {
                        $("#pro-center .panel-body").append('<div class="roadmap-item" pCtrId="' + id + '" iProId="' + list[i].id + '" iProName="' + list[i].proName + '">\n' +
                            '            <span class="roadmap-ico"><span class="glyphicon glyphicon-edit pull-right"></span></span>\n' +
                            '            <span class="roadmap-title">' + list[i].proName + '</span>\n' +
                            '        </div>')
                    }
                    if (ctrFn.opt.proId) {
                        $("#pro-center .roadmap-item[iProId='" + ctrFn.opt.proId + "']").addClass("active");
                    }

                    while (ctrFn.proPage.flag) {
                        ctrFn.pageTool({box: $(".pro-box"), totalCounts: ctrFn.proPage.cnt}, function () {
                            ctrFn.findPrt(id, ctrFn.tmpPage.currentPage, ctrFn.tmpPage.pageSize);
                            $("#pro-center").find(".panel-heading").find("strong").find(".badge").text(ctrFn.tmpPage.currentPage + "/" + ctrFn.tmpPage.totalPages);
                        });

                        ctrFn.proPage.flag = false;
                    }
                    $("#pro-center .glyphicon-edit").unbind().bind("click", function () {
                        event.stopPropagation();
                        var ctrId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("pCtrId");
                        var proId = $(this).parent("span.roadmap-ico").parent(".roadmap-item").attr("iProId");
                        // tip.tipMod({text: '你确定要删除'+$(this).parent(".roadmap-ico").parent(".roadmap-item").attr("ctrName")+"吗？" });
                        tip.tipMod({text: '<div class="input-group"><span class="input-group-addon">更新名称</span><input name="changeName" type="text" class="form-control"/></div>'}, function () {
                            tip.mod.find("#sure-btn").unbind().one("click", function () {
                                var changeName = tip.mod.find("[name='changeName']").val();
                                if (changeName) {
                                    $.ajax({
                                        url: ctrFn.url.proRename,
                                        type: 'post',
                                        data: {ctrId: ctrId, proId: proId, newName: changeName},
                                        success: function (data) {
                                            var result = data ? JSON.parse(data) : null;
                                            if (result && result.success) {
                                                tip.tipBox({text: "修改成功，请刷新后查看"}, true);
                                                tip.mod.modal('hide');
                                            } else {
                                                tip.tipBox({type: "warn", text: "修改失败"}, true);
                                                tip.mod.modal('hide');
                                            }

                                        },
                                        error: function () {
                                            tip.tipBox({type: 'err', text: "添加失败，服务器故障"}, true);
                                            tip.mod.modal('hide');
                                        }
                                    })
                                }
                            });
                        })

                    })

                    $("#pro-center .roadmap-item").unbind().bind("click", function () {
                        $(this).addClass("active").siblings(".roadmap-item").removeClass("active");
                        $("#worker-center .process:eq(1)").text(">>" + $(this).attr("iProName"));
                        $("#worker-center .panel-body").html("请选择项目");
                        $("#worker-center .res-box").html("");
                        $("#worker-center").find(".panel-heading").find("strong").find(".badge").text("0/0");
                        ctrFn.resPage.flag = true;
                        ctrFn.findWorker($(this).attr("pCtrId"), $(this).attr("iProId"));
                        ctrFn.opt.proId = $(this).attr("iProId");
                    });
                } else {
                    $("#pro-center .panel-body").text("未查询到数据");
                }
            }
        })
    },
    findWorker: function (ctrId, proId, start, size) {
        util.post({
            url: ctrFn.url.resumeList,
            data: {
                ctrId: ctrId,
                proId: proId,
                keyword: ctrFn.opt.searchType && ctrFn.opt.searchType == "人员" ? $("#search input").val() : null,
                start: start ? start : 1,
                size: size ? size : 9
            },
            success: function (data) {
                var result = JSON.parse(data);
                $("#worker-center .panel-body").html('');
                if (result.success) {
                    var list = result.target.data;
                    ctrFn.resPage.cnt = result.target.counts;
                    for (var i = 0; i < list.length; i++) {
                        $("#worker-center .panel-body").append('<div class="roadmap-item" resumeId="' + list[i].id + '">\n' +
                            '            <span class="roadmap-ico"><span class="glyphicon glyphicon-edit pull-right"></span>' +
                            '            <span class="pull-left"><span class="glyphicon glyphicon-eye-open"></span></span></span>\n' +
                            '            <span class="roadmap-title">' + list[i].owner + '</span>\n' +
                            '        </div>')
                    }
                    while (ctrFn.resPage.flag) {

                        ctrFn.pageTool({box: $(".res-box"), totalCounts: ctrFn.resPage.cnt}, function () {
                            ctrFn.findWorker(ctrId, proId, ctrFn.tmpPage.currentPage, ctrFn.tmpPage.pageSize);
                            $("#worker-center").find(".panel-heading").find("strong").find(".badge").text(ctrFn.tmpPage.currentPage + "/" + ctrFn.tmpPage.totalPages);
                        })
                        ctrFn.resPage.flag = false;
                    }

                    //修改视图
                    var flag = true, cFlag = true;
                    $("#worker-center .glyphicon-edit").unbind().bind("click", function () {
                        event.stopPropagation();
                        var workerId = $(this).parent(".roadmap-ico").parent(".roadmap-item").attr("resumeId");
                        //简历修改
                        tip.tipMod({mod: "modify"}, function () {
                            tip.mod.find(".form-group .col-lg-5:not('.update-tar')").attr("hidden", false).find("input").attr("readonly", "readonly");
                            tip.mod.find(".update-tar").attr("hidden", true);
                            util.post({
                                url: ctrFn.url.resumeDetail,
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
                                        while (flag) {
                                            tip.mod.find(".form-group:eq(1)").find("select").eq(0).searchableSelect();
                                            flag = false;
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
                                                while (cFlag) {
                                                    tip.mod.find(".form-group:eq(4)").find("select").eq(0).searchableSelect();
                                                    cFlag = false;
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
                                    }
                                }
                            })
                        })
                    })

                    //swf视图
                    $("#worker-center .glyphicon-eye-open").unbind().bind("click", function () {
                        tip.tipMod({
                            // text: '<div style="position: absolute; left: 50px; top: 10px;">\n' +
                            // '    <a id="viewerPlaceHolder" style="width: 820px; height: 650px; display: block"></a>\n' +
                            // '    <div id="documentViewer" style="width: 820px; height: 650px; display: block"></div>'
                            text: '<div>\n' +
                            '    <a id="viewerPlaceHolder" style="height: 400px"></a>\n' +
                            '    <div id="documentViewer" style="400px"></div>'
                        }, function () {
                            $.ajax({
                                url: host + 'resume/previewDoc',
                                type: 'GET',
                                success: function (result) {
                                    if (!result)
                                        return;
                                    var data = JSON.parse(result);
                                    console.log(data.msg.replace("//", "/"))
                                    var fp = new FlexPaperViewer(
                                        'FlexPaperViewer',
                                        'viewerPlaceHolder', {
                                            config: {
                                                // SwfFile : escape(host + "js/swf/test.swf"),//编码设置
                                                // SwfFile: host + data.msg.replace("//", "/"),//编码设置
                                                SwfFile: 'http://127.0.0.1:80/test.swf',
                                                Scale: 0.6,
                                                ZoomTransition: 'easeOut',//变焦过渡
                                                ZoomTime: 0.5,
                                                ZoomInterval: 0.2,//缩放滑块-移动的缩放基础[工具栏]
                                                FitPageOnLoad: true,//自适应页面
                                                FitWidthOnLoad: true,//自适应宽度
                                                FullScreenAsMaxWindow: false,//全屏按钮-新页面全屏[工具栏]
                                                ProgressiveLoading: false,//分割加载
                                                MinZoomSize: 0.2,//最小缩放
                                                MaxZoomSize: 3,//最大缩放
                                                SearchMatchAll: true,
                                                InitViewMode: 'Portrait',//初始显示模式(SinglePage,TwoPage,Portrait)

                                                ViewModeToolsVisible: true,//显示模式工具栏是否显示
                                                ZoomToolsVisible: true,//缩放工具栏是否显示
                                                NavToolsVisible: true,//跳页工具栏
                                                CursorToolsVisible: false,
                                                SearchToolsVisible: true,
                                                PrintPaperAsBitmap: false,
                                                localeChain: 'en_US'
                                            }
                                        });
                                }
                            })
                        })
                    })

                } else {
                    $("#worker-center .panel-body").text("未查询到数据");
                }
            }
        })
    },
    checkUpdate: function (dom, domVal) {
        if (dom.attr("hidden") == "hidden")
            return false;
        if (!domVal || domVal == dom.prev(".prev-tar").find("input").val())
            return false;
        return true;
    },
    searchByKeyword: function () {
        switch (ctrFn.opt.searchType) {
            case "中心":
                ctrFn.initCtrSpace();
                ctrFn.initCtrList();
                break;
            case "项目":
                if (!ctrFn.opt.ctrId) {
                    tip.tipBox({type: 'warn', text: '请选择中心后再操作！'}, true)
                }
                ctrFn.initProSpace();
                ctrFn.findPrt(ctrFn.opt.ctrId);
                break;
            case "人员":
                if (!ctrFn.opt.ctrId || !ctrFn.opt.proId) {
                    tip.tipBox({type: 'warn', text: '请选择中心和项目后再操作！'}, true)
                }
                ctrFn.initWorkerSpace();
                ctrFn.findWorker(ctrFn.opt.ctrId, ctrFn.opt.proId);
                break;
        }
    },
    addWorker: function (start, size) {
        tip.tipMod({mod: 'wkAddMod',}, function () {
            tip.mod.find(".worker-list").html('<span class="loading"></span>');
            util.post({
                url: ctrFn.url.getResumes,
                data: {
                    keyword: $("#wkAddMod").find(".keyword-input").val(),
                    ctrId: ctrFn.opt.ctrId,
                    proId: ctrFn.opt.proId,
                    start: start ? start : 1,
                    size: size ? size : 16
                },
                success: function (data) {
                    var result = JSON.parse(data);
                    if (result && result.success) {
                        tip.mod.find(".worker-list").html("");
                        ctrFn.resPage.cellCnt = result.target.counts;
                        for (var i = 0; i < result.target.data.length; i++) {
                            tip.mod.find(".worker-list").append('<div class="worker-list-item" wkId="' + result.target.data[i].id + '">\n' +
                                '                        <p><span class="glyphicon glyphicon-user" title="姓名"></span><span>' + result.target.data[i].owner + '</span></p>\n' +
                                '                        <p><span class="glyphicon glyphicon-magnet" title="学历"></span><span>' + result.target.data[i].education + '</span></p>\n' +
                                '                        <p><span class="glyphicon glyphicon-tower" title="专业"></span><span title="' + result.target.data[i].major + '">' + result.target.data[i].major + '</span></p>\n' +
                                '                        <p><span class="glyphicon glyphicon-time" title="毕业时间"></span><span>' + util.formatDate(result.target.data[i].graduateTime, true) + '</span></p>\n' +
                                '                        <p><span class="glyphicon glyphicon-home" title="归属地"></span><span title="' + result.target.data[i].dptName + '">' + result.target.data[i].dptName + '</span></p>\n' +
                                '                    </div>')
                        }

                        if (ctrFn.resArray && ctrFn.resArray.length > 0) {
                            for (var j = 0; j < ctrFn.resArray.length; j++) {
                                $(".worker-list").find("[wkId='" + ctrFn.resArray[j] + "']").addClass("select");
                            }
                        }
                        while (ctrFn.resPage.cellFlag) {
                            ctrFn.pageTool({
                                box: $(".worker-page-box"),
                                totalCounts: ctrFn.resPage.cellCnt,
                                first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                                prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                                next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                                last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                                pageSize: 16,
                                visiblePages: 3
                            }, function () {
                                ctrFn.addWorker(ctrFn.tmpPage.currentPage, ctrFn.tmpPage.pageSize);
                            })
                            ctrFn.resPage.cellFlag = false;
                        }

                    } else {
                        $(".worker-page-box").html("");
                        tip.tipMod({mod: 'wkAddMod',}, function () {
                            tip.mod.find(".worker-list").html('<span>未查询到数据 </span>');
                        })
                    }
                    $(".worker-list-item").unbind().bind("click", function () {
                        if ($(this).hasClass("select")) {
                            $(this).removeClass("select");
                            ctrFn.resArray.splice(ctrFn.resArray.indexOf($(this).attr("wkId")), 1);
                        } else {
                            $(this).addClass("select");
                            ctrFn.resArray.push($(this).attr("wkId"))
                        }
                    });
                    $("#wkAddMod .modal-footer button:eq(1)").unbind().bind("click", function () {
                        util.post({
                            url: ctrFn.url.resumeAdd,
                            data: {
                                ctrId: ctrFn.opt.ctrId,
                                proId: ctrFn.opt.proId,
                                workers: ctrFn.resArray.toString()
                            },
                            success: function (data) {
                                var result = JSON.parse(data);
                                if (result && result.success) {
                                    tip.tipBox({text: "添加成功，请刷新后查看"}, true);
                                    tip.mod.modal('hide');
                                } else {
                                    tip.tipBox({text: "添加失败"}, true);
                                    tip.mod.modal('hide');
                                }
                            }
                        }, function () {
                            tip.mod.modal('hide');
                        })
                    });
                }
            }, function () {
                tip.mod.modal('hide');
            })
        })
    }
}

$(function () {
    ctrFn.initSearchBox();
    ctrFn.initCtrList();
    ctrFn.addOpt({
        dom: $("#ctr-center .panel-title .glyphicon-plus"),
        init: {
            mod: 'alertModal',
            title: '新增中心',
            label: '中心名称'
        },
        ajax: {
            url: ctrFn.url.dptCheck,
            checkData: {},
            saveUrl: ctrFn.url.dptAdd
        }
    }, false)

    //给中心添加新项目
    ctrFn.addOpt({
        dom: $("#pro-center .panel-title .glyphicon-plus"),
        init: {
            mod: 'alertModal',
            title: '新增项目',
            label: '项目名称'
        },
        ajax: {
            url: ctrFn.url.proCheck,
            checkData: {},
            saveUrl: ctrFn.url.proAdd
        }
    }, true);

    $("#worker-center .panel-title .glyphicon-plus").unbind().bind("click", function () {
        if (!ctrFn.opt.ctrId || !ctrFn.opt.proId) {
            tip.tipBox({type: 'warn', text: "请先选择中心和项目，再进行操作"});
            return;
        }
        ctrFn.resPage.cellFlag = true;
        ctrFn.resArray = [];
        ctrFn.addWorker();
        $("#wkAddMod .resume-search-btn").unbind().bind("click", function () {
            ctrFn.addWorker();
        })
        $(document).keydown(function (e) {
            if (e.keyCode == 13 && $("#wkAddMod .keyword-input").is(":focus")) {
                ctrFn.resPage.cellFlag = true;
                ctrFn.addWorker();
            }
        })
    })

    $("#search .btn").unbind().bind("click", function () {
        ctrFn.searchByKeyword();
    });

    $(document).keydown(function (e) {
        if (e.keyCode == 13 && $("#search input").is(":focus"))
            ctrFn.searchByKeyword();
    });
})


