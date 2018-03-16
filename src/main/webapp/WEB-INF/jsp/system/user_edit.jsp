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
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css">
    <script src="${ctx}/js/plugin/layui/layui.js"></script>

</head>
<body class="childrenBody" style="font-size: 12px;">
<form class="layui-form layui-form-pane">
    <input id="userid" name="userid" type="hidden" value="${user.userid}">

    <input id="pageFlag"  type="hidden" value="${pageFlag}">

    <div class="layui-form-item">
        <label class="layui-form-label">登陆账号</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="username" lay-verify="required|userLoginName" maxlength="10" value="${user.username}" placeholder="请输入登陆账号">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户姓名</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="name" lay-verify="required|userName" maxlength="25" value="${user.name}" placeholder="请输入用户姓名">
        </div>
    </div>
    <div class="layui-form-item" pane>
        <label class="layui-form-label">角色状态</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="radio" name="state" value="0" title="有效" checked disabled>
                <input type="radio" name="state" value="1" title="失效" disabled>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' and  user.state == '0' }">
                <input type="radio" name="state" value="0" title="有效"  disabled <c:if test="${user.state ==0 }">checked</c:if>/>
                <input type="radio" name="state" value="1" title="失效" disabled  <c:if test="${user.state ==1 }">checked</c:if>/>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' and  user.state == '1' }">
                <input type="radio" name="state" value="0" title="有效"   <c:if test="${user.state ==0 }">checked</c:if>/>
                <input type="radio" name="state" value="1" title="失效"   <c:if test="${user.state ==1 }">checked</c:if>/>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: center;">
            <button class="layui-btn" lay-submit="" lay-filter="saveUser">保存</button>
            <button type="layui-btn" id="cancle" class="layui-btn layui-btn-primary">取消</button>

    </div>
</form>
<script type="text/javascript">
    layui.config({
        base : "${ctx}/js/base/"
    }).use(['form','layer','jquery','common'],function(){
        var $ = layui.jquery,
                form = layui.form(),
                common = layui.common,
                layer = parent.layer === undefined ? layui.layer : parent.layer;

        /**表单验证*/
        form.verify({
            userLoginName: function(value, item){
                //验证登陆账号
                if(!new RegExp("^[0-9A-Za-z_]{2,15}$").test(value)){
                    return '登陆账号只能为英文、数字、下划线，长度2-7位';
                }
                //验证登陆账号是否存在

            },
            userName: function(value, item){
                //验证用户名
                if(!new RegExp("^([\u4e00-\u9fa5]){2,10}$").test(value)){
                    return '用户姓名只能为中文，长度2-7位';
                }
            }
        });

        /**保存*/
        form.on("submit(saveUser)",function(data){
            var pageFlag = $("#pageFlag").val();
            var userSaveLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            //登陆验证
            $.ajax({
                url : '${ctx}/user/ajax_save_user',
                type : 'post',
                async: false,
                data : data.field,
                success : function(data) {
//                    console.log(data);
                    if(data.code=="SUCCESS"){
                        top.layer.close(userSaveLoading);
                        if(pageFlag == 'addPage'){
                            common.cmsLaySucMsg("保存成功,默认密码123456,请及时修改")
                        }else {
                            common.cmsLaySucMsg("保存成功")
                        }
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
                    }else{
                        top.layer.close(userSaveLoading);
                        common.cmsLayErrorMsg(data.msg);
                    }
                },error:function(data){
                    top.layer.close(index);

                }
            });
            return false;
        });
        //取消
        $("#cancle").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        });

    });

</script>
</body>
</html>