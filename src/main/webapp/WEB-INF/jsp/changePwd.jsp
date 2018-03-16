<%--
  Created by IntelliJ IDEA.
  User: susu
  Date: 2017/8/14
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <title>天创运维监控平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/css/view/index/user.css" media="all"/>
</head>
<body class="childrenBody">
<form class="layui-form changePwd" id="formedit">
    <div style="margin:0 0 15px 110px;color:#f00;">新密码必须两次输入一致才能提交</div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="text" value="超级管理员" disabled class="layui-input layui-disabled">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">旧密码</label>
        <div class="layui-input-inline">
            <input type="password" value="" name="oldpwd" placeholder="请输入旧密码" lay-verify="required|oldPwd"
                   class="layui-input pwd">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-inline">
            <input type="password" value="" name="newpwd" id="newpwd" placeholder="请输入新密码" lay-verify="required|newPwd"
                   id="oldPwd" class="layui-input pwd">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline">
            <input type="password" value="" name="newpwd2" id="newpwd2" placeholder="请确认密码"
                   lay-verify="required|confirmPwd" class="layui-input pwd">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="changePwd">立即修改</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/plugin/layui/layui.js"></script>
<script>
    layui.config({
        base: "${ctx}/js/"
    }).use(['form', 'layer', 'jquery', 'layedit', 'laydate'], function () {
        var form = layui.form(),
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            $ = layui.jquery;
        //创建一个编辑器
        form.on("submit(changePwd)", function (data) {
            if ($("#newpwd").val() == $("#newpwd2").val() && $("#newpwd2").val() != "") {
                var d = {};
                var data = $("#formedit").serializeArray();
                $.each(data, function () {
                    d[this.name] = this.value;
                });
                var formdata = JSON.stringify(d);
                $.ajax({
                    type: 'post',
                    url: '${ctx}/ChangePwd.action',
                    contentType: 'application/json;charset=utf-8',
                    data: formdata,
                    success: function (data) {//返回json结果
                        if (data.code == "SUCCESS") {
                            layer.msg("修改成功！");
                            location.reload();
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            } else {
                layer.msg("请确认新密码一致且不为空！");
            }

        });
    });
</script>
</body>
</html>
