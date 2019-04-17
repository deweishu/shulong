<#include "/macro/frame.ftl">

<@html>
    <@head title="系统">

    </@head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>专题下的应用列表</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <#--<button class="btn btn-info " type="button" onclick="clearCache();">&nbsp;清除专题缓存</button>-->
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">

                                    <table id="table_list"></table>
                                </div>
                            </div>
                            <!-- End Example Card View -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

    <@footerJs>

    </@footerJs>

<!-- Bootstrap table -->
<script src="${static}/jia/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${static}/jia/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${static}/jia/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- Peity -->
<script src="${static}/jia/js/plugins/peity/jquery.peity.min.js"></script>
<!-- 自定义js -->
<script src="${static}/jia/js/content.js?v=1.0.0"></script>
<script>
    layer.config({extend: '${static}/jia/js/plugins/layer/extend/layer.ext.js'})
    var ztId="${ztId!}";
    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/sys/apk/zt/data/"+ztId,
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: false,
            //每页显示的记录数
            pageSize: 10,
            //当前第几页
            pageNumber: 1,
            //记录数可选列表
            pageList: [5, 10, 15, 20, 25],
            //是否启用查询
            search: false,
            //是否启用详细信息视图
            detailView:true,
            detailFormatter:detailFormatter,
            //表示服务端请求
            sidePagination: "server",
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            uniqueId: "id",
            checkboxHeader:true,
            clickToSelect:true,
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [{
                title: "应用名字",
                field: "name"
            }, {
                title: "应用logo",
                field: "logo",
                formatter: function (value, row, index) {
                    return '<a href="javascript:;" onclick="openImg(\''+value+'\')" >点击查看</a>';
                }
            },  {
                title: "分类",
                field: "cateName"
            },  {
                title: "奖励糖果",
                field: "candyNum"
            },{
                title: "应用状态",
                field: "status",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-info">上架中</span>';
                    }else if(value==-10){
                        return '<span class="label label-danger">已下架</span>';
                    }else if(value==20){
                        return '<span class="label label-warning">待审核</span>';
                    }else if(value==30){
                        return '<span class="label label-info">审核通过</span>';
                    }else if(value==40){
                        return '<span class="label label-danger">审核未通过</span>';
                    }else {
                        return '-';
                    }
                }
            },{
                title: "应用类型",
                field: "clientType",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-info">安卓</span>';
                    }else if(value==20){
                        return '<span class="label label-primary">IOS</span>';
                    }else if(value==30){
                        return '<span class="label label-info">安卓+IOS</span>';
                    }else {
                        return '-';
                    }
                }
            }, {
                title: "下载次数",
                field: "downNum"
            },  {
                title: "是否有广告",
                field: "haveAd",
                formatter: function (value, row, index) {
                    if(value){
                        return '<span class="label label-danger">是</span>';
                    }else {
                        return '<span class="label label-info">否</span>';
                    }
                }
            }, {
                title: "是否有插件",
                field: "havePlugin",
                formatter: function (value, row, index) {
                    if(value){
                        return '<span class="label label-danger">是</span>';
                    }else {
                        return '<span class="label label-info">否</span>';
                    }
                }
            }, {
                title: "是否人工亲测",
                field: "havePeople",
                formatter: function (value, row, index) {
                    if(value){
                        return '<span class="label label-info">是</span>';
                    }else {
                        return '<span class="label label-danger">否</span>';
                    }
                }
            },{
                title: "创建时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            }, {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="removeCate(\''+row.id+'\')">&nbsp;移除</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    /*搜索按钮*/
    $('#search').click(function () {
        var name = $('#name').val();
        var categoryId = $('#categoryId').val();
        var status = $('#status').val();
        var havePlugin = $('#havePlugin').val();
        var haveAd = $('#haveAd').val();
        $('#table_list').bootstrapTable('refresh', {
            url: '${ctx!}/apk/data?name=' + name+'&categoryId='+categoryId+'&status='+status+'&havePlugin='+havePlugin+'&haveAd='+haveAd,
            pageNumber: 1,
            pageSize: 10
        });
    });


    /*重置搜索条件*/
    $('#reset').click(function() {
        $('#name').val('');
        $('#categoryId').val('');
        $('#status').val('');
        $('#havePlugin').val('');
        $('#haveAd').val('');
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/apk/data', pageNumber: 1, pageSize: 10});
    });



    function removeCate(id){
        var currCate="${ztId!}";
        var s="确定移除吗？";
        layer.confirm(s, {icon: 3, title:'提示'}, function(index){
            var cindex=layer.load(1);
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/sys/apk/delete/zt/"+id+"/"+currCate,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.closeAll();
                    });
                }
            });
        });

    }
    
    function clearCache() {
        var cindex=layer.load(1);
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "${ctx!}/sys/apk/clear/zt/cache/"+ztId,
            success: function(msg){
                layer.msg(msg.msg, {time: 2000},function(){
                    $('#table_list').bootstrapTable("refresh");
                    layer.closeAll();
                });
            }
        });
    }

    function openImg(url) {
        layer.open({
            type: 2,
            title: false,
            area: ['20%', '20%'],
            shade: 0.8,
            closeBtn: 1,
            shadeClose: false,
            content: url
        });
    }


    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>应用描述:</b> ' + row.describe + '</p>');
        html.push('<p><b>软件特色:</b> ' + row.special + '</p>');
        html.push('<p><b>审核备注:</b> ' + row.statusReason + '</p>');
        return html.join('');
    }

    
</script>

</@html>
