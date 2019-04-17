<#include "/macro/frame.ftl">

<@html>
    <@head title="Chain Store管理后台">

    </@head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs" style="font-size:20px;">
                                        <img src="https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/img/manage-logo.png">
                                        <strong class="font-bold">Chain Store</strong>
                                    </span>
                                </span>
                        </a>
                    </div>
                    <div class="logo-element">
                    </div>
                </li>
                <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                    <span class="ng-scope">菜单</span>
                </li>
                <li>
                    <a class="J_menuItem" href="${ctx!}/admin/welcome">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">主页</span>
                    </a>
                </li>

                <#--<li>
                    <a href="#">
                        <i class="fa fa fa-cog"></i>
                        <span class="nav-label">系统管理</span>
                        <span class="fa arrow"></span>
                    </a>

                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${ctx!}/admin">用户管理</a>
                        </li>

                        <li>
                            <a class="J_menuItem" href="${ctx!}/admin/role">角色管理</a>
                        </li>

                        <li>
                            <a class="J_menuItem" href="${ctx!}/admin/resource/index">资源管理</a>
                        </li>

                        <li>
                            <a class="J_menuItem" href="${ctx!}/article/index">文章公告</a>
                        </li>

                    </ul>
                </li>-->


                <li id="blankMenu"></li>

                <li class="line dk"></li>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-info " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-user"></i> <span class="label label-primary"></span>【${(user.realname)!''}】
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a onclick="updatePwd()">
                                    <div>
                                        <i class="fa fa-refresh"></i> 修改密码
                                        <span class="pull-right text-muted small">登录密码</span>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="${ctx!}/logout">
                                    <div>
                                        <i class="fa fa-remove"></i> 注销
                                        <span class="pull-right text-muted small">退出登录</span>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe id="J_iframe" width="100%" height="100%" src="${ctx!}/admin/welcome" frameborder="0" data-id="index_v1.html" seamless></iframe>
        </div>
    </div>
    <!--右侧部分结束-->
</div>
    <@footerJs>

    </@footerJs>
<!-- 自定义js -->
<script src="${static}/jia/js/hAdmin.js?v=4.1.0"></script>
<script type="text/javascript" src="${static}/jia/js/index.js"></script>
<script type="text/javascript">
    function updatePwd(){
        layer.open({
            type: 2,
            title: '修改密码',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['893px', '600px'],
            content: '${ctx!}/admin/update/pwd',
            end: function(index){
                window.location.reload();
            }
        });
    }


    createMenuListHmtl(${menu?if_exists});

    //创建左侧导航Html
    function  createMenuListHmtl(menuList) {
        if(!$.isArray(menuList)){
            return "";
        }

        var listHmtl = "";
        for(var i = 0;i < menuList.length;i++)
        {
            var firstMenuItem = menuList[i] || {};
            listHmtl += '<li>';
            listHmtl += '<a href="#"><i class="'+firstMenuItem.menu.icon+'"></i><span class="nav-label">'+firstMenuItem.menu.name+'</span><span class="fa arrow"></span></a>';

            childNode = firstMenuItem.menuElements || [];

            // 当有子节点
            if(childNode.length > 0){
                var childMenuHtml = '<ul class="nav nav-second-level">';
                for(var j= 0;j < childNode.length ;j++)
                {
                    var secondMenuItem = childNode[j];
                    href = secondMenuItem.url || "",
                    title = secondMenuItem.name || "",
                    childMenuHtml += '<li><a class="J_menuItem" href="${ctx!}'+href+'">'+title+'</a></li>'; // 二级标签 HTML;
                }
                childMenuHtml += "</ul>";
            }

            listHmtl += childMenuHtml;
            listHmtl += '</li>';
        }

        /*console.log("listHmtl-->>>" + listHmtl);*/
        $("#blankMenu").before(listHmtl);
    }
</script>
</body>
</@html>