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
    <%--<link rel="shortcut icon" href="/static/img/favicon.ico">--%>

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
                    <form class="layui-form" id="userSearchForm">
                        <div class="layui-input-inline">
                            <label>账号</label>
                        </div>
                        <div class="layui-input-inline" style="width:145px;">
                            <input type="text" name="username" value="" placeholder="请输入账号" class="layui-input search_input">
                        </div>
                        <div class="layui-input-inline">
                            <label>姓名</label>
                        </div>
                        <div class="layui-input-inline" style="width:145px;">
                            <input type="text" name="name" value="" placeholder="请输入姓名关键字" class="layui-input search_input">
                        </div>
                        <a class="layui-btn userSearchList_btn" lay-submit lay-filter="userSearchFilter"><i class="layui-icon larry-icon larry-chaxun7"></i>查询</a>
                    </form>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal userAdd_btn"> <i class="layui-icon larry-icon larry-xinzeng1"></i>新增用户</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal excelUserExport_btn"  style="background-color:#5FB878"> <i class="layui-icon larry-icon larry-danye"></i>导出</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-danger userBatchFail_btn"><i class="layui-icon larry-icon larry-shanchu"></i>批量失效</a>
                </div>
            </blockquote>
            <div class="larry-separate"></div>
            <!-- 用户列表 -->
            <div class="layui-tab-item layui-field-box layui-show">
                <div class="layui-form">
                    <table class="layui-table" lay-even="" lay-skin="row">
                        <thead >
                        <tr>
                            <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
                            <th>登陆账号</th>
                            <th>用户姓名</th>
                            <th>用户状态</th>
                            <th>拥有角色</th>
                            <%--<th>创建人</th>--%>
                            <%--<th>创建时间</th>--%>
                            <%--<th>修改人</th>--%>
                            <%--<th>修改时间</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="userTbody"></tbody>
                    </table>
                </div>
                <div class="larry-table-page clearfix" id="userPage"></div>
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


        /**加载用户列表信息*/
        userPageList(0);

        /**全选*/
        form.on('checkbox(allChoose)', function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function (index, item) {
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });
        /**添加用户*/
        $(".userAdd_btn").click(function(){
            var url = "${ctx}/user/user_add";
            common.cmsLayOpen('新增用户',url,'550px','265px');
        });
        /**修改用户*/
        $("body").on("click",".user_edit",function(){
            var userId = $(this).attr("data-id");
            var url =  "${ctx}/user/user_update?userId="+userId;
            common.cmsLayOpen('编辑用户',url,'550px','265px');
        });

        /**用户失效*/
        $("body").on("click",".user_fail",function(){
            var userId = $(this).attr("data-id");
            var userStatus = $(this).attr("data-status");
            var currentUserId = '${LOGIN_NAME.userId}';/*当前登录用户的ID*/
            if(userStatus == 1){
                common.cmsLayErrorMsg("当前用户已失效");
                return false;
            }
            if(userId == currentUserId){
                common.cmsLayErrorMsg("当前登陆用户不能被失效");
                return false;
            }

            var url = "${ctx}/user/ajax_user_fail.do";
            var param = {userId:userId};
            common.ajaxCmsConfirm('系统提示', '确定失效用户，并解除与角色绑定关系吗?',url,param);

        });
        /**分配角色*/
        $("body").on("click",".user_grant",function(){
            var userId = $(this).attr("data-id");
            var userStatus = $(this).attr("data-status");
            if(userStatus == 1){
                common.cmsLayErrorMsg("当前用户已失效,不能被分配角色");
                return false;
            }
            var url =  "${ctx}/user/user_grant?userId="+userId;
            common.cmsLayOpen('分配角色',url,'500px','440px');


        });
        /**导出用户信息*/
        $(".excelUserExport_btn").click(function(){
            var url = '${ctx}/user/excel_users_export';
            $("#userSearchForm").attr("action",url);
            $("#userSearchForm").submit();
        });
        //
        /**查询*/
        $(".userSearchList_btn").click(function(){
            //监听提交
            form.on('submit(userSearchFilter)', function (data) {
                userPageList(0,data.field.username,data.field.name);
            });

        });
        /**批量失效*/
        $(".userBatchFail_btn").click(function(){
            if($("input:checkbox[name='userIdCK']:checked").length == 0){
                common.cmsLayErrorMsg("请选择要失效的用户信息");
            }else{
                var isCreateBy = false;
                var userStatus = false;
                var currentUserName = '${LOGIN_NAME.userId}';
                var userIds = [];

                $("input:checkbox[name='userIdCK']:checked").each(function(){
                    userIds.push($(this).val());
                    //不能失效当前登录用户
                    if(currentUserName != $(this).val()){
                        isCreateBy = true;
                    }else{
                        isCreateBy = false;
                        return false;
                    }
                    //用户已失效
                    if($(this).attr('alt') == 0){
                        userStatus = true;
                    }else{
                        userStatus = false;
                        return false;
                    }

                });
                if(isCreateBy==false){
                    common.cmsLayErrorMsg("当前登录用户不能被失效,请重新选择");

                    return false;
                }
                if(userStatus==false){
                    common.cmsLayErrorMsg("当前选择的用户已失效");

                    return false;
                }
                var url = "${ctx}/user/ajax_user_batch_fail.do";
                var param = {userIds:userIds};
                common.ajaxCmsConfirm('系统提示', '确定失效当前用户，并解除与角色绑定关系吗?',url,param);
            }



        });

        /**加载用户信息**/
        function userPageList(curr,username,name){
            var pageLoading = layer.load(2);
            $.ajax({
                url : '${ctx}/user/ajax_user_list',
                type : 'post',
                data :{
                    page: curr || 0 ,   //当前页
                    rows: 10 ,          //每页显示7条数据
                    username: username,
                    name: name
                },
                success : function(data) {
                    FastJson.format(data);
                    console.log(data);
                    if(data.code=="SUCCESS"){
                        $("#userTbody").text('');//先清空原先内容
                        var pdata =data.data;
                        $(pdata).each(function(index,item){

                            //用户状态
                            var userStatusLable;
                            switch (item.state){
                                case 0:
                                    userStatusLable = '<span class="label label-success ">0-有效</span>';
                                    break;
                                case 1:
                                    userStatusLable = '<span class="label label-danger ">1-失效</span>'
                                    break;
                            }
                            //拥有角色
                            var roleNamesLable="";
                            if(item.roleList.length!= 0){
                                for(var j=0;j<item.roleList.length;j++)
                                    roleNamesLable += item.roleList[j].description +",";
                            }
                            //操作按钮
                            var opt ='<div class="layui-btn-group">';
                            opt+=  '<a class="layui-btn layui-btn-mini user_edit" data-id="'+item.userid+'"><i class="layui-icon larry-icon larry-bianji2"></i> 编辑</a>';
                            opt+=  '<a class="layui-btn layui-btn-mini layui-btn-warm  user_grant" data-id="'+item.userid+'" data-status= "'+item.state+'"><i class="layui-icon larry-icon larry-jiaoseguanli3"></i>角色</a>';
                            opt+=  '<a class="layui-btn layui-btn-mini layui-btn-danger  user_fail" data-id="'+item.userid+'" data-status= "'+item.state+'"><i class="layui-icon larry-icon larry-ttpodicon"></i>失效</a>';
                            opt+= '</div>';
                            //组装table
                            $("#userTbody").append(
                                '<tr>'+
                                '<td><input name="userIdCK" lay-skin="primary" type="checkbox"  alt="'+item.state+'" value="'+item.userid+'"></td>'+
                                '<td title="'+objNull(item.username)+'">'+objNull(item.username)+'</td>'+
                                '<td title="'+objNull(item.name)+'">'+objNull(item.name)+'</td>'+
                                '<td>'+userStatusLable+'</td>'+
                                '<td title="'+objNull(item.roleNames)+'" style="text-align:left;">'+objNull(roleNamesLable)+'</td>'+
//                                '<td>'+item.creator+'</td>'+
//                                '<td>'+item.createTime+'</td>'+
//                                '<td>'+objNull(item.modifier)+'</td>'+
//                                '<td>'+objNull(item.updateTime)+'</td>'+
                                '<td>'+opt+'</td>'+
                                '</tr>'
                            );
                            //重新渲染form
                            form.render();

                        });
                        //分页
                        laypage({
                            cont: 'userPage',
                            pages: data.totalsize,
                            curr: curr || 1, //当前页
                            groups: 10, //连续显示分页数
                            skip: true,
                            jump: function(obj, first){ //触发分页后的回调
                                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                    userPageList(obj.curr);
                                }
                            }
                        });
                        layer.close(pageLoading);
                    }
                }

            });
        };
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