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
                    <h5>APP分类维护</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button>
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
            url: "${ctx!}/category/data",
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
                title: "分类名称",
                field: "name"
            }, {
                title: "排序",
                field: "cateSort"
            }, {
                title: "类型",
                field: "type",
                formatter: function (value, row, index) {
                    return value==10?"分类":"专题";
                }
            }, {
                title: "创建时间",
                field: "createTime"
            }, {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml ="";
                    if(row.type==10){
                        operateHtml +=  '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button>';
                        operateHtml += '  <button class="btn btn-danger btn-xs" type="button" onclick="delCategory(\''+row.id+'\')">&nbsp;删除</button>';
                    }
                    if(row.type==20){
                        operateHtml += '  <button class="btn btn-primary btn-xs" type="button" onclick="zhuanti(\''+row.id+'\')">&nbsp;APP应用列表</button>';
                    }else{
                        operateHtml += '  <button class="btn btn-info btn-xs" type="button" onclick="recommend(\''+row.id+'\')">&nbsp;推荐APP</button>';
                    }
                    return operateHtml;
                }
            }]
        });
    });


    function recommend(cateId){
        layer.open({
            type: 2,
            title: false,
            area: ['80%', '90%'],
            shade: 0.8,
            closeBtn: 1,
            shadeClose: false,
            content: '${ctx!}/cate/recommed/index/'+cateId,
            end: function(index){

            }
        });
    }

    function zhuanti(id) {
        var t="";
        if(id==9){
            t="新品推荐的应用";
        }else if(id==10){
            t="入门必备的应用";
        }else if(id==11){
            t="今日主题的应用";
        }
        layer.open({
            type: 2,
            title: t,
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/sys/apk/zt/index/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    
    function delCategory(id){
        layer.confirm('确定删除吗？删除后不可恢复', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/category/delete/"+id,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function edit(id) {
        layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/category/edit/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function add() {
        layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/category/add',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    
</script>

</@html>
