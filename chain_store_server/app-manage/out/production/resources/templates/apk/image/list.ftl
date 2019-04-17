<#include "/macro/frame.ftl">

<@html>
    <@head title="系统">
    <link href="${static}/jia/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
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

                    </p>
                    <hr>
                    <#list imgList as img>
                        <a class="fancybox" href="${img.imgUrl}" title="应用演示图">
                            <img alt="image" src="${img.imgUrl}" />
                        </a>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

    <@footerJs>

    </@footerJs>

<!-- Peity -->
<script src="${static}/jia/js/plugins/peity/jquery.peity.min.js"></script>
<!-- Fancy box -->
<script src="${static}/jia/js/plugins/fancybox/jquery.fancybox.js"></script>
<!-- 自定义js -->
<script src="${static}/jia/js/content.js?v=1.0.0"></script>
<script>
    $(document).ready(function () {
        $('.fancybox').fancybox({
            openEffect: 'none',
            closeEffect: 'none'
        });
    });
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
