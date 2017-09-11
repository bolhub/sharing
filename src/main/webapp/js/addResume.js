$(function () {
    // Basic
    $('.dropify').dropify();

    // Translated
    $('.dropify-fr').dropify({
        messages: {
            'default': '点击或拖拽文件到这里',
            'replace': '点击或拖拽文件到这里来替换文件',
            'remove': '移除文件',
            'error': '对不起，你上传的文件太大了'
        }
    });

    // Used events
    var drEvent = $('.dropify-event').dropify();

    drEvent.on('dropify.beforeClear', function (event, element) {
        return confirm("Do you really want to delete \"" + element.filename + "\" ?");
    });

    drEvent.on('dropify.afterClear', function (event, element) {
        alert('File deleted');
    });


    $("#ctlBtn").unbind().bind("click", function (e) {

        $("#resumeForm").bootstrapValidator('validate');
        e.preventDefault();
        var form = $("#resumeForm")[0];
        var formData = new FormData(form);
        $.ajax({
            url: host + "resume/uploadRes",
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var result = data ? JSON.parse(data) : null;
                if (result.success){
                    tip.tipBox({text: result.msg}, true);
                    $("#resumeForm").reset();
                } else {
                    tip.tipBox({text: result.msg}, true);
                }
            },
            error: function () {
                tip.tipBox({type: 'err', text: "服务器错误！"}, true)
            }
        });
    })

});

$("#resumeForm").bootstrapValidator({
    message: 'This value not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        resName: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '简历人员姓名不可为空'
                },
                stringLength: {
                    min: 2,
                    max: 30,
                    message: '姓名为2-30字符'
                },
                regexp: {
                    regexp: /^[\u4e00-\u9fa5]{2,4}$|^[a-zA-Z]{3,30}$/,
                    message: '姓名为2-4字的中文名或3-30字符的英文名'
                }
            }
        },
        education: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '学历信息不可为空'
                }
            }
        },
        // major: {
        //     group: 'col-lg-5',
        //     validators: {
        //         notEmpty: {
        //             message: '专业不可为空'
        //         }
        //     }
        // },
        graduateTime: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '毕业时间不可为空'
                },
                date: {
                    format: 'yyyy/MM/dd',
                    message: '时间格式不符'
                }
            }
        }/*,
        resumeDpt: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '公司不可为空'
                }
            }
        }*/
    }
})

$(function () {
    $.ajax({
        url: host + 'organize/dpsTotal/searchList',
        type: 'POST',
        async: false,
        data: {dptName: null},
        success: function (data) {
            if (data) {
                var result = JSON.parse(data).target;
                if (!result)
                    return;
                $("select[name='resumeDpt']").html("");
                for (var i = 0; i < result.length; i++) {
                    $("select[name='resumeDpt']").append('<option value="' + result[i].id + '">' + result[i].dptName + '</option>')
                }
                $('select').searchableSelect();
            }
        }
    })
})


