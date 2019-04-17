<#include "/macro/frame.ftl">

<@html>
    <@head title="用户列表数据">

    </@head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>邀请记录</h5>
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
            url: "${ctx!}/user/invet/data?invetId=${invetId!}",
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
                title: "用户名",
                field: "nickName",
                formatter: function (value, row, index) {
                    return value==null||value.length==0?'-':value;
                }
            }, {
                title: "手机号",
                field: "mobile"
            }, {
                title: "状态",
                field: "status",
                formatter: function (value, row, index) {
                    return value==true?"<span class=\"label label-info\">正常</span>":"<span class=\"label label-info\">封号</span>";
                }
            }, {
                title: "头像url",
                field: "headImg",
                formatter:function(value,row,index){
                    return '<a href="javascript:;" onclick="openImg(\''+value+'\')" target="_blank" >点击查看</a>';
                }
            }, {
                title: "用户糖果余额",
                field: "candyAmount"
            }, {
                title: "邀请人",
                field: "invertNickName",
                formatter: function (value, row, index) {
                    return value==null||value.length==0?'-':value;
                }
            },{
                title: "注册时间",
                field: "createTime"
            }]
        });

    });


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
