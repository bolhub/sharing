var tip = {
    mod: null,
    tipMod: function (init,callback) {
        tip.mod = init && init.mod ? $("#" + init.mod) : $("#tipMod");
        if (init) {
            if (init.title)
                tip.mod.find(".modal-title").html(init.title);
            if (init.text)
                tip.mod.find(".modal-body").html(init.text);
            if (init.closeBtnName)
                tip.mod.find(".close-btn").text(init.closeBtnName);
            if (init.closeBtnHide)
                tip.mod.find(".close-btn").hide();
            if (init.sureBtnName)
                tip.mod.find(".sure-btn").text(init.sureBtnName);
            if (init.sureBtnHide)
                tip.mod.find(".sure-btn").hide();
        }
        if (typeof callback == "function")
            callback();
        tip.mod.modal();
    },
    tipBox: function (init, autoClose) {
        var $box = init && init.mod ? $("#" + init.mod) : $("#tipBox");
        var type = init && init.type ? init.type : "info";
        var $mark = $box.find(".panel-title").find("span").eq(0);
        var $title = $box.find(".panel-title").find("strong").eq(0);
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
                $box.find(".panel-title").find("strong").html(init.title);
            if (init.text)
                $box.find(".panel-body").html(init.text);
        }
        $box.show(500);
        $box.find(".close").unbind().bind("click", function () {
            $box.hide(500)
        })
        if (autoClose) {
            setTimeout(function () {
                $box.hide(500);
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