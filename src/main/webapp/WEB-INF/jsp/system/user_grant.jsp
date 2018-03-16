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

    <link rel="stylesheet" href="${ctx}/css/base/main.css">
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css">
    <script src="${ctx}/js/plugin/layui/layui.js"></script>
    <script src="${ctx}/js/base/FastJson.js"></script>

</head>
<body class="childrenBody" style="font-size: 12px;">
<div class="row" style="height:330px;overflow: auto;">
    <div class="sysNotice col" style="width: 49%;">
        <blockquote class="layui-elem-quote title">待分配的角色</blockquote>
        <div class="layui-form" style="height: 270px;  border: 1px solid #e2e2e2;">
            <table class="layui-table" style="border: 0px;" >
                <colgroup>
                    <col width="10">
                </colgroup>
                <tbody id="undistributedRole">
                </tbody>
            </table>
        </div>

    </div>
    <div style="width:20px;  position: absolute; top: 138px; left:240px;">
        <a id="selectRoleRight" title="右移" style="cursor: pointer"><i class="layui-icon" style="color:#009688;">&#xe602;</i></a>
        <a id="selectRoleLeft"  title="左移" style="cursor: pointer"><i class="layui-icon" style="color:#009688;">&#xe603;</i></a>
    </div>
    <div class="sysNotice col" style="width: 49%;margin-left: 8px;">
        <blockquote class="layui-elem-quote title">已分配的角色</blockquote>
        <div class="layui-form" style="height: 270px;  border: 1px solid #e2e2e2;">
            <table class="layui-table" style="border: 0px;" >
                <colgroup>
                    <col width="10">
                </colgroup>
                <tbody id="deceasedRole">


                </tbody>
            </table>
        </div>
    </div>
</div>
<input id="userid"  name="userid" type="hidden" value="${user.userid}">
<input id="roleIds" name="roleIds" type="hidden" value="">
<div class="layui-form-item" style="text-align: center;">
    <button class="layui-btn" id="saveRole">保存</button>
    <button type="layui-btn" id="cancle" class="layui-btn layui-btn-primary">取消</button>
</div>

<script type="text/javascript">
    layui.config({
        base : "${ctx}/js/base/"
    }).use(['form','layer','jquery','common'],function(){
        var $ = layui.jquery,
                form = layui.form(),
                common = layui.common,
                layer = parent.layer === undefined ? layui.layer : parent.layer;

        //查询待分配的角色
        getUndistributedRoleList();
        //查询已分配的角色*
        getDeceasedRolelist();

        /**查询待分配的角色*/
        function getUndistributedRoleList(){
            var pageLoading = layer.load(2);
            var roleIds = $("#userid").val();
            $.ajax({
                url : '${ctx}/user/ajax_undistributed_role_list',
                type : 'post',
                async: false,
                data :{
                    userid: roleIds
                },
                success : function(data) {
                    FastJson.format(data);
                    console.log(data);
                    if (data.code=="SUCCESS") {
                        var pdata = data.data;
                        $(pdata).each(function(index,item){
                            $("#undistributedRole").append(
                                    '<tr>'+
                                        '<td><input name="undistributed" lay-skin="primary" type="checkbox" alt="'+item.description+'"  value="'+item.id+'"></td>'+
                                        '<td>'+item.description+'</td>'+
                                    '</tr>'
                            );
                            form.render();
                        });
                    }
                    layer.close(pageLoading);
                },
                error: function() {
                    layer.close(pageLoading);
                }
            });

        }
        function getDeceasedRolelist() {
            var pdata = '${user}';
            pdata=$.parseJSON(pdata);
            $(pdata.roleList).each(function(index,item){
                $("#deceasedRole").append(
                    '<tr>'+
                    '<td><input name="deceased" lay-skin="primary" type="checkbox" alt="'+item.role+'" value="'+item.id+'"></td>'+
                    '<td>'+item.description+'</td>'+
                    '</tr>'
                );
                form.render();

            });
        }

        /**角色选择右移*/
        $("#selectRoleRight").click(function(){

            if($("input:checkbox[name='undistributed']:checked").length > 0){
                $("input:checkbox[name='undistributed']:checked").each(function(){
                    var roleName = $(this).attr('alt');
                    var roleId = $(this).val();

                    $(this).parent().parent().closest('tr').remove();
                    $("#deceasedRole").append(
                            '<tr>'+
                            '<td><input name="deceased" lay-skin="primary" type="checkbox" alt="'+roleName+'" value="'+roleId+'"></td>'+
                            '<td>'+roleName+'</td>'+
                            '</tr>'
                    );
                    form.render();


                });
            }else{
                common.cmsLayErrorMsg("请选择需要分配的角色信息");
            }
        });

        /**角色选择左移*/
        $("#selectRoleLeft").click(function(){

            if($("input:checkbox[name='deceased']:checked").length > 0){
                $("input:checkbox[name='deceased']:checked").each(function(){
                    var roleName = $(this).attr('alt');
                    var roleId = $(this).val();

                    $(this).parent().parent().closest('tr').remove();
                    $("#undistributedRole").append(
                            '<tr>'+
                            '<td><input name="undistributed" lay-skin="primary" type="checkbox" alt="'+roleName+'" value="'+roleId+'"></td>'+
                            '<td>'+roleName+'</td>'+
                            '</tr>'
                    );
                    form.render();


                });
            }else{
                common.cmsLayErrorMsg("请选择需要移除的已分配角色信息");

            }
        });


        /**角色保存*/
        $("#saveRole").click(function(){
            //角色ID
            var ids = [];
            //得到全部已分配的角色Id
            $("input:checkbox[name='deceased']").each(function(){
                var roleId = $(this).val();
                ids.push(roleId);
            });

            $('#roleIds').val(ids);

            var userRoleLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

            $.ajax({
                url : '${ctx}/user/ajax_save_user_role',
                type : 'post',
                async: false,
                data : {
                    userId:$('#userid').val(),
                    roleIds:$('#roleIds').val()
                },
                success : function(data) {
                    console.log(data);
                    if(data.code == "SUCCESS"){
                        top.layer.close(userRoleLoading);
                        common.cmsLaySucMsg("用户角色信息保存成功.");
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
                    }else{
                        top.layer.close(userRoleLoading);
                        common.cmsLayErrorMsg(data.msg);
                    }
                },error:function(data){
                    top.layer.close(userRoleLoading);

                }
            });

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