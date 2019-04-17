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
                    <h5>选择应用</h5>
                </div>
                <div class="ibox-content">
                    <p>

                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <div class="my-container">
                                        <div class="myText-content">
                                            <input id="name" name="name" type="text" class="form-control" placeholder="应用名称">
                                        </div>
                                    </div>
                                    <div class="myBtn-content">
                                        <button id="search" type="button" class="btn btn-primary">搜索</button>
                                        <button id="reset" type="button" class="btn btn-default">重置</button>
                                    </div><br><br>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <button class="btn btn-info" type="button" onclick="selectApk()" >&nbsp;确认选择</button>&nbsp;

                                    </div>
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
            url: "${ctx!}/apk/data",
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
            uniqueId: "id",
            checkboxHeader:true,
            clickToSelect:true,
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [{
                checkbox:true
            }, {
                title: "应用名字",
                field: "name",
                formatter: function (value, row, index) {
                    return value;
                }
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
            }]
        });
    });


    /*搜索按钮*/
    $('#search').click(function () {
        var name = $('#name').val();
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/apk/data?name=' + name+'&customerId=${customerId!}', pageNumber: 1, pageSize: 10});
    });


    /*重置搜索条件*/
    $('#reset').click(function() {
        $('#name').val('');
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/apk/data?&customerId=${customerId!}', pageNumber: 1, pageSize: 10});
    });



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

    function selectApk(){
        var content = $("#table_list").bootstrapTable('getSelections');
        if(content==null || content=="undefined" ||content.length==0){
            layer.msg('请选择一个应用');
            return;
        }
        var ids="";
        for(var i=0;i<content.length;i++){
            ids+=content[i].id+",";
        }
        console.log(ids);
        parent.$("#apkIds").val(ids);
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);

    }

    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>应用描述:</b> ' + row.describe + '</p>');
        html.push('<p><b>软件特色:</b> ' + row.special + '</p>');
        html.push('<p><b>审核备注:</b> ' + row.statusReason + '</p>');
        html.push('<p><b>安卓包名:</b> ' + row.packageName + '</p>');
        html.push('<p><b>IOS BuildId:</b> ' + row.buildId + '</p>');
        html.push('<p><b>开发者名称:</b> ' + row.devName + '</p>');
        html.push('<p><b>开发者官网:</b> ' + row.devUrl + '</p>');
        html.push('<p><b>适用年龄:</b> ' + row.applyAge + '</p>');
        html.push('<p><b>语言:</b> ' + row.laguage + '</p>');
        return html.join('');
    }

    
</script>

</@html>
