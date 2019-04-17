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
                    <h5>系统配置信息</h5>
                </div>
                <div class="ibox-content">
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
            url: "${ctx!}/config/data",
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
            columns: [{
                title: "配置code",
                field: "configCode"
            }, {
                title: "配置描述",
                field: "configDesc"
            },  {
                title: "配置数值",
                field: "configNum"
            }, {
                title: "添加时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            },  {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button> ';
                    return operateHtml;
                }
            }]
        });
    });

    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>配置内容:</b> ' + row.configContent + '</p>');
        return html.join('');
    }


    function edit(id) {
        layer.open({
            type: 2,
            title: '更新配置',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/config/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    
</script>

</@html>
