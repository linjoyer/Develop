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
    <div class="larry-personal" >
        <div class="layui-tab" >
            <blockquote class="layui-elem-quote mylog-info-tit">
                <div class="layui-inline">
                    <form class="layui-form" id="resSearchForm">
                        <div class="layui-input-inline" style="width:110px;">
                            <select name="searchTerm" >
                                <option value="resNameTerm">菜单名称</option>
                            </select>
                        </div>
                        <div class="layui-input-inline" style="width:145px;">
                            <input type="text" name="searchContent" value="" placeholder="请输入关键字" class="layui-input search_input">
                        </div>
                        <a class="layui-btn resSearchList_btn" lay-submit lay-filter="resSearchFilter"><i class="layui-icon larry-icon larry-chaxun7"></i>查询</a>
                    </form>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal  resAdd_btn"> <i class="layui-icon larry-icon larry-xinzeng1"></i>新增菜单</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-normal excelResExport_btn"  style="background-color:#5FB878"> <i class="layui-icon larry-icon larry-danye"></i>导出</a>
                </div>
                <div class="layui-inline">
                    <a class="layui-btn layui-btn-danger resBatchFail_btn"><i class="layui-icon larry-icon larry-shanchu"></i>批量失效</a>
                </div>
            </blockquote>
            <div class="larry-separate"></div>
            <!-- 菜单列表 -->
            <div class="layui-tab-item layui-field-box layui-show" >
                <div class="layui-form">
                    <table class="layui-table" lay-even="" lay-skin="row">
                        <thead >
                            <tr>
                                <th><input name="" lay-skin="primary" lay-filter="allChoose" type="checkbox"></th>
                                <th>菜单名称</th>
                                <th>菜单编码</th>
                                <th>菜单状态</th>
                                <th>菜单路径</th>
                                <th>菜单类型</th>
                                <th>上级菜单</th>
                                <%--<th>创建时间</th>--%>
                                <%--<th>修改时间</th>--%>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody id="resTbody">
                        </tbody>
                    </table>
                </div>
                <div class="larry-table-page clearfix" id="resPage"></div>
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

        /**加载菜单列表信息*/
        rolePageList(0);


        /**全选*/
        form.on('checkbox(allChoose)', function (data) {
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function (index, item) {
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });

        /**导出资源权限信息*/
        $(".excelResExport_btn").click(function(){
            var url = '${ctx}/per/excel_per_export';
            $("#resSearchForm").attr("action",url);
            $("#resSearchForm").submit();
        });
        /**新增菜单*/
        $(".resAdd_btn").click(function(){
        	
        	var url = "${ctx}/per/res_edit.do";
            common.cmsLayOpen('新增菜单',url,'750px','470px','top');
        });
        /* 编辑菜单 */
        $("body").on("click",".res_edit",function(){
            var resId = $(this).attr("data-id");
            var url = "${ctx}/per/res_update.do?id="+resId;
            common.cmsLayOpen('编辑菜单',url,'750px','470px','top');
        });

        /**加载菜单信息*/
        function rolePageList(curr,searchTerm,searchContent){
            var pageLoading = layer.load(2);
            $.ajax({
                url : '${ctx}/per/ajax_per_list',
                type : 'post',
                data :{
                    page: curr ||0 ,   //当前页
                    rows: 10 ,         //每页显示四条数据
                    searchTerm: searchTerm,
                    searchContent: searchContent
                },
                success : function(data) {
//                    console.log(data);
                    if(data.code=="SUCCESS" ){
                        $("#resTbody").text('');//先清空原先内容
                        var pdata =data.data;
                        $(pdata).each(function(index,item){


                            //菜单状态
                            var resStatusLable;
                            switch (item.available){
                                case true:
                                    resStatusLable = '<span class="label label-success ">0-有效</span>';
                                    break;
                                case false:
                                    resStatusLable = '<span class="label label-danger ">1-失效</span>'
                                    break;
                            }
                            //菜单类型
                            var menuTypeLable;
                            switch (item.resourceType){
                                case 'menu':
                                    menuTypeLable = '<span class="label label-warning">菜单</span>';
                                    break;
                                case 'button':
                                    menuTypeLable = '<span class="label label-info ">按钮</span>'
                                    break;
                            }

                            var resLinkAddressLable;
                            if(objNull(item.url) != "" && item.url.length > 18){
                                resLinkAddressLable = item.url.substring(0,18) +"...";

                            }else{
                                resLinkAddressLable = item.url;
                            }
                            //操作
                            var opt ='<div class="layui-btn-group">';
                            opt+=  '<a class="layui-btn layui-btn-mini res_edit" data-id="'+item.id+'"><i class="layui-icon larry-icon larry-bianji2"></i> 编辑</a>';
                            opt+=  '<a class="layui-btn layui-btn-mini layui-btn-danger  links_del" data-id=""><i class="layui-icon larry-icon larry-ttpodicon"></i>失效</a>';
                            opt+= '</div>';

                            $("#resTbody").append(
                                    '<tr>'+
                                    '<td><input name="" lay-skin="primary" type="checkbox"></td>'+
                                    '<td  title="'+objNull(item.name)+'">'+objNull(item.name)+'</td>'+
                                    '<td>'+item.permission+'</td>'+
                                    '<td>'+resStatusLable+'</td>'+
                                    '<td  title="'+objNull(item.url)+'">'+objNull(resLinkAddressLable)+'</td>'+
                                    '<td>'+menuTypeLable+'</td>'+
                                    '<td>'+objNull(item.parentid)+'</td>'+
//                                    '<td>'+item.createTime+'</td>'+
//                                    '<td>'+objNull(item.modifyTime)+'</td>'+
                                    '<td>'+opt+'</td>'+
                                    '</tr>'
                            );
                            form.render();

                        });
                        laypage({
                            cont: 'resPage',
                            pages:  pdata.totalSize,
                            curr: curr || 1, //当前页
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