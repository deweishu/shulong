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
                    <h5>APP版本管理</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;上传新版本</button>
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
            url: "${ctx!}/sys/appversion/data",
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
            columns: [  {
                title: "应用版本号",
                field: "versionCode"
            }, {
                title: "应用版本名称",
                field: "versionName"
            },  {
                title: "下载地址",
                field: "downloadUrl",
                formatter: function (value, row, index) {
                    return "<a href='"+value+"' target='_blank'>点击下载</a>";
                }
            }, {
                title: "应用版本的加密值",
                field: "md5"
            }, {
                title: "版本状态",
                field: "versionStatus",
                formatter: function (value, row, index) {
                    if(value == true)
                        return '<span class="label label-info">上架</span>';
                    else
                        return '<span class="label label-danger">下架</span>';
                }
            }, {
                title: "版本大小",
                field: "versionSize"
            }, {
                title: "是否强制更新",
                field: "forceUpdate",
                formatter: function (value, row, index) {
                    if(value == true)
                        return '<span class="label label-danger">是</span>';
                    else
                        return '<span class="label label-info">否</span>';
                }
            },  {
                title: "客户端类型",
                field: "clientType",
                formatter: function (value, row, index) {
                    if(value == 10)
                        return '<span class="label label-primary">安卓</span>';
                    else
                        return '<span class="label label-info">IOS</span>';
                }
            },  {
                title: "创建时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            },  {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button> ';
                    operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="status(\''+row.id+'\',true)">&nbsp;上架</button> ';
                    operateHtml += '<button class="btn btn-warning btn-xs" type="button" onclick="status(\''+row.id+'\',false)">&nbsp;下架</button> ';
                    operateHtml += '<button class="btn btn-danger btn-xs" type="button" onclick="deleteApp(\''+row.id+'\')">&nbsp;删除</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    function edit(id) {
        layer.open({
            type: 2,
            title: '更新信息',
            shadeClose: true,
            shade: false,
            area: ['90%', '80%'],
            content: '${ctx!}/sys/appversion/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function add() {
        layer.open({
            type: 2,
            title: '上传新版本',
            shadeClose: true,
            shade: false,
            area: ['90%', '80%'],
            content: '${ctx!}/sys/appversion/add',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function status(id,obj){
        var t="";
        if(obj==true){
            t="确定上架此版本吗?";
        }else{
            t="确定下架此版本吗?";
        }
        layer.confirm(t, {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/sys/appversion/status/"+id+"/"+obj,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }
    
    function deleteApp(id) {
        layer.confirm("确定删除该版本吗？", {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/sys/appversion/delete/"+id,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }
    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>版本更新说明:</b> ' + row.versionDesc + '</p>');
        return html.join('');
    }

</script>

</@html>
