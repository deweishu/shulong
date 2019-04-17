<#include "/macro/frame.ftl">

<@html>
    <@head title="消息推送记录">

    </@head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>消息推送记录</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-success " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;发起消息推送</button>
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

    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/push/data",
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
            columns: [ {
                title: "推送标题",
                field: "pushTitle"
            },{
                title: "推送描述",
                field: "pushTxt"
            },{
                title: "操作者名称",
                field: "operateName"
            },{
                title: "推送时间",
                field: "createTime"
            }]
        });
    });

    function add(){
        layer.open({
            type: 2,
            title: '发送消息推送',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/push/send',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>推送具体内容(透传消息):</b> ' + row.pushContent + '</p>');
        html.push('<p><b>推送结果:</b> ' + row.pushResult + '</p>');
        return html.join('');
    }
</script>

</@html>