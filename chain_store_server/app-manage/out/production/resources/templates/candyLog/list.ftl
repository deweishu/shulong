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
                    <h5>用户糖果获取记录</h5>
                </div>
                <div class="ibox-content">
                    <p>
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
            url: "${ctx!}/candy/data?userId=${userId!}",
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
            columns: [{
                title: "发生糖果余额数",
                field: "candyNum"
            }, {
                title: "糖果发生改变的原因描述",
                field: "changeDesc"
            }, {
                title: "获取渠道",
                field: "changeType",
                formatter: function (value, row, index) {
                    if(value==10){
                        return '<span class="label label-info">邀请好友</span>';
                    }else if(value==20){
                        return '<span class="label label-primary">分享APP</span>';
                    }else if(value==30){
                        return '<span class="label label-daner">下载APP</span>';
                    }else if(value==40){
                        return '<span class="label label-daner">抽奖所得</span>';
                    }else if(value==50){
                        return '<span class="label label-daner">签到获取</span>';
                    }else if(value==60){
                        return '<span class="label label-daner">注册成功</span>';
                    }else if(value==70){
                        return '<span class="label label-daner">评论APP</span>';
                    }else {
                        return '-';
                    }
                }
            }, {
                title: "数据ID",
                field: "changeId"
            },  {
                title: "获得时间",
                field: "createTime"
            }]
        });
    });

</script>

</@html>
