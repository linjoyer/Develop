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
    <link rel="shortcut icon" href="${ctx}/static/img/favicon.ico">
	<script src="${ctx}/js/plugin/layui/layui.js"></script>


</head>
<body class="childrenBody" style="font-size: 12px;">
<form class="layui-form layui-form-pane">
    <input id="id" name="id" type="hidden" value="${role.id}">
	<input id="description" name="description" type="hidden" value="${role.description}">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="role" lay-verify="required|roleName" maxlength="10" value="${role.role}" placeholder="请输入角色名称">
        </div>
    </div>
    <div class="layui-form-item" pane>
        <label class="layui-form-label">角色状态</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="radio" name="available" value="1" title="有效" checked disabled>
                <input type="radio" name="available" value="0" title="失效" disabled>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' and  role.available == 'true' }">
                <input type="radio" name="available" value="1" title="有效" disabled  <c:if test="${role.available ==true }">checked</c:if>/>
                <input type="radio" name="available" value="0" title="失效" disabled  <c:if test="${role.available ==false }">checked</c:if>/>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' and  role.available == 'false' }">
                <input type="radio" name="available" value="1" title="有效"  <c:if test="${role.available ==true }">checked</c:if>/>
                <input type="radio" name="available" value="0" title="失效"  <c:if test="${role.available ==false }">checked</c:if>/>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea name="roleRemark" placeholder="请输入内容" class="layui-textarea" maxlength="50" style="resize:none;min-height:40px;">${role.roleRemark}</textarea>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: center;">
            <button class="layui-btn" lay-submit="" lay-filter="saveRole">保存</button>
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
            roleName: function(value, item){
                //角色名称
                if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                    return '角色名称不能有特殊字符';
                }
            }
        });

        //保存
        form.on("submit(saveRole)",function(data){
            var roleSaveLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            $.ajax({
                url : '${ctx}/role/ajax_save_role.do',
                type : 'post',
                async: false,
                data : data.field,
                success : function(data) {
                    if(data.code == "SUCCESS"){
                        top.layer.close(roleSaveLoading);
                        common.cmsLaySucMsg("角色信息保存成功！");
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引                        
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
                    }else{
                        top.layer.close(roleSaveLoading);
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