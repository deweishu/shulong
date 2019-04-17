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
                    <h5>发送消息推送</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm"  method="post" >
                        <input id="type" name="type" value="10" class="form-control" type="hidden">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">推送标题：</label>
                            <div class="col-sm-8">
                                <input id="title" name="title" placeholder="不超过40字" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">推送内容：</label>
                            <div class="col-sm-8">
                                <textarea id="text" name="text" placeholder="不超过300字" class="form-control" rows="4"></textarea>
                            </div>
                        </div>


                        <div class="form-group" id="appselect" >
                            <label class="col-sm-3 control-label">应用选择：</label>
                            <div class="col-sm-8">
                                <input id="apkId" name="apkId"  class="form-control" type="hidden">
                                <input id="apkName" name="apkName"   onclick="selectApp();" readonly class="form-control" type="text">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否响铃：</label>
                            <div class="col-sm-8">
                                <select name="ring" id="ring">
                                    <option value="true">是</option>
                                    <option value="false">否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否振动：</label>
                            <div class="col-sm-8">
                                <select name="vibrate" id="vibrate">
                                    <option value="true">是</option>
                                    <option value="false">否</option>
                                </select>
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
                title:{
                    maxlength:40,
                    required: true
                },
                text:{
                    maxlength:300,
                    required: true
                }
            },
            messages: {},
            submitHandler:function(form){
                var load=layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/push/msg/send",
                    data: $(form).serialize(),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            layer.closeAll();
                        });
                    }
                });
            }
        });
    });

    function selectApp(){
        layer.open({
            type: 2,
            title: false,
            area: ['80%', '90%'],
            shade: 0.8,
            closeBtn: 1,
            shadeClose: false,
            content: '${ctx!}/apk/select/page',
            end: function(index){

            }
        });
    }
</script>

</body>

</html>
</@html>
