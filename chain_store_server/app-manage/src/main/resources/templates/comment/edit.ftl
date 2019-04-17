<#include "/macro/frame.ftl">

<@html>
    <@head title="系统">

    </@head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>编辑评论信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(commentLog.id)!''}">
                        <input type="hidden" name="userId" value="${(commentLog.userId)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">评分数（1-5）：</label>
                            <div class="col-sm-8">
                                <input id="commentNum" name="commentNum" value="${(commentLog.commentNum)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">评分内容：</label>
                            <div class="col-sm-8">
                                <input id="commentDesc" name="commentDesc" value="${(commentLog.commentDesc)!''}" class="form-control" type="text">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机型号：</label>
                            <div class="col-sm-8">
                                <input id="mobileType" name="mobileType" value="${(commentLog.mobileType)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- 自定义js -->
<script src="${static}/jia/js/jquery.min.js?v=2.1.4"></script>
<script src="${static}/jia/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${static}/jia/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${static}/jia/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${static}/jia/js/plugins/layer/layer.min.js"></script>
<!-- layerDate plugin javascript -->
<script src="${static}/jia/js/plugins/layer/laydate/laydate.js"></script>
<script src="${static}/jia/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${static}/jia/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${static}/jia/js/plugins/validate/messages_zh.min.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $("#frm").validate({
            rules: {
                commentNum:{
                 required: true
                },
                commentDesc:{
                    required: true,
                    maxlength:100
                },
                mobileType:{
                    required: true
                }
            }, 
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/comment/save",
                    data: $(form).serialize(),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name); 
                            parent.layer.close(index);
                        });
                    }
                });
            }
        });
    });
</script>

</body>

</html>
</@html>
