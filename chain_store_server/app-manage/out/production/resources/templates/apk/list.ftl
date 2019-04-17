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
                    <h5>我的应用</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;创建新应用</button>&nbsp;&nbsp;
                        <a href="http://chainstore.oss-cn-shenzhen.aliyuncs.com/pdf/%E5%BA%94%E7%94%A8%E4%B8%8A%E4%BC%A0%E6%93%8D%E4%BD%9C%E6%8C%87%E5%8D%97.pdf" target="_blank">应用上传操作指南</a>
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
                                        <button class="btn btn-info" type="button" onclick="imgList()" ><i class="fa fa-file-image-o"></i>&nbsp;应用演示图上传/查看</button>&nbsp;
                                        <span style="color: red;">应用的演示图必须上传，否则无法审核通过。</span>
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
            url: "${ctx!}/apk/data?customerId=${customerId!}",
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
                radio:true
            }, {
                title: "应用名字",
                field: "name",
                formatter: function (value, row, index) {
                    return "<a href='javascript:;' onclick='edit(\""+row.id+"\")' >"+value+"</a>";
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
            }, {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = ' ';
                    if(row.status ==10){
                        operateHtml += '<button class="btn btn-danger btn-xs" type="button" onclick="operState(\''+row.id+'\',-10)">&nbsp;下架</button> ';
                    }
                    if(row.status ==30 || row.status == -10){
                        operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="operState(\''+row.id+'\',10)">&nbsp;上架</button> ';
                    }
                    if(row.status ==40){
                        operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="delApk(\''+row.id+'\')">&nbsp;删除</button> ';
                    }
                    operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="versionList(\''+row.id+'\')">&nbsp;版本控制</button> ';
                    return operateHtml;
                }
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


    function versionList(id){
        layer.open({
            type: 2,
            title: '应用版本控制',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/version/index/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function delApk(id){
        layer.confirm("删除后不可恢复", {icon: 3, title:'提示'}, function(index){
            var load=layer.load(1);
            $.ajax({
                type: "GET",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                url: "${ctx!}/apk/del/"+id,
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

    /**操作应用状态信息*/
    function operState(id, status) {
        console.log(id,status)
        var t="";
        if(status==-10){
            t="确定下架吗？";
        }else if(status==10){
            t="确定上架吗？";
        }
        var txt="";
        layer.confirm(t, {icon: 3, title:'提示'}, function(index){
            var load=layer.load(1);
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=utf-8",
                data : JSON.stringify({'id':id,'status':status,statusTxt:''}),
                dataType: "json",
                url: "${ctx!}/apk/status",
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

    function edit(id) {
        layer.open({
            type: 2,
            title: '更新应用信息',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/apk/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


    function add() {
        var load=layer.load(1);
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "${ctx!}/apk/valid/add",
            success: function(msg){
                if(msg.data==true){
                    layer.open({
                        type: 2,
                        title: '创建新应用',
                        shadeClose: false,
                        shade: [0.8, '#393D49'],
                        area: ['90%', '80%'],
                        content: '${ctx!}/apk/add/${customerId!}',
                        end: function(index){
                            $('#table_list').bootstrapTable("refresh");
                            layer.closeAll();
                        }
                    });
                }else{
                    layer.open({
                        title: '提示',
                        content: '已经达到创建应用个数限制',
                        end:function(){
                            $('#table_list').bootstrapTable("refresh");
                            layer.closeAll();
                        }
                    });
                }
            }
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

    function imgList(id){
        var content = $("#table_list").bootstrapTable('getSelections');
        if(content==null || content=="undefined" ||content.length==0){
            layer.msg('请选择一个应用');
            return;
        }
        var id= content[0].id;
        layer.open({
            type: 2,
            title: '应用演示图',
            shadeClose: false,
            shade: 0.8,
            closeBtn: 1,
            area: ['95%', '90%'],
            content: '${ctx!}/img/index/'+id,
            end: function(index){
            }
        });
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
