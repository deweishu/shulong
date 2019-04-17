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
                    <h5>用户列表数据</h5>
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
                                            <input id="mobile" name="mobile" type="text" class="form-control" placeholder="手机号">
                                        </div>
                                    </div>
                                    <div class="myBtn-content">
                                        <button id="search" type="button" class="btn btn-primary">搜索</button>
                                        <button id="reset" type="button" class="btn btn-default">重置</button>
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

    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/user/data",
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
                    return value==true?"<span class=\"label label-info\">正常</span>":"<span class=\"label label-danger\">封号</span>";
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
            },  {
                title: "注册时间",
                field: "createTime"
            },  {
                title: "注册类型",
                field: "thirdType",
                formatter: function (value, row, index) {
                    if(value==null||value==0){
                        return '手机号注册';
                    }else if(value=='10'){
                        return '微信';
                    }else if(value=='20'){
                        return 'QQ';
                    }else if(value=='30'){
                        return '微博';
                    }
                }
            },  {
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+row.id+'\')">&nbsp;编辑</button> ';
                    operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="candyList(\''+row.id+'\')">&nbsp;糖果记录</button> ';
                    operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="signLog(\''+row.id+'\')">&nbsp;签到记录</button> ';
                    operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="shareLog(\''+row.id+'\')">&nbsp;邀请记录</button> ';
                    if(row.status){
                        operateHtml += '<button class="btn btn-danger btn-xs" type="button" onclick="userStatus(\''+row.id+'\',false)">&nbsp;封号</button> ';
                    }else{
                        operateHtml += '<button class="btn btn-info btn-xs" type="button" onclick="userStatus(\''+row.id+'\',true)">&nbsp;解封</button> ';
                    }
                    return operateHtml;
                }
            }]
        });


        /*搜索按钮*/
        $('#search').click(function () {
            var mobile = $('#mobile').val();
            $('#table_list').bootstrapTable('refresh', {
                url: '${ctx!}/user/data?mobile=' + mobile,
                pageNumber: 1,
                pageSize: 10
            });
        });


        /*重置搜索条件*/
        $('#reset').click(function() {
            $('#mobile').val('');
            $('#table_list').bootstrapTable('refresh', { url: '${ctx!}/user/data', pageNumber: 1, pageSize: 10});
        });


    });

    function candyList(id){
        layer.open({
            type: 2,
            title: '糖果记录',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/candy/index/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function signLog(id) {
        layer.open({
            type: 2,
            title: '签到记录',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/sign/index/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    function shareLog(id){
        layer.open({
            type: 2,
            title: '邀请记录',
            shadeClose: false,
            shade: [0.8, '#393D49'],
            area: ['90%', '80%'],
            content: '${ctx!}/user/invet/index/'+id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }


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

    function userStatus(userId,flag){
        var t="";
        if(flag){
            t="确定解封该账号吗？";
        }else{
            t="确定对该账号进行封号处理吗？";
        }
        layer.confirm(t, {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/user/status/"+userId+"/"+flag,
                success: function(rsp){
                    layer.msg(rsp.msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

</script>

</@html>
