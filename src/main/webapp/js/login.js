/**
 * Created by bolom on 2017/8/4.
 */

$(function () {
    $("[name='goSignUp']").click(function () {
        $(".login-contain").slideUp(1000).attr("hidden", "hidden");
        $(".register-contain").hide().removeAttr("hidden").slideDown(1000);
    });
    $("[name='backLogin']").click(function () {
        $(".register-contain").attr("hidden", "hidden");
        $(".login-contain").hide().removeAttr("hidden").slideDown(1000);
    })


    $("#register-contain").bootstrapValidator({
        message: 'This value not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            account: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: '帐号不能为空'
                    },
                    stringLength: {
                        min: 5,
                        max: 28,
                        message: '只允许5-28个字符长度'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '只能包含字母、数字、点和下划线'
                    },
                    remote: {
                        type: 'POST',
                        url: host + 'validAcct',
                        message: '帐号已占用'
                    }
                }
            },
            username: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 20,
                        message: '姓名为2-30个字符'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5]{2,4}$|^[a-zA-Z]{3,30}$/,
                        message: '用户名为2-4个中文汉字或3-30个英文字符'
                    }
                }
            },
            password: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: '密码不可为空'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '两次输入密码不一致'
                    },
                    different: {
                        field: 'account',
                        message: '密码不可与帐号一致'
                    }
                }
            },
            confirmPassword: {
                group: '.col-lg-4',
                validators: {
                    notEmpty: {
                        message: "密码不可为空"
                    },
                    identical: {
                        field: 'password',
                        message: '两次输入密码不一致'
                    },
                    different: {
                        field: 'account',
                        message: '密码不可与帐号一致'
                    }
                }
            }
        }
    })

    var fillData = function () {
        return {
            acct: $("[name='account']").val().trim(),
            name:$("[name='username']").val().trim(),
            pwd: $("[name='password']").val().trim()
        }
    }

    $("[name='signUp']").click(function () {
        $("#register-contain").bootstrapValidator('validate');
        util.post({
            url: host + "register",
            data: fillData(),
            success : function (data) {
                var result = data ? JSON.parse(data) : null;
                if (data && result.success){
                    tip.tipBox({
                        text: result.msg
                    })
                } else {
                    tip.tipBox({
                        text: result.msg
                    })
                }
            }
        })
    })

})
