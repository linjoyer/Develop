layui.config({
    base: "/js/"
}).use(['form', 'layer'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //video背景
    $(window).resize(function () {
        if ($(".video-player").width() > $(window).width()) {
            $(".video-player").css({
                "height": $(window).height(),
                "width": "auto",
                "left": -($(".video-player").width() - $(window).width()) / 2
            });
        } else {
            $(".video-player").css({
                "width": $(window).width(),
                "height": "auto",
                "left": -($(".video-player").width() - $(window).width()) / 2
            });
        }
    }).resize();
    var contextpath = getContextPath();
    //登录按钮事件
    // form.on("submit(login)", function (data) {
    //     $.ajax({
    //         type: 'post',
    //         url: contextpath + '/LoginCheck.action',
    //         contentType: 'application/json;charset=utf-8',
    //         data: '{"username":"' + $('#sloginid').val() + '" ,"password":"' + $('#psw').val() + '"}',
    //         success: function (data) {//返回json结果
    //             if (data.code == "SUCCESS") {
    //                 SetCookie("userName", "管理员");
    //                 window.location.href = "/view/index.html";
    //             } else {
    //                 alert(data.msg);
    //             }
    //         }
    //     });
    //     return false;
    // })
})

//写入cookie
function SetCookie(name, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + 6 * 24 * 60 * 60 * 1000); //6天过期
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    return true;
}

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

