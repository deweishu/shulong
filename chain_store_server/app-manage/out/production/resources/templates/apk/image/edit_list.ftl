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
                    <h5>应用演示图</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        <button class="btn btn-info " type="button" onclick="imgList();"><i class="fa fa-file-image-o"></i>&nbsp;纯图片查看</button>
                        &nbsp;&nbsp;
                        <button class="btn btn-info " type="button" onclick="upload();"><i class="fa fa-plus"></i>&nbsp;上传图片</button>
                        <span style="color: red;">注：应用演示图最多只能上传${num!}张</span>
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <div class="form-group">
                                        <button class="btn btn-danger" type="button" onclick="delImg()" ><i class="fa fa-first-order"></i>&nbsp;删除</button>&nbsp;
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
            url: "${ctx!}/img/data?apkId=${objectId!}",
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
            detailView:false,
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
            columns: [ {
                checkbox:true
            }, {
                title: "排序",
                field: "imgSort"
            },  {
                title: "图片url",
                field: "imgUrl",
                formatter: function(value,row,index){
                    return "<img src='"+value+"' width='200px' height='200px' />";
                }
            }, {
                title: "上传时间",
                field: "createTime"
            },{
                title: "功能",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class="btn btn-info btn-xs" type="button" onclick="edit(\''+row.id+'\',\''+row.imgSort+'\')">&nbsp;修改排序</button> ';
                    return operateHtml;
                }
            }]
        });
    });


    function edit(id,imgSort){
        layer.prompt({value: imgSort},function(value, index, elem){
            var iindex=layer.load(1);
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/img/update/sort/"+id+"/"+value,
                success: function(msg){
                    layer.msg(msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(iindex);
                    });
                }
            });
        });
    }


    function delImg() {
        var content = $("#table_list").bootstrapTable('getSelections');
        if(content==null || content=="undefined" ||content.length==0){
            layer.msg('请至少选择一张图片进行删除');
            return;
        }
        var ids="";
        for(var i=0;i<content.length;i++){
            ids+=content[i].id+",";
        }
        layer.confirm('确定删除吗？删除后不可恢复', {icon: 3, title:'提示'}, function(index){
            var iindex=layer.load(1);
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${ctx!}/img/delete/"+ids,
                success: function(msg){
                    layer.msg(msg, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.closeAll();
                    });
                }
            });
        });
    }

    function imgList(){
        var id= "${objectId!}";
        window.location="${ctx!}/file/list/"+id;
    }

    function upload() {
        var id = "${objectId!}";
        var load=layer.load(1);
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "${ctx!}/file/valid/add/"+id,
            success: function(msg){
                if(msg.data==true){
                    layer.close(load);
                    layer.open({
                        type: 2,
                        title: '上传应用演示图',
                        shadeClose: true,
                        shade: false,
                        area: ['95%', '90%'],
                        content: '${ctx!}/file/webupload/'+id,
                        end: function(index){
                        }
                    });
                }else{
                    layer.close(load);
                    layer.open({
                        title: '提示',
                        content: '已经达到上传限制多大张数',
                        end:function(){
                            $('#table_list').bootstrapTable("refresh");
                        }
                    });
                }
            }
        });
    }
</script>

</@html>
