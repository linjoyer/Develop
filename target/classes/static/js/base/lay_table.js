/*
  author：shz
  time：2017-07-14
   创建表格
   如果页面需要创建多个表格，则使用该语句：var Table_Layui1=Table_Layui,然后使用对象Table_Layui1去操作
   */
var Table_Layui = {
    //Table的模块ID--建议使用Div
    TablePanel: "",
    //是否需要序号
    CountNumberBool: false,
    //表格的列标题参数：
    //{ txtName: "列标题的名称",
    //ValueCode: "列值的取值编码",
    //width: 列的长度,
    //Style: "自定义列的样式"
    //ValueDeal: 自定义的处理值得方法 }
    Column: [
        {
            txtName: "", ValueCode: "", width: 0, Style: "", ValueDeal: function (value) {
            return value;
        }
        }
    ],
    //编辑方法
    Edit: "",
    //删除方法
    Delete: "",
    //编辑方法
    EditCol: "",
    data: [],
    //当前页
    PageIndex: 1,
    //每页展示的数据量
    PageSize: 10,
    //总数据量，假如该值为0，则表示不需要创建页码
    SumDateCounte: 0,
    //根据页码查询指定数据的方法
    SelectDataByPageIndex: function () {

    },
    //获取指定行数据
    GetRowData: function (index) {
        return this.data[index];
    },
    //页码
    Page_Layui: Page_Layui,
    //创建Layui的表格框架
    CreateTableFrame: function () {
        var width = 0;
        var colgroupHtml = "";
        var theadHtml = "";
        //序号字段
        if (this.CountNumberBool) {
            width = 50;
            colgroupHtml = "<col style='width:50;'>";
            theadHtml = "<th>序号</th>";
        }
        //列标题
        var Column = this.Column;
        for (var i = 0; i < Column.length; i++) {
            width += Column[i].width;
            colgroupHtml += "<col  width='" + Column.width + "'>";
            theadHtml += "<th style='" + (Column[i].Style == undefined ? "" : Column[i].Style) + "'>" + Column[i].txtName + "</th>";
        }
        //控制按钮 的列标题
        if (this.Edit != "" || this.Delete != "") {
            width += 120;
            colgroupHtml += "<col  width='160'>";
            theadHtml += "<th>操作</th>";
        }
        //数据
        var tbody = "";
        if (this.data != undefined && this.data != null) {
            if (this.data.length > 0) {
                // alert("加载数据"+this.PageIndex*this.PageSize);
                for (var i = (this.PageIndex - 1) * this.PageSize; i < (this.PageIndex * this.PageSize > this.data.length ? this.data.length : this.PageIndex * this.PageSize); i++) {
                    // for(var i=0;i<this.data.length;i++){
                    tbody += "<tr>";
                    //序号
                    if (this.CountNumberBool) {
                        tbody += "<td style='text-align: center;'>" + ((parseFloat(this.PageIndex) - 1) * this.PageSize + i + 1) + "</td>";
                    }
                    for (var j = 0; j < Column.length; j++) {
                        //单元格的样式
                        tbody += "<td style=\'" + (Column[j].Style == undefined ? "" : Column[j].Style) + "\'>";
                        var value = "";
                        //单元格的数据
                        if (Column[j].ValueDeal != undefined) {
                            value = Column[j].ValueDeal(this.data[i][Column[j].ValueCode]);
                        }
                        else {
                            value = this.data[i][Column[j].ValueCode];
                        }
                        tbody += (value == null ? "" : value) + "</td>";
                    }
                    var ischecked = "";
                    if (this.data[i].mIseffec == "0") {
                        ischecked = "";
                    } else {
                        ischecked = "checked";
                    }
                    //控制按钮
                    if (this.Edit != "" || this.Delete != "") {
                        tbody += "<td style='width: 10%;'>";
                        //编辑按钮功能
                        if (this.Edit != "") {
                            tbody += "<a class='layui-btn layui-btn-mini news_edit' href='javascript:void(0)'  onclick='(" + this.Edit + ")(" + i + ")' ><i class='iconfont icon-edit'></i> 编辑</a>";
                        }
                        //编辑字段按钮功能
                        if (this.EditCol != "") {
                            tbody += "<a class='layui-btn layui-btn-mini news_edit' href='javascript:void(0)' onclick='(" + this.EditCol + ")(" + i + ")' ><i class='layui-icon'></i>停止</a>";
                        }
                        //删除按钮功能
                        if (this.Delete != "") {
                            // tbody += "<button class='layui-btn layui-btn-danger layui-btn-mini links_del' onclick='(" + this.Delete + ")(" + i + ")' > <i class='layui-icon'>&#x1006;</i>停止</button>";
                            tbody += "<a class='layui-btn layui-btn-danger layui-btn-mini news_del' href='javascript:void(0)' onclick='(" + this.Delete + ")(" + i + ")' ><i class='layui-icon'></i> 删除</a>";
                        }

                        tbody += "</td>";
                    }
                    tbody += "</tr>";
                }
            }
        }
        //初始化页码对象
        this.Page_Layui = Page_Layui;
        //初始化分页容器的ID
        if (this.Page_Layui.PagePlaneID == "") {
            this.Page_Layui.PagePlaneID = new Date().getTime();
        }

        // //需要创建页码
        // if (this.SumDateCounte > 0) {
        //     width = width < 1000 ? 1000 : width;//控制容器的最小长度需要适配页码
        // }


        var html = "<div style='width:100%;'>"
            + " <table class='layui-table'>"
            + "    <colgroup>"
            + colgroupHtml
            + "    </colgroup>"
            + "    <thead> "
            + "        <tr>"
            + theadHtml
            + "        </tr>"
            + "    </thead>"
            + "    <tbody >" + tbody + "</tbody>"
            + " </table>"
            + " <div id=\"" + this.Page_Layui.PagePlaneID + "\"></div>"
            + "</div>";
        $("#" + this.TablePanel).html(html);

        //创建页码
        if (this.SumDateCounte > 0) {
            this.Page_Layui.PageIndex = this.PageIndex;
            this.Page_Layui.PageSize = this.PageSize;
            this.Page_Layui.SumDateCounte = this.SumDateCounte;
            this.Page_Layui.SelectDataByPageIndex = this.SelectDataByPageIndex;
            this.Page_Layui.CreatePage();//创建页码
        }

    }
};
/*
 创建页码
 如果页面需要创建多个页码，则使用该语句：var Page_Layui1=Page_Layui,然后使用对象Page_Layui1去操作
 */
var Page_Layui = {
    //存放页码的容器
    PagePlaneID: "",
    //当前页
    PageIndex: 1,
    //每页展示的数据量
    PageSize: 10,
    //数据的总数量
    SumDateCounte: 0,
    //根据页码查询数据的方法
    SelectDataByPageIndex: function (PageIndex) {
    },
    //刷新当前页数据
    Refresh: function () {
        this.SelectDataByPageIndex(this.PageIndex);
    },
    CreatePage: function () {
        $("#" + this.PagePlaneID).css("width", "100%");
        $("#" + this.PagePlaneID).css("text-align", "center");
        var Page_Layui_PageSelect = this.SelectDataByPageIndex;
        var Page_Layui_PageID = this.PagePlaneID;
        var Page_Layui_PageIndex = this.PageIndex;
        var SumPage = Math.ceil(this.SumDateCounte / this.PageSize);
        layui.use(['form', 'laypage', 'layer'], function () {
            var laypage = layui.laypage
                , layer = layui.layer,
                form = layui.form();
            laypage({
                cont: Page_Layui_PageID
                , curr: Page_Layui_PageIndex
                , pages: SumPage //总页数
                , groups: 5 //连续显示分页数
                , skip: 'true'
                , jump: function (obj, first) {
                    //得到了当前页，用于向服务端请求对应数据
                    if (!first) {
                        //$("#" + thisPageIndexLabelID).val(obj.curr);
                        Page_Layui_PageSelect(obj.curr);
                    }
                }
            });
        });
    }


};
