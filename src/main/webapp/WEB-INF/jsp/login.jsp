<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="system/mytags.jsp" %>
<html lang="en">
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/css/view/login/login.css" media="all"/>
    <title>登录</title>
</head>
<script type="text/javascript" src="${ctx}/js/plugin/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/view/login/login.js"></script>
<body>
<%--<iframe href="/login" height="100px" width="200px" id="casframe"></iframe>--%>
<video class="video-player" preload="auto" autoplay="autoplay" loop="loop" data-height="1080" data-width="1920"
       height="100%" width="100%">
    <source src="${ctx}/base/media/login.mp4" type="video/mp4">

</video>
<div class="video_mask"></div>

<div class="login">
    <h1>天创运维监控平台</h1>
    <form class="layui-form"  action="" method="post">
        <div class="layui-form-item">
            <input class="layui-input" id="username" name="username" placeholder="用户名" lay-verify="required" type="text"
                   autocomplete="off">
        </div>
        <div class="layui-form-item">
            <input class="layui-input" id="password" name="password" placeholder="密码" lay-verify="required" type="password"
                   autocomplete="off">
        </div>
        <div class="layui-form-item form_code">
            <input class="layui-input"  name="captcha" placeholder="验证码" lay-verify="required" type="text" autocomplete="off">
            <div class="code"><img src="${ctx}/kaptcha.jpg" id="kaptcha-img" width="116" height="36"></div>
        </div>
        <button type="submit" class="layui-btn login_btn" lay-submit="" lay-filter="login">登录</button>
        <%--<button type="button" class="layui-btn login_btn" onclick="testiframe()">测试</button>--%>
    </form>

</div>
</body>


<script>
    layui.use(['form', 'layedit', 'laydate'], function(){
        var layer = layui.layer;
        <c:if test="${msg!=null}">
            layer.msg('${msg}', {
                icon: 2,
                time: 3000 //2秒关闭（如果不配置，默认是3秒）
            }, function(){
                //do something
            });
        </c:if>
    });
    function testiframe(){
        alert($("#casframe").html());
    }
    function dddl() {
        var username =document.getElementById("username").value;
        var password =document.getElementById("password").value;
        $.ajax({
            url:"https://localhost:8443/cas/login?service=http://localhost:8282/cas",//单点登录的报表服务器
            dataType:"jsonp",//跨域采用jsonp方式
            data:{"username":username,"password":password},
            jsonp:"callback",
            timeout:5000,//超时时间（单位：毫秒）
            success:function(data) {
                console.log(data);
                if (data.status === "success") {
                    alert("success"); //登录成功
                    window.location=data.url;//登录成功，直接跳转到平台系统页面
                }
                else if (data.status === "fail"){
                    alert("fail");//登录失败（用户名或密码错误）
                }
            },
            error:function(){
                alert("error"); // 登录失败（超时或服务器其他错误）
            }
        });
    }
</script>
</html>
