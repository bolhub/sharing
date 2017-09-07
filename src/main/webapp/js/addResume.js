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


    $("#ctlBtn").unbind().one("click", function (e) {
        e.preventDefault();
        var form = $("#resumeForm")[0];
        var formData = new FormData(form);
        formData.append("ids", "123");
        formData.append("ids", $("[name='file']").file)
        console.info(formData.get("ids"))
        console.info(formData)
        $.ajax({
            url: host + "resume/uploadRes" ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var result = data ? JSON.parse(data) : null;
                console.info("data --> " + result)
            },
            error: function () {
                tip.tipBox({type: 'err', text: "服务器错误！"},true)
            }
        });
    })

});


/*jQuery(function () {
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({


        // swf文件路径
        swf: host + 'webuploader-0.1.5/Uploader.swf',
        // 文件接收服务端。
        server: 'http://localhost:8090/resume/uploadResume',
        // 不压缩image
        resize: false,

        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        chunked: false,
        chunkSize: 1014 * 1024,
        threads: true,
        fileNumLimit: 1,
        fileSingleSizeLimit: 1000 * 1024 * 1024,
        duplicate: true

    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        // var fileName = file.name.length > 28 ? file.name.substr(0, 28) + '…' : file.name
        $list.append('<div id="' + file.id + '" class="item">' +
            // '<span class="doc-icon"></span> ' +
            '<h5 class="info" title="' + file.name + '">' + file.name + '</h5>' +
            '<p class="state">等待上传...</p>' +
            '</div>');
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css('width', percentage * 100 + '%');
    });

    uploader.on('uploadSuccess', function (file, response) {
        $('#' + file.id).find('p.state').text('已上传');
        var json = fillResumeInfo();
        json.fileName = file.name;
        $.ajax({
            url: host + "resume/storeResume",
            type: 'post',
            data: json,
            success: function (data) {

                console.info(data);
            },
            error: function () {

            }
        })
    });

    uploader.on('uploadError', function (file, reason) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').fadeOut();
    });

    uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $btn.children("span").eq(1).text('暂停上传');
        } else {
            $btn.children("span").eq(1).text('开始上传');
        }
    });

    $btn.on('click', function () {
        $("#resume-upload").bootstrapValidator('validate');

        // console.info(fillResumeInfo())
        // var name = $(".resume-name").val(),
        //     education = $(".resume-education").val(),
        //     graduate = $(".resume-graduate").val(),
        //     project = $(".resume-project").val();
        // if (!name || !education || !graduate || !project) {
        //     alert("请完整填写相关信息");
        //     return;
        // }


        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });

    function closeUploader() {
        for (var i = 0; i < uploader.getFiles().length; i++) {
            uploader.removeFile(uploader.getFiles()[i])
        }
        var $li = $("#" + uploader.getFiles()[i].id);
        $li.off().remove();
        setStatus('pedding');
        fileCount = 0;
        fileSize = 0;
        uploader.reset();
        updateStatus();
    }
});*/

function fillResumeInfo() {
    return {
        name: $("[name='resumeName']").val(),
        education: $("[name='resumeEducation']").val(),
        major: $("[name='resumeMajor']").val(),
        graduate: $("[name='resumeGraduate']").val(),
        dptId: $("select[name='resumeDpt']").val()
    }
}

$("#resume-upload").bootstrapValidator({
    message: 'This value not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        resumeName: {
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
        resumeEducation: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '学历信息不可为空'
                }
            }
        },
        resumeMajor: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '专业不可为空'
                }
            }
        },
        resumeGraduate: {
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
        },
        // resumeCompany: {
        //     group: 'col-lg-5',
        //     validators: {
        //         notEmpty: {
        //             message: '公司不可为空'
        //         }
        //     }
        // }
        resumeDpt: {
            group: 'col-lg-5',
            validators: {
                notEmpty: {
                    message: '公司不可为空'
                }
            }
        }
    }
})

$(function () {
    $.ajax({
        url: host + 'organize/dpsTotal/searchList',
        type: 'POST',
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

    $("#reset").click(function () {
        alert($("select[name='resumeDpt']").val())
    })
})


