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
                    <h5>编辑信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(appVersion.id)!''}">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用版本号：</label>
                            <div class="col-sm-8">
                                <input id="versionCode" name="versionCode" placeholder="例如：1" value="${(appVersion.versionCode)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用版本名称：</label>
                            <div class="col-sm-8">
                                <input id="versionName" name="versionName" placeholder="例如：v1.2.1" value="${(appVersion.versionName)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">更新包上传：</label>
                            <div class="col-sm-8">
                                <input type="file"  name="apkFile" id="apkFile"  >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">客户端类型：</label>
                            <div class="col-sm-8">
                                <select id="clientType" name="clientType">
                                    <option>---请选择---</option>
                                    <option value="10" <#if appVersion.clientType==10>selected</#if> >安卓</option>
                                    <option value="20" <#if appVersion.clientType==20>selected</#if> >IOS</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">版本状态：</label>
                            <div class="col-sm-8">
                                <select id="versionStatus" name="versionStatus">
                                    <option value="">---请选择---</option>
                                    <option value="true" <#if appVersion.versionStatus==true>selected</#if> >上架</option>
                                    <option value="false" <#if appVersion.versionStatus==false>selected</#if> >下架</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否强制更新：</label>
                            <div class="col-sm-8">
                                <select id="forceUpdate" name="forceUpdate">
                                    <option value="">---请选择---</option>
                                    <option value="true" <#if appVersion.forceUpdate==true>selected</#if> >是</option>
                                    <option value="false" <#if appVersion.forceUpdate==false>selected</#if> >否</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用版本更新说明：</label>
                            <div class="col-sm-8">
                                <textarea id="versionDesc" name="versionDesc" placeholder="不超过1000字" class="form-control" rows="6">${(appVersion.versionDesc)!''}</textarea>
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
                versionCode:{
                 required: true
                },
                versionName:{
                 required: true
                },
                versionDesc:{
                 required: true
                },
                versionStatus:{
                 required: true
                },
                versionSize:{
                 required: true
                },
                 forceUpdate:{
                 required: true
                }
            }, 
            messages: {},
            submitHandler:function(form){
                var apkIndex=layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/sys/appversion/save",
                    async: true,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: new FormData($('#frm')[0]),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            if(msg.code==0){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            }else{
                                layer.close(apkIndex);
                            }
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
