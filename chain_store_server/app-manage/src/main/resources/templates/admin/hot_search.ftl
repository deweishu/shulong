<#include "/macro/frame.ftl">

<@html>
    <@head title="Chain Store管理后台">

    </@head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>热搜关键词维护</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-success " type="button" onclick="addKey();"><i class="fa fa-plus"></i>&nbsp;添加</button>
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Card View -->
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
    layer.config({extend: 'extend/layer.ext.js'})

    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/hotsearch/data",
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: false,
            //每页显示的记录数
            pageSize: 100,
            //当前第几页
            pageNumber: 1,
            //记录数可选列表
            //是否启用查询
            search: false,
            //是否启用详细信息视图
            detailView:false,
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
            columns: [ {
                title: "关键词",
                field: "name"
            },{
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="deleteKey(\''+row.name+'\')">&nbsp;删除</button> ';
                    return operateHtml;
                }
            }]
        });
    });

    function addKey(){
        layer.prompt({value: ''},function(value, index, elem){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/hotsearch/add/"+value,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function deleteKey(name){
        layer.confirm('确定删除吗？删除后不可恢复', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/hotsearch/delete/"+name,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }


</script>

</@html>