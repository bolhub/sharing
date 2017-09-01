/**
 * Created by bolom on 2017/8/11.
 */

$(function () {
    var count;
    var flag = true;
    var sourceList = {
        listTmp: $('<tr>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '<td></td>' +
            '</tr>'),
        formatDate: function (tm) {
            var date = new Date(parseInt(tm));
            var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)
            var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
            return date.getFullYear() + "-" + month + "-" + day;
        },
        sourceOpts: function () {
            return {
                keyword: $(".keyword-input").val() ? $(".keyword-input").val().trim() : null
            }
        },
        fillList: function (start, size) {
            start = start ? start : 1;
            size = size ? size : 8;
            $(".source-list").html("");
            $.ajax({
                url: host + "/share/shareList/" + start + "/" + size,
                type: "POST",
                data: sourceList.sourceOpts(),
                success: function (data) {
                    if (!data)
                        return;
                    var result = JSON.parse(data);
                    console.info(data)
                    if (result && result.success && result.success == true) {
                        var list = result.target.data;
                        count = result.target.counts;
                        for (var i = 0; i < list.length; i++) {
                            var item = list[i];
                            var clone = sourceList.listTmp.clone();
                            $("td:eq(0)", clone).text(i + 1).attr("tabLab", i + 1);
                            $("td:eq(1)", clone).text(item.content);
                            $("td:eq(2)", clone).text(item.mark);
                            $("td:eq(3)", clone).text(sourceList.formatDate(item.description));
                            $("td:eq(3)", clone).text(item.description);
                            $("td:eq(4)", clone).text(sourceList.formatDate(item.uploadTime));
                            $("td:eq(5)", clone).html('<a href="' + host + "share/" + item.id + '/downShare">下载</a>');
                            $(".source-list").append(clone);
                        }
                        while (flag) {
                            $(".box").jqPaginator({
                                first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                                prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                                next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                                last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
                                page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                                // totalPages: 5,
                                totalCounts: count,
                                pageSize: 8,
                                currentPage: 1,
                                visiblePages: 7,
                                disableClass: 'disabled',
                                activeClass: 'active',
                                onPageChange: function (n) {
                                    sourceList.fillList(n, this.pageSize);
                                }
                            });
                            flag = false;
                        }
                    }
                }
            })
        }
    }

    sourceList.fillList();
    $(".source-search-btn").click(function () {
        sourceList.fillList();
    })
})