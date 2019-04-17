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
                    <h5>短信发送记录</h5>
                </div>
                <div class="ibox-content">
                    <p><br/>
                    </p>
                    <p><br/></p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Card View -->
                            <div class="example-wrap">

                                <div class="my-container">
                                    <div class="myText-content">
                                        <input id="mobile" name="mobile" type="text" class="form-control" placeholder="手机号码">
                                    </div>
                                </div>
                                <div class="myBtn-content">
                                    <button id="search" type="button" class="btn btn-primary">搜索</button>&nbsp;&nbsp;
                                    <button id="reset" type="button" class="btn btn-default">重置</button>
                                </div>
                                <br><p></p>
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
            // 必须输入手机号码才予查询
            url: "${ctx!}/sms/record/data",
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
            //表示服务端请求
            sidePagination: "server",
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            //json数据解析
            responseHandler: function (res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [
                {
                    title: "手机号码",
                    field: "mobile"
                }, {
                    title: "验证码",
                    field: "code"
                }, {
                    title: "短信模板",
                    field: "templateCode"
                }, {
                    title: "短信内容",
                    field: "content"
                }, {
                    title: "发送时间",
                    field: "createTime"
                }, {
                    title: "发送状态",
                    field: "status",
                    formatter: function (value, row, index) {
                        switch (value) {
                            case 10:
                                return '<span class="label label-info">已发送</span>';
                                break;
                            case 20:
                                return '发送成功';
                                break;
                            case 30:
                                return '<span class="label label-danger">发送失败</span>';
                                break;
                            default:
                                return value;
                                break;
                        }
                    }
                }
            ]
        });
    });


    /*搜索按钮*/
    $('#search').click(function () {
        var mobile = $('#mobile').val();
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/sms/record/data?mobile=' + mobile, pageNumber: 1, pageSize: 10});
    });

    /*重置搜索条件*/
    $('#reset').click(function() {
        $('#mobile').val('');
    });

</script>

</@html>