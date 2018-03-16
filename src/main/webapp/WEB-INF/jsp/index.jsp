<%--
  Created by IntelliJ IDEA.
  User: susu
  Date: 2017/8/10
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>数据调度中心后台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/js/plugin/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/css/view/main/main.css" media="all"/>
</head>
<body class="main_body">
<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <a href="#" class="logo">数据调度中心</a>
            <!-- 显示/隐藏菜单 -->
            <a href="javascript:void(0);" class="iconfont hideMenu icon-menu1"></a>
            <!-- 搜索 -->
            <div class="layui-form component">
                <select name="modules" lay-verify="required" lay-search="">
                    <option value="">直接选择或搜索选择</option>


                </select>
                <i class="layui-icon">&#xe615;</i>
            </div>
            <!-- 天气信息 -->
            <div class="weather" pc>
                <div id="tp-weather-widget"></div>
                <script>(function (T, h, i, n, k, P, a, g, e) {
                    g = function () {
                        P = h.createElement(i);
                        a = h.getElementsByTagName(i)[0];
                        P.src = k;
                        P.charset = "utf-8";
                        P.async = 1;
                        a.parentNode.insertBefore(P, a)
                    };
                    T["ThinkPageWeatherWidgetObject"] = n;
                    T[n] || (T[n] = function () {
                        (T[n].q = T[n].q || []).push(arguments)
                    });
                    T[n].l = +new Date();
                    if (T.attachEvent) {
                        T.attachEvent("onload", g)
                    } else {
                        T.addEventListener("load", g, false)
                    }
                }(window, document, "script", "tpwidget", "//widget.seniverse.com/widget/chameleon.js"))</script>
                <script>tpwidget("init", {
                    "flavor": "slim",
                    "location": "WX4FBXXFKE4F",
                    "geolocation": "enabled",
                    "language": "zh-chs",
                    "unit": "c",
                    "theme": "chameleon",
                    "container": "tp-weather-widget",
                    "bubble": "disabled",
                    "alarmType": "badge",
                    "color": "#FFFFFF",
                    "uid": "U9EC08A15F",
                    "hash": "039da28f5581f4bcb5c799fb4cdfb673"
                });
                tpwidget("show");</script>
            </div>
            <!-- 顶部右侧菜单 -->
            <ul class="layui-nav top_menu">
                <li class="layui-nav-item showNotice" id="showNotice" pc>
                    <a href="javascript:;"><i class="iconfont icon-gonggao"></i><cite>系统公告</cite></a>
                </li>
                <!--<li class="layui-nav-item" mobile>-->
                <!--<a href="javascript:;" class="mobileAddTab" data-url="page/changePwd.html"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><cite>设置</cite></a>-->
                <!--</li>-->
                <%--<li class="layui-nav-item" mobile>--%>
                    <%--<a href="/logout"><i class="iconfont icon-loginout"></i> 退出</a>--%>
                <%--</li>--%>
                <li class="layui-nav-item lockcms" pc>
                    <a href="javascript:;"><i class="iconfont icon-lock1"></i><cite>锁屏</cite></a>
                </li>
                <li class="layui-nav-item" pc>
                    <a href="javascript:;">
                        <img src="${ctx}/images/base/userface5.jpg" class="layui-circle" width="35" height="35">
                        <cite class="userName">${username}</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <%--<dd>--%>
                            <%--<a href="javascript:;" data-url="/gotouserInfo"><i class="iconfont icon-zhanghu"--%>
                                                          <%--data-icon="icon-zhanghu"></i><cite>个人资料</cite></a>--%>
                        <%--</dd>--%>
                        <dd><a href="javascript:;" data-url="/gotochangePwd"><i class="iconfont icon-shezhi1"
                                                                                     data-icon="icon-shezhi1"></i><cite>修改密码</cite></a>
                        </dd>
                        <dd><a href="javascript:;" class="changeSkin noAddTab"><i
                                class="iconfont icon-huanfu"></i><cite>更换皮肤</cite></a></dd>
                        <dd><a href="/logout" class="noAddTab"><i
                                class="iconfont icon-loginout"></i><cite>退出</cite></a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black">
        <div class="user-photo">
            <a class="img" title="我的头像"><img src="${ctx}/images/base/userface5.jpg"></a>
            <p>你好！<span class="userName">${username}</span></p>
        </div>
        <div class="navBar layui-side-scroll"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
            <ul class="layui-tab-title top_tab" id="top_tabs">
                <li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>后台首页</cite></li>
            </ul>

            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" class="freshnowpage"><i class="icon-refresh"></i>
                            刷新当前</a>
                        </dd>
                        <dd><a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i>
                            关闭其他</a>
                        </dd>
                        <dd><a href="javascript:;" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a>
                        </dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="${ctx}/gotomain"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <div class="layui-footer footer">

    </div>
</div>

<!-- 移动导航 -->
<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
<div class="site-mobile-shade"></div>
<script type="text/javascript" src="${ctx}/js/plugin/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/view/index/leftNav.js"></script>
<%--<script type="text/javascript" src="${ctx}/js/view/index/index.js"></script>--%>
<script>
    //获取上下文环境
//    var localObj = window.location;
//    var contextPath = "/"+localObj.pathname.split("/")[1];
    var $, tab, skyconsWeather;
    layui.config({
        base: "${ctx}/js/view/index/"
    }).use(['bodyTab', 'form', 'element', 'layer', 'jquery'], function () {
        var form = layui.form(),
            layer = layui.layer,
            element = layui.element();
        $ = layui.jquery;
        tab = layui.bodyTab({
            openTabNum: "50",  //最大可打开窗口数量
            url: "${ctx}/view/json/navs.json" //获取菜单json地址
        });
        //更换皮肤
        var skin = window.sessionStorage.getItem("skin")
        if (skin) {  //如果更换过皮肤
            if (window.sessionStorage.getItem("skinValue") != "自定义") {
                $("body").addClass(window.sessionStorage.getItem("skin"));
            } else {
                $(".layui-layout-admin .layui-header").css("background-color", skin.split(',')[0]);
                $(".layui-bg-black").css("background-color", skin.split(',')[1]);
                $(".hideMenu").css("background-color", skin.split(',')[2]);
            }
        }
        $(".changeSkin").click(function () {
            layer.open({
                title: "更换皮肤",
                area: ["310px", "280px"],
                type: "1",
                content: '<div class="skins_box">' +
                '<form class="layui-form">' +
                '<div class="layui-form-item">' +
                '<input type="radio" name="skin" value="默认" title="默认" lay-filter="default" checked="">' +
                '<input type="radio" name="skin" value="橙色" title="橙色" lay-filter="orange">' +
                '<input type="radio" name="skin" value="蓝色" title="蓝色" lay-filter="blue">' +
                '<input type="radio" name="skin" value="自定义" title="自定义" lay-filter="custom">' +
                '<div class="skinCustom">' +
                '<input type="text" class="layui-input topColor" name="topSkin" placeholder="顶部颜色" />' +
                '<input type="text" class="layui-input leftColor" name="leftSkin" placeholder="左侧颜色" />' +
                '<input type="text" class="layui-input menuColor" name="btnSkin" placeholder="顶部菜单按钮" />' +
                '</div>' +
                '</div>' +
                '<div class="layui-form-item skinBtn">' +
                '<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-normal" lay-submit="" lay-filter="changeSkin">确定更换</a>' +
                '<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-primary" lay-submit="" lay-filter="noChangeSkin">我再想想</a>' +
                '</div>' +
                '</form>' +
                '</div>',
                success: function (index, layero) {
                    if (window.sessionStorage.getItem("skinValue")) {
                        $(".skins_box input[value=" + window.sessionStorage.getItem("skinValue") + "]").attr("checked", "checked");
                    }
                    ;
                    if ($(".skins_box input[value=自定义]").attr("checked")) {
                        $(".skinCustom").css("visibility", "inherit");
                        $(".topColor").val(skin.split(',')[0]);
                        $(".leftColor").val(skin.split(',')[1]);
                        $(".menuColor").val(skin.split(',')[2]);
                    }
                    ;
                    form.render();
                    $(".skins_box").removeClass("layui-hide");
                    $(".skins_box .layui-form-radio").on("click", function () {
                        var skinColor;
                        if ($(this).find("span").text() == "橙色") {
                            skinColor = "orange";
                        } else if ($(this).find("span").text() == "蓝色") {
                            skinColor = "blue";
                        } else if ($(this).find("span").text() == "默认") {
                            skinColor = "";
                        }
                        if ($(this).find("span").text() != "自定义") {
                            $("body").removeAttr("class").addClass("main_body " + skinColor + "");
                            $(".skinCustom").removeAttr("style");
                            $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                        } else {
                            $(".skinCustom").css("visibility", "inherit");
                        }
                    })
                    var skinStr, skinColor;
                    $(".topColor").blur(function () {
                        $(".layui-layout-admin .layui-header").css("background-color", $(this).val());
                    })
                    $(".leftColor").blur(function () {
                        $(".layui-bg-black").css("background-color", $(this).val());
                    })
                    $(".menuColor").blur(function () {
                        $(".hideMenu").css("background-color", $(this).val());
                    })

                    form.on("submit(changeSkin)", function (data) {
                        if (data.field.skin != "自定义") {
                            if (data.field.skin == "橙色") {
                                skinColor = "orange";
                            } else if (data.field.skin == "蓝色") {
                                skinColor = "blue";
                            } else if (data.field.skin == "默认") {
                                skinColor = "";
                            }
                            window.sessionStorage.setItem("skin", skinColor);
                        } else {
                            skinStr = $(".topColor").val() + ',' + $(".leftColor").val() + ',' + $(".menuColor").val();
                            window.sessionStorage.setItem("skin", skinStr);
                        }
                        window.sessionStorage.setItem("skinValue", data.field.skin);
                        layer.closeAll("page");
                    });
                    form.on("submit(noChangeSkin)", function () {
                        $("body").removeAttr("class").addClass("main_body " + window.sessionStorage.getItem("skin") + "");
                        $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                        layer.closeAll("page");
                    });
                },
                cancel: function () {
                    $("body").removeAttr("class").addClass("main_body " + window.sessionStorage.getItem("skin") + "");
                    $(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
                }
            })
        })

        //隐藏左侧导航
        $(".hideMenu").click(function () {
            $(".layui-layout-admin").toggleClass("showMenu");
            //渲染顶部窗口
            tab.tabMove();
        })

        //渲染左侧菜单
        tab.render();

        //锁屏
        function lockPage() {
            layer.open({
                title: false,
                type: 1,
                content: '	<div class="admin-header-lock" id="lock-box">' +
                '<div class="admin-header-lock-img"><img src="/images/base/face.jpg"/></div>' +
                '<div class="admin-header-lock-name" id="lockUserName">超级管理员</div>' +
                '<div class="input_btn">' +
                '<input type="password" class="admin-header-lock-input layui-input" autocomplete="off" placeholder="请输入密码解锁.." name="lockPwd" id="lockPwd" />' +
                '<button class="layui-btn" id="unlock">解锁</button>' +
                '</div>' +
                '<p>请输入“123456”，否则不会解锁成功哦！！！</p>' +
                '</div>',
                closeBtn: 0,
                shade: 0.9
            })
            $(".admin-header-lock-input").focus();
        }

        $(".lockcms").on("click", function () {
            window.sessionStorage.setItem("lockcms", true);
            lockPage();
        })
        // 判断是否显示锁屏
        if (window.sessionStorage.getItem("lockcms") == "true") {
            lockPage();
        }
        // 解锁
        $("body").on("click", "#unlock", function () {
            if ($(this).siblings(".admin-header-lock-input").val() == '') {
                layer.msg("请输入解锁密码！");
                $(this).siblings(".admin-header-lock-input").focus();
            } else {
                if ($(this).siblings(".admin-header-lock-input").val() == "123456") {
                    window.sessionStorage.setItem("lockcms", false);
                    $(this).siblings(".admin-header-lock-input").val('');
                    layer.closeAll("page");
                } else {
                    layer.msg("密码错误，请重新输入！");
                    $(this).siblings(".admin-header-lock-input").val('').focus();
                }
            }
        });
        $(document).on('keydown', function () {
            if (event.keyCode == 13) {
                $("#unlock").click();
            }
        });

        //手机设备的简单适配
        var treeMobile = $('.site-tree-mobile'),
            shadeMobile = $('.site-mobile-shade')

        treeMobile.on('click', function () {
            $('body').addClass('site-mobile');
        });

        shadeMobile.on('click', function () {
            $('body').removeClass('site-mobile');
        });

        // 添加新窗口
        $("body").on("click", ".navBar .layui-nav .layui-nav-item a,.top_menu.layui-nav .layui-nav-child a:not('.noAddTab'),.mobileAddTab", function () {
            //如果不存在子级
            if ($(this).siblings().length == 0) {
                addTab($(this));
                $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
            }
            $(this).parent("li").siblings().removeClass("layui-nav-itemed");
        })

        //公告层
        function showNotice() {
            layer.open({
                type: 1,
                title: "系统公告",
                closeBtn: false,
                area: '310px',
                shade: 0.8,
                id: 'LAY_layuipro',
                btn: ['我知道了'],
                moveType: 1,
                content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>' +
                '这是一个数据发送工具的后台管理端，功能可能比较少，请见谅。' +
                '</p></div>',
                success: function (layero) {
                    var btn = layero.find('.layui-layer-btn');
                    btn.css('text-align', 'center');
                    btn.on("click", function () {
                        window.sessionStorage.setItem("showNotice", "true");
                    })
                    if ($(window).width() > 432) {  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
                        btn.on("click", function () {
                            layer.tips('系统公告躲在了这里', '#showNotice', {
                                tips: 3
                            });
                        })
                    }
                }
            });
        }

        //判断是否处于锁屏状态(如果关闭以后则未关闭浏览器之前不再显示)
        if (window.sessionStorage.getItem("lockcms") != "true" && window.sessionStorage.getItem("showNotice") != "true") {
            showNotice();
        }
        $(".showNotice").on("click", function () {
            showNotice();
        })

        //刷新后还原打开的窗口
        if (window.sessionStorage.getItem("menu") != null) {
            menu = JSON.parse(window.sessionStorage.getItem("menu"));
            curmenu = window.sessionStorage.getItem("curmenu");
            var openTitle = '';
            for (var i = 0; i < menu.length; i++) {
                openTitle = '';
                if (menu[i].icon) {
                    if (menu[i].icon.split("-")[0] == 'icon') {
                        openTitle += '<i class="iconfont ' + menu[i].icon + '"></i>';
                    } else {
                        openTitle += '<i class="layui-icon">' + menu[i].icon + '</i>';
                    }
                }
                openTitle += '<cite>' + menu[i].title + '</cite>';
                openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + menu[i].layId + '">&#x1006;</i>';
                element.tabAdd("bodyTab", {
                    title: openTitle,
                    content: "<iframe src='${ctx}" +menu[i].href + "' data-id='" + menu[i].layId + "'></frame>",
                    id: menu[i].layId
                })
                //定位到刷新前的窗口
                if (curmenu != "undefined") {
                    if (curmenu == '' || curmenu == "null") {  //定位到后台首页
                        element.tabChange("bodyTab", '');
                    } else if (JSON.parse(curmenu).title == menu[i].title) {  //定位到刷新前的页面
                        element.tabChange("bodyTab", menu[i].layId);
                    }
                } else {
                    element.tabChange("bodyTab", menu[menu.length - 1].layId);
                }
            }
            //渲染顶部窗口
            tab.tabMove();
        }

        //关闭其他
        $(".closePageOther").on("click", function () {
            if ($("#top_tabs li").length > 2 && $("#top_tabs li.layui-this cite").text() != "后台首页") {
                var menu = JSON.parse(window.sessionStorage.getItem("menu"));
                $("#top_tabs li").each(function () {
                    if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
                        element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
                        //此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
                        for (var i = 0; i < menu.length; i++) {
                            if ($("#top_tabs li.layui-this cite").text() == menu[i].title) {
                                menu.splice(0, menu.length, menu[i]);
                                window.sessionStorage.setItem("menu", JSON.stringify(menu));
                            }
                        }
                    }
                })
            } else if ($("#top_tabs li.layui-this cite").text() == "后台首页" && $("#top_tabs li").length > 1) {
                $("#top_tabs li").each(function () {
                    if ($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")) {
                        element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
                        window.sessionStorage.removeItem("menu");
                        menu = [];
                        window.sessionStorage.removeItem("curmenu");
                    }
                })
            } else {
                layer.msg("没有可以关闭的窗口了@_@");
            }
            //渲染顶部窗口
            tab.tabMove();
        })
        //关闭其他
        $(".freshnowpage").on("click", function () {


        })

        //关闭全部
        $(".closePageAll").on("click", function () {
            if ($("#top_tabs li").length > 1) {
                $("#top_tabs li").each(function () {
                    if ($(this).attr("lay-id") != '') {
                        element.tabDelete("bodyTab", $(this).attr("lay-id")).init();
                        window.sessionStorage.removeItem("menu");
                        menu = [];
                        window.sessionStorage.removeItem("curmenu");
                    }
                })
            } else {
                layer.msg("没有可以关闭的窗口了@_@");
            }
            //渲染顶部窗口
            tab.tabMove();
        })
        // $(".userName").html(getCookie("userName"));
    })

    //打开新窗口
    function addTab(_this) {
        tab.tabAdd(_this);
    }

    //读取cookie
    function getCookie(name) {
        var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
        if (arr != null) return unescape(arr[2]);
        return null;
    };


</script>
</body>
</html>