<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>天创运维监控平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <script src="${ctx}/js/plugin/layui/layui.js"></script>
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/css/base/global.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base/personal.css" media="all">
<body>
<div class="larry-grid" >
    <div id="linuxmsg" style="width: 100%;height: 300px;">

    </div>
</div>
<script type="text/javascript">
    layui.config({
        base : "${ctx}/js/base/"
    }).use(['form', 'laypage', 'layer','common'], function () {
        var $ = layui.jquery,
            form = layui.form(),
            laypage = layui.laypage,
            layer = layui.layer,
            common = layui.common;
        /**加载信息*/
        rolePageList();

        /**加载菜单信息*/
        function rolePageList(curr,searchTerm,searchContent){
            var pageLoading = layer.load(2);
            $.ajax({
                url : '${ctx}/linux/all',
                type : 'post',
                success : function(data) {
//                    console.log(data);
                    if(data.code=="SUCCESS" ){
                        var pdata =data.data;
                        $("#linuxmsg").html(pdata);
                    }else{
                        alert("获取信息失败");
                    }
                    layer.close(pageLoading);
                }

            });
        }
    });

    /**undefined 值 过滤*/
    function objNull(obj) {
        if(typeof(obj) == "undefined" || obj == null){
            return "";
        }
        return obj;
    }

</script>
</body>
</html>