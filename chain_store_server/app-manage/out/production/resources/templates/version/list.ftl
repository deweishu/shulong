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
                    <h5>版本记录</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;发布新版本</button>
                        <br/><br/>
                        <span style="color:red;">温馨提示：点击列表中的“+”号可查看更多信息</span>
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

    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/version/data?apkId=${apkId!}",
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: true,
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
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [{
                title: "应用名称",
                field: "apkName"
            },  {
                title: "客户端类型",
                field: "apkType",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-info">安卓应用</span>';
                    }else if(value==20){
                        return '<span class="label label-primary">IOS应用</span>';
                    }else {
                        return '-';
                    }
                }
            },{
                title: "版本号",
                field: "versionCode"
            }, {
                title: "版本名",
                field: "versionName"
            },{
                title: "安装包下载",
                field: "downUrl",
                formatter: function (value, row, index) {
                    return "<a href='"+value+"' target='_blank'>点击下载</a>";
                }
            },{
                title: "安装包大小",
                field: "size"
            }, {
                title: "链接类型",
                field: "linkType",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-warning">AppStore链接</span>';
                    }else if(value==20){
                        return '<span class="label label-danger">第三方分发平台链接</span>';
                    }else if(value==30){
                        return '<span class="label label-info">自有安装包</span>';
                    }else {
                        return '-';
                    }
                }
            },{
                title: "审核状态",
                field: "status",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-warning">待审核</span>';
                    }else if(value==-10){
                        return '<span class="label label-danger">审核未通过</span>';
                    }else if(value==20){
                        return '<span class="label label-info">审核通过</span>';
                    }else if(value==-20){
                        return '<span class="label label-danger">已下架</span>';
                    }else if(value==30){
                        return '<span class="label label-info">上架中</span>';
                    }else {
                        return '-';
                    }
                }
            }, {
                title: "创建时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            },  {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '';
                    if(row.status ==30){
                        operateHtml = '<button class="btn btn-warning btn-xs" type="button" onclick="stop(\''+row.id+'\')">&nbsp;下架</button> ';
                    }
                    if(row.status ==20 || row.status ==-20){
                        operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="start(\''+row.id+'\')">&nbsp;上架</button> ';
                    }
                    operateHtml += '<button class="btn btn-danger btn-xs" type="button" onclick="deleteVersion(\''+row.id+'\')">&nbsp;删除</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    function deleteVersion(id){
        layer.confirm('删除后不可恢复，确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/version/delete/"+id,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }


    function start(id){
        layer.confirm('确定上架此版本吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/version/status/"+id+"/30",
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function stop(id){
        layer.confirm('确定下架此版本吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/version/status/"+id+"/-20",
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }


    function add() {
        layer.open({
            type: 2,
            title: '发布新版本',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/version/add/${apkId!}',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>更新内容:</b> ' + row.versionContent + '</p>');
        html.push('<p><b>审核备注:</b> ' + row.statusReason + '</p>');
        return html.join('');
    }


</script>

</@html>
