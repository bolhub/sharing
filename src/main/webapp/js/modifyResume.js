$(function () {
    var modifyOpt = {
        pageTool: {flag: true, counts: 0},
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
                    }
                }
            })
        }
    };

    modifyOpt.getList(null, 1, 10);


})