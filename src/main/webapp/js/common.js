var tip = {
    tipMod: function (init,callback) {
        var $mod = init && init.mod ? $("#" + init.mod) : $("#tipMod");
        if (init) {
            if (init.title)
                $mod.find(".modal-title").html(init.title);
            if (init.text)
                $mod.find(".modal-body").html(init.text);
            if (init.closeBtnName)
                $mod.find(".close-btn").text(init.closeBtnName);
            if (init.closeBtnHide)
                $mod.find(".close-btn").hide();
            if (init.sureBtnName)
                $mod.find(".sure-btn").text(init.sureBtnName);
            if (init.sureBtnHide)
                $mod.find(".sure-btn").hide();
        }
        if (typeof callback == "function")
            callback();
        $mod.modal();
    },
    tipBox: function (init, autoClose) {
        var $mod = init && init.mod ? $("#" + init.mod) : $("#tipBox");
        var type = init && init.type ? init.type : "info";
        var $mark = $mod.find(".panel-title").find("span").eq(0);
        var $title = $mod.find(".panel-title").find("strong").eq(0);
        switch (type) {
            case 'info':
                $mark.removeClass().addClass("glyphicon glyphicon-info-sign");
                $title.html("提示");
                break;
            case "warn":
                $mark.removeClass().addClass("glyphicon glyphicon-warning-sign");
                $title.html("警告");
                break;
            case "err":
                $mark.removeClass().addClass("glyphicon glyphicon-exclamation-sign");
                $title.html("错误");
                break;
        }
        if (init) {
            if (init.title)
                $mod.find(".panel-title").find("strong").html(init.title);
            if (init.text)
                $mod.find(".panel-body").html(init.text);
        }
        $mod.show(500);
        $mod.find(".close").unbind().bind("click", function () {
            $mod.hide(500)
        })
        if (autoClose) {
            setTimeout(function () {
                $mod.hide(500);
            }, 5000);
        }
    }
}

var util= {
    formatDate: function (tm, endMonth) {
        var date = new Date(parseInt(tm));
        var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)
        var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
        if (endMonth)
            return  month + "/" + date.getFullYear();
        return date.getFullYear() + "-" + month + "-" + day;
    }
}

$(function () {
    $("#tipBox").hide();
})