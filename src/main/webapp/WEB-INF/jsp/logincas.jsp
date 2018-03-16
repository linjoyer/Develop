<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>远程CAS客户端登陆页面</title>
    <link rel="stylesheet" type="text/css" href="http://blog.163.com/wm_at163/blog/<%= request.getContextPath() %>/styles/main.css" />
    <script type="text/javascript">
        function getParam(name) {
            var queryString = window.location.search;
            var param = queryString.substr(1, query.length - 1).split("&");
            for (var i = 0; i < param.length; i++) {
                var keyValue = param[i].split("=");
                if (keyValue[0] == name) return keyValue[1];
            }
            return null;
        }
        function init() {
            // 显示异常信息
            var error = getParam("errorMessage");
            if (error) {
                document.getElementById("errorMessage").innerHTML = decodeURIComponent(error);
            }
            // 注入service
            var service = getParam("service");
            if (service)
                document.getElementById("service").value = decodeURIComponent(service);
            else
                document.getElementById("service").value = location.href;
        }
    </script>
</head>
<body>
<h1>远程CAS客户端登陆页面</h1>
<% if (request.getRemoteUser() == null) { %>
<div id="errorMessage"></div>
<form id="myLoginForm" action="https://guolin/cas-server/remoteLogin" method="post">
    <input type="hidden" id="service" name="service" value="">
    <input type="hidden" name="loginUrl" value="http://guolin:9080/cas-client-java-custom-login/login.jsp">
    <input type="hidden" name="submit" value="true" />
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密&nbsp;&nbsp;码:</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登陆" /></td>
        </tr>
    </table>
</form>
<script type="text/javascript">init()</script>
<% } else { %>
<div class="welcome">您好：<%= request.getRemoteUser() %></div>
<div id="logout">
    <a href="http://blog.163.com/wm_at163/blog/https://GUOLIN/cas-server/remoteLogout?service=http://guolin:9080/cas-client-java-custom-login/login.jsp">单点登出</a>
</div>
<% } %>
</body>
</html>