<%--
  Created by IntelliJ IDEA.
  User: susu
  Date: 2017/8/14
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <title>保安业数据发送后台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/css/view/main/main.css" media="all"/>
</head>
<body class="childrenBody">
<div class="panel_box row">
    <div class="panel col">
        <a href="javascript:;" data-url="page/user/allUsers.html">
            <div class="panel_icon" style="background-color:#FF5722;">
                <i class="layui-icon" data-icon="&#xe612;">&#xe612;</i>
            </div>
            <div class="panel_word userAll">
                <span></span>
                <cite>在线用户</cite>
            </div>
        </a>
    </div>

    <div class="panel col">
        <a href="javascript:;" data-url="page/message/message.html">
            <div class="panel_icon">
                <i class="layui-icon" data-icon="&#xe63a;">&#xe63a;</i>
            </div>
            <div class="panel_word newMessage">
                <span></span>
                <cite>新消息</cite>
            </div>
        </a>
    </div>

    <div class="panel col">
        <a href="javascript:;" data-url="page/news/newsList.html">
            <div class="panel_icon" style="background-color:#F7B824;">
                <i class="layui-icon" data-icon="&#xe631;">&#xe631;</i>
            </div>
            <div class="panel_word waitNews">
                <span></span>
                <cite>当前任务数</cite>
            </div>
        </a>
    </div>

</div>

<div class="row">
    <div class="sysNotice col">
        <blockquote class="layui-elem-quote title">更新日志</blockquote>
        <div class="layui-elem-quote layui-quote-nm" id="update_log">
            <%--<p># v1.0.1（优化） - 2017-07-14</p>--%>
            <%--<p>* 修改刚进入页面无任何操作时按回车键提示“请输入解锁密码！”</p>--%>
            <%--<p>* 优化关闭弹窗按钮的提示信息位置问题【可能是因为加载速度的原因，造成这个问题，所以将提示信息做了一个延时】</p>--%>
            <%--<p>* “个人资料”提供修改功能</p>--%>

        </div>
    </div>
    <div class="sysNotice col">
        <blockquote class="layui-elem-quote title">系统基本参数</blockquote>
        <table class="layui-table">
            <colgroup>
                <col width="150">
                <col>
            </colgroup>
            <tbody>
            <tr>
                <td>当前版本</td>
                <td class="version">1.0</td>
            </tr>
            <tr>
                <td>开发作者</td>
                <td class="author">shz</td>
            </tr>
            <tr>
                <td>服务器环境</td>
                <td class="server">zookeeper</td>
            </tr>
            <tr>
                <td>数据库版本</td>
                <td class="dataBase">oracle11g</td>
            </tr>
            <tr>
                <td>当前用户权限</td>
                <td class="userRights">最高管理员</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="${ctx}/js/plugin/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/view/main/main.js"></script>
</body>
</html>
