$(function () {
    $.ajax({
        url: host + "resume/owenRes",
        type: 'post',
        success: function (data) {
            var result = data ? JSON.parse(data) : null;
            console.info(result)
        }
    })
})