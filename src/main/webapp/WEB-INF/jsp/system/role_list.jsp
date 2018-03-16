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
    <link rel="stylesheet" href="${ctx}/css/base/global.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/base/personal.css" media="all">
    <script src="${ctx}/js/plugin/layui/layui.js"></script>
	<script src="${ctx}/js/base/FastJson.js"></script>

<body>
<div class="larry-grid larryTheme-A">
    <div class="larry-personal">
        <div class="layui-tab">
            <blockquote class="layui-elem-quote mylog-info-tit">
                <div class="layui-inline">
                    <form class="layui-form" id="roleSearchForm">
                        <div class="layui-input-inline" style="width:110px;">
                            <select name="searchTerm" >
                                <option value="roleNameTerm">角色名称</option>
                            </select>
                        </div>
                        <div class="layui-input-inline" style="width:145px;">
                            <input type="text" name="searchContent" value="" placeholder="请输入关键字" class="layui-input search_input">
                        </div>
                        <a class="layui-btn roleSearchList_btn" lay-submit lay-filter="roleSearchFilter"><i class="layui-icon larry-icon larry-chaxun7"></i>查询</a>
                    </form>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal  roleAdd_btn"> <i class="layui-icon larry-icon larry-xinzeng1"></i>新增角色</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal excelRoleExport_btn"  style="background-color:#5FB878"> <i class="layui-icon larry-icon larry-danye"></i>导出</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-danger roleBatchFail_btn"><i class="layui-icon larry-icon larry-shanchu"></i>批量失效</a>
                </div>
            </blockquote>
            <div class="larry-separate"></div>
            <!-- 角色列表 -->
            <div class="layui-tab-item layui-field-box layui-show">
                <div class="layui-form">
                    <table class="layui-table" lay-even="" lay-skin="row">
                        <thead >
                        <tr>
                            <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
                            <th>角色名称</th>
                            <th>角色状态</th>
                            <th>菜单资源</th>
                            <th>角色说明</th>
                            <%--<th>创建人</th>--%>
                            <%--<th>创建时间</th>--%>
                            <%--<th>修改人</th>--%>
                            <%--<th>修改时间</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="roleTbody">
                        </tbody>
                    </table>
                </div>
                <div class="larry-table-page clearfix" id="rolePage"></div>
            </div>

        </div>
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

        /**加载角色列表信息*/
        rolePageList(0);

        /**全选*/
        form.on('checkbox(allChoose)', function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function (index, item) {
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });


        /**添加角色*/
        $(".roleAdd_btn").click(function(){

            var url = "${ctx}/role/role_add.do";
            common.cmsLayOpen('新增角色',url,'550px','340px');
        });


        /**修改角色*/
        $("body").on("click",".role_edit",function(){
            var roleId = $(this).attr("data-id");
            var url = "${ctx}/role/role_update.do?roleId="+roleId;
            common.cmsLayOpen('编辑角色',url,'550px','340px');
        });

        /**角色失效*/
        $("body").on("click",".role_fail",function(){
            var roleId = $(this).attr("data-id");
            var roleStatus = $(this).attr("data-status");
            if(roleStatus == 'false'){
                common.cmsLayErrorMsg("当前角色已失效");
                return false;
            }

            var url = "${ctx}/role/ajax_role_fail.do";
            var param = {roleId:roleId};
            common.ajaxCmsConfirm('系统提示', '失效角色、解除角色、用户、菜单绑定关系?',url,param);

        });

        /**角色授权*/
        $("body").on("click",".role_grant",function(){
            var roleId = $(this).attr("data-id");
            var roleStatus = $(this).attr("data-status");
            if(roleStatus == 1){
                common.cmsLayErrorMsg("当前角色已失效,不能授权");
                return false;
            }
            var url =  "${ctx}/role/role_grant.do?roleId="+roleId;
            common.cmsLayOpen('角色授权',url,'255px','520px');

        });

        /**导出角色信息*/
        $(".excelRoleExport_btn").click(function(){
            var url = '${ctx}/role/excel_role_export';
            $("#roleSearchForm").attr("action",url);
            $("#roleSearchForm").submit();
        });

        /**查询*/
        $(".roleSearchList_btn").click(function(){
            //监听提交
            form.on('submit(roleSearchFilter)', function (data) {
                rolePageList(0,data.field.searchTerm,data.field.searchContent);
            });

        });

        /**批量失效*/
        $(".roleBatchFail_btn").click(function(){
            if($("input:checkbox[name='roleIdCK']:checked").length == 0){
                common.cmsLayErrorMsg("请选择要失效的角色信息");
            }else{
                var roleStatus = false;
                var roleIds = [];

                $("input:checkbox[name='roleIdCK']:checked").each(function(){
                    roleIds.push($(this).val());
                    //角色已失效
                    if($(this).attr('alt') == 'true'){
                        roleStatus = true;
                    }else{
                        roleStatus = false;
                        return false;
                    }

                });
                if(roleStatus==false){
                    common.cmsLayErrorMsg("当前选择的角色已失效");
                    return false;
                }
                var url = "${ctx}/role/ajax_role_batch_fail.do";
                var param = {roleIds:roleIds};
                common.ajaxCmsConfirm('系统提示', '失效角色、解除角色、用户、菜单绑定关系?',url,param);
            }

        });


        /**加载角色信息*/
        function rolePageList(curr,searchTerm,searchContent){
            var pageLoading = layer.load(2);
            $.ajax({
                url : '${ctx}/role/ajax_role_list',
                type : 'post',
                data :{
                    page: curr || 0 ,   //当前页
                    rows: 10 ,          //每页显示四条数据
                    searchTerm: searchTerm,
                    searchContent: searchContent
                },
                success : function(data) {
                    FastJson.format(data);
                    console.log(data);
                    if(data.code=="SUCCESS" ){
                        $("#roleTbody").text('');//先清空原先内容
                        var pdata =data.data;
                        $(pdata).each(function(index,item){
                            //角色名称
                            var roleNameLable;
                            if(objNull(item.roleName) != "" && item.roleName.length > 9){
                                roleNameLable = item.roleName.substring(0,9) +"...";

                            }else{
                                roleNameLable = item.roleName;
                            }
                            //角色状态
                            var roleStatusLable="";
                            switch (item.available){
                                case true:
                                    roleStatusLable = '<span class="label label-success">0-有效</span>';
                                    break;
                                case false:
                                    roleStatusLable = '<span class="label label-danger">1-失效</span>'
                                    break;
                            }

                            //拥有资源
                            var resourceNamesLable="";
                            if(item.permissions.length!= 0){
                                for(var j=0;j<item.permissions.length;j++)
                                    resourceNamesLable+= item.permissions[j].name+",";
                            }
                            if(resourceNamesLable.length>30)
                                resourceNamesLable=resourceNamesLable.substring(0,30)+"...";
                            //角色说明
                            var roleRemarkLable;
                            if(objNull(item.roleRemark) != "" && item.roleRemark.length > 12){
                                roleRemarkLable = item.roleRemark.substring(0,12) +"...";

                            }else{
                                roleRemarkLable = item.roleRemark;
                            }
                            var opt ='<div class="layui-btn-group">';
                            opt+=  '<a class="layui-btn layui-btn-mini role_edit" data-id="'+item.id+'"><i class="layui-icon larry-icon larry-bianji2"></i> 编辑</a>';
                            opt+=  '<a class="layui-btn layui-btn-mini layui-btn-warm  role_grant" data-id="'+item.id+'" data-status= "'+item.available+'"><i class="layui-icon larry-icon larry-quanxianguanli"></i>权限</a>';
                            opt+=  '<a class="layui-btn layui-btn-mini layui-btn-danger  role_fail" data-id="'+item.id+'" data-status= "'+item.available+'"><i class="layui-icon larry-icon larry-ttpodicon"></i>失效</a>';
                            opt+= '</div>';
                            $("#roleTbody").append(
                                '<tr>'+
                                '<td><input name="roleIdCK" lay-skin="primary" type="checkbox" alt="'+item.available+'" value="'+item.id+'"></td>'+
                                '<td title="'+objNull(item.description)+'">'+objNull(item.description)+'</td>'+
                                '<td>'+roleStatusLable+'</td>'+
                                '<td title="'+objNull(item.resourceNames)+'">'+objNull(resourceNamesLable)+'</td>'+
                                '<td title="'+objNull(item.roleRemark)+'">'+objNull(roleRemarkLable)+'</td>'+
//                                '<td>'+item.creator+'</td>'+
//                                '<td>'+item.createTime+'</td>'+
//                                '<td>'+objNull(item.modifier)+'</td>'+
//                                '<td>'+objNull(item.modifierTime)+'</td>'+
                                '<td>'+opt+'</td>'+
                                '</tr>'
                            );
                            form.render();
                        });
                        laypage({
                            cont: 'rolePage',
                            pages:  data.totalSize,
                            curr: curr || 1, //当前页
                            groups: 8, //连续显示分页数
                            skip: true,
                            jump: function(obj, first){ //触发分页后的回调
                                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                    rolePageList(obj.curr);
                                }
                            }
                        });
                        layer.close(pageLoading);

                    }
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