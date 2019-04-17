<#include "/macro/frame.ftl">

<@html>
    <@head title="系统管理后台">

    </@head>


<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>合作商信息</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加合作商</button>
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="my-container">
                                    <div class="myText-content">
                                        <input id="name" name="name" type="text" class="form-control" placeholder="合作商名称">
                                    </div>
                                </div>
                                <div class="my-container">
                                    <div class="myText-content">
                                        <input id="phone" name="phone" type="text" class="form-control" placeholder="合作商手机号">
                                    </div>
                                </div>
                                <div class="myBtn-content">
                                    <button id="search" type="button" class="btn btn-primary">搜索</button>
                                    <button id="reset" type="button" class="btn btn-default">重置</button>
                                </div>

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
            url: "${ctx!}/customer/data",
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
                title: "客户名称",
                field: "name"
            },{
                title: "手机号",
                field: "phone"
            },{
                title: "客户状态",
                field: "status",
                formatter:function (value, row, index) {
                    return value==true?"<span class=\"label label-info\">正常</span>":"<span class=\"label label-info\">封号</span>";
                }
            },{
                title: "创建时间",
                field: "createTime"
            }, {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button> ';
                    operateHtml += '<button class="btn btn-primary btn-xs" type="button" onclick="restPwd(\''+row.id+'\')">&nbsp;重置密码</button> ';
                    return operateHtml;
                }
            }]
        });
    });

    /*搜索按钮*/
    $('#search').click(function () {
        var name = $('#name').val();
        var phone = $('#phone').val();
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/customer/data?name=' + name + '&phone=' +phone, pageNumber: 1, pageSize: 10});
    });


    /*重置搜索条件*/
    $('#reset').click(function() {
        $('#name').val('');
        $('#phone').val('');
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/customer/data', pageNumber: 1, pageSize: 10});
    });



    function restPwd(id) {
        layer.confirm('重置密码之后，将会变为初始密码', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "POST",
                data:{id: id},
                dataType: "json",
                url: "${ctx!}/customer/rest/pwd/"+id,
                success: function(rsp){
                    layer.msg(rsp.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function edit(id){
        layer.open({
            type: 2,
            title: '修改合作商信息',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/customer/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function add() {
        layer.open({
            type: 2,
            title: '添加合作商',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/customer/add',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    
</script>

</@html>
