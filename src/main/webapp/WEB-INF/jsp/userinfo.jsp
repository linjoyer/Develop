<%--
  Created by IntelliJ IDEA.
  User: susu
  Date: 2017/8/14
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
</head>
<body class="childrenBody">
<form class="layui-form" id="formedit">
    <div class="layui-form-item">
        <label class="layui-form-label">发送地址</label>
        <div class="layui-input-block">
            <input type="text" name="mName" id="mName" class="layui-input newsName" lay-verify="title" placeholder="请输入数据类型">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">ACCOUNT_ID</label>
        <div class="layui-input-block">
            <input type="text" name="ACCOUNT_ID" id="ACCOUNT_ID" class="layui-input newsName" lay-verify="title" placeholder="请输入任务说明">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">ORG_CODE</label>
        <div class="layui-input-block">
            <input type="text" name="ORG_CODE" id="ORG_CODE" class="layui-input newsName" lay-verify="title" placeholder="请输入对应表名">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">ORG_NAME</label>
        <div class="layui-input-block">
            <input type="text" name="ORG_NAME" id="ORG_NAME" class="layui-input newsName" lay-verify="title" placeholder="请输入对应表名">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">PASSWORD</label>
        <div class="layui-input-block">
            <input type="text" name="PASSWORD" id="PASSWORD" class="layui-input newsName" lay-verify="title" placeholder="请输入对应表名">
        </div>
    </div>


    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="addNews">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/plugin/layui/layui.js"></script>
<script>
    layui.config({
        base : "${ctx}/js/"
    }).use(['form','layer','jquery','layedit','laydate'],function(){
        var form = layui.form(),
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            laypage = layui.laypage,
            layedit = layui.layedit,
            laydate = layui.laydate,
            $ = layui.jquery;

        //创建一个编辑器
        var editIndex = layedit.build('news_content');
        var addNewsArray = [],addNews;
        form.on("submit(addNews)",function(data){
            var d = {};
            var data = $("#formedit").serializeArray();
            $.each(data, function() {
                d[this.name] = this.value;
            });
            var formdata=JSON.stringify(d);
            $.ajax({
                type:'post',
                url:'${ctx}/UpdateUserInfo.action?protype=userinfo',
                contentType:'application/json;charset=utf-8',
                data:formdata,
                success:function(data){//返回json结果
                    if(data.code=="SUCCESS"){
                        layer.msg("提交成功！");
                        location.reload();
                    }else{
                        layer.msg(data.msg);
                    }
                }
            });


        });

        $.ajax({
            type:'post',
            url:'${ctx}/QueryUserInfo.action?protype=userinfo',
            async:false,
            contentType:'application/json;charset=utf-8',
            data:'',
            success:function(data){
//						    console.log(data);
                if(data.code=="success"){
                    var obj =data.data;
                    for(var key in obj)
                    {
                        $("#"+key).val(obj[key]);
                    }
                    form.render(); //更新全部
                }else{
                    alert(data.msg);
                }
            }
        });



    });
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return "";
    }

</script>
</body>
</html>
