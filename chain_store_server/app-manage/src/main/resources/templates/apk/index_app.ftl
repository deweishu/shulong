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
                    <h5>首页APP维护</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <span style="color: red;">默认查询当天的数据</span>
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <div class="my-container">
                                        <div class="myText-content">
                                            <input id="date" name="date" type="text" class="form-control" value="${date!}" placeholder="选择日期"
                                                   onclick="laydate({istime: false,festival: true , format: 'YYYY-MM-DD'})"
                                            >
                                        </div>
                                    </div>
                                    <div class="myBtn-content">
                                        <button id="search" type="button" class="btn btn-primary">搜索</button>
                                    </div><br><br>
                                    <div class="hr-line-dashed"></div>

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
<script src="${static}/jia/js/plugins/layer/laydate/laydate.js"></script>
<script src="${static}/jia/js/content.js?v=1.0.0"></script>
<script>
    layer.config({extend: '${static}/jia/js/plugins/layer/extend/layer.ext.js'})
    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/index/app/data/${date!}",
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
                    "rows": res
                };
            },
            //数据列
            columns: [ {
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
                        return "<i class='fa fa-android' style='color:red'></i>";
                    }else if(value==20){
                        return "<i class='fa fa-apple' style='color:#140064'></i>";
                    }else if(value==30){
                        return "<i class='fa fa-android' style='color:red'></i>  &nbsp; <i class='fa fa-apple'  style='color:#140064'></i>";
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
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="removeToday(\''+row.id+'\')">&nbsp;移除</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    /*搜索按钮*/
    $('#search').click(function () {
        var date = $('#date').val();
        window.location="${ctx!}/index/app/index?date="+date;
    });


    function removeToday(id) {
        var t="确定移除吗？";
        var date=$("#date").val();
        layer.confirm(t, {icon: 3, title:'提示'}, function(index){
            var load=layer.load(1);
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/index/app/delete/"+date+"/"+id,
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



    function openImg(url) {
        layer.open({
            type: 2,
            title: false,
            area: ['40%', '50%'],
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
