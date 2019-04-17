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
                    <h5>应用评论数据</h5>
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
                                            <input id="commentDesc" name="commentDesc" type="text" class="form-control" placeholder="评论内容（包含）">
                                        </div>
                                    </div>
                                    <div class="my-container">
                                        <div class="myText-content">
                                            <select id="commentNum" name="commentNum">
                                                <option value="">--分数--</option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="myBtn-content">
                                        <button id="search" type="button" class="btn btn-primary">搜索</button>
                                        <button id="reset" type="button" class="btn btn-default">重置</button>
                                    </div><br><br>
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
            url: "${ctx!}/comment/data?apkId=${apkId!}",
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
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [  {
                title: "评分数",
                field: "commentNum"
            }, {
                title: "点赞数",
                field: "loveNum"
            },{
                title: "用户昵称",
                field: "userName"
            }, {
                title: "手机型号",
                field: "mobileType"
            }, {
                title: "评论时间",
                field: "createTime"
            }, {
                title: "更新时间",
                field: "updateTime"
            }, {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-danger btn-xs" type="button" onclick="delComment(\''+row.id+'\')">&nbsp;删除</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    /*搜索按钮*/
    $('#search').click(function () {
        var commentDesc = $('#commentDesc').val();
        var commentNum = $('#commentNum').val();
        $('#table_list').bootstrapTable('refresh', {
            url: '${ctx!}/comment/data?apkId=${apkId!}&commentDesc=' + commentDesc+'&commentNum='+commentNum,
            pageNumber: 1,
            pageSize: 10
        });
    });


    /*重置搜索条件*/
    $('#reset').click(function() {
        $('#commentDesc').val('');
        $('#commentNum').val('');
        $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/comment/data?apkId=${apkId!}', pageNumber: 1, pageSize: 10});
    });


    function delComment(id){
        var s="确定删除该评论吗？";
        layer.confirm(s, {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/comment/del/"+id,
                success: function(msg){
                    layer.msg(msg.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>评论内容:</b> ' + row.commentDesc + '</p>');
        return html.join('');
    }

    
</script>

</@html>
