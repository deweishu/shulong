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
                    <h5>banner列表数据</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button>
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
            url: "${ctx!}/banner/data",
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
            search: true,
            //是否启用详细信息视图
            detailView:true,
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
            columns: [ {
                title: "banner图片",
                field: "logo",
                formatter: function (value, row, index) {
                    return '<a href="javascript:;" onclick="openImg(\''+value+'\')"  >点击查看</a>';
                }
            }, {
                title: "连接url",
                field: "url",
                formatter: function (value, row, index) {
                    return '<a href='+value+'  target="_blank" >点击查看</a>';
                }
            }, {
                title: "连接类型",
                field: "linkType",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-info">网页连接</span>';
                    }else if(value==20){
                        return '<span class="label label-primary">APP</span>';
                    }else {
                        return '<span class="label label-primary">无</span>';
                    }
                }
            }, {
                title: "应用名称",
                field: "apkName"
            }, {
                title: "是否可用",
                field: "status",
                formatter: function (value, row, index) {
                    if(value==true){
                        return '<span class="label label-info">可用</span>';
                    }else {
                        return '<span class="label label-danger">不可用</span>';
                    }
                }
            },  {
                title: "创建时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            },   {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button> ';
                    if(row.status){
                        operateHtml += '<button class="btn btn-warning btn-xs" type="button" onclick="stop(\''+row.id+'\')">&nbsp;停止</button> ';
                    }else{
                        operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="start(\''+row.id+'\')">&nbsp;开始</button> ';
                    }
                    operateHtml += '<button class="btn btn-danger btn-xs" type="button" onclick="deleteBanner(\''+row.id+'\')">&nbsp;删除</button> ';
                    return operateHtml;
                }
            }]
        });
    });

    function edit(id) {
        layer.open({
            type: 2,
            title: '修改banner信息',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/banner/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function add() {
        layer.open({
            type: 2,
            title: '添加banner',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/banner/add',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function start(id){
        layer.confirm('确定开启此广告BANNER？', {icon: 3, title:'提示'}, function(index){
            var load=layer.load(1);
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/banner/status/"+id+"/true",
                success: function(msg){
                    layer.msg(msg.msg, {time: 1500},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                        layer.close(load);
                    });
                }
            });
        });
    }

    function deleteBanner(id){
        layer.confirm('删除后不可恢复，确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/banner/delete/"+id,
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
        layer.confirm('确定停用此广告BANNER?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/banner/status/"+id+"/false",
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }


    function openImg(url) {
        if(url.length>0){
            layer.open({
                type: 2,
                title: false,
                area: ['90%', '90%'],
                shade: 0.8,
                closeBtn: 1,
                shadeClose: true,
                content: url
            });
        }else{
            layer.msg("数据为空，看不鸟");
        }
    }

</script>

</@html>
