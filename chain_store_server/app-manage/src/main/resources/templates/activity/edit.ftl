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
                        <input type="hidden" name="id" value="${(activity.id)!''}">
                        <input type="hidden" name="status" value="${(activity.status)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动类型：</label>
                            <div class="col-sm-8">
                                <select name="type">
                                    <option value="">---请选择---</option>
                                    <#list typeList as type>
                                        <option value="${type.code}" <#if activity.type==type.code>selected</#if> >${type.name!}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动标题：</label>
                            <div class="col-sm-8">
                                <input id="title" name="title" placeholder="请输入活动标题，不超过50字" maxlength="50" value="${(activity.title)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动主图：</label>
                            <div class="col-sm-8">
                                <div id="preview2">
                                    <#if activity.mainImg!=null >
                                        <input type="hidden" name="mainImg" value="${(activity.mainImg)!''}" />
                                        <img src="${(activity.mainImg)!""}" width="450px" height="150px" id="imghead2">
                                    </#if>
                                </div>
                                <input type="file" onchange="previewImage(this,2)" name="mainImgFile" id="mainImgFile" accept="image/*" >
                                <br/><br/>
                                <span>建议尺寸：1040*300，如果图片过大， 建议先压缩再上传，压缩地址：https://tinypng.com/</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动logo：</label>
                            <div class="col-sm-8">
                                <div id="preview3">
                                    <#if activity.logo!=null >
                                        <input type="hidden" name="logo" value="${(activity.logo)!''}" />
                                        <img src="${(activity.logo)!""}" width="100px" height="100px" id="imghead3">
                                    </#if>
                                </div>
                                <input type="file" onchange="previewImage(this,3)" name="logoFile" id="logoFile" accept="image/*" >
                                <br/><br/>
                                <span>建议尺寸：100*100</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动链接：</label>
                            <div class="col-sm-8">
                                <input id="linkUrl" name="linkUrl" value="${(activity.linkUrl)!''}" placeholder="例如：http://www.baidu.com" class="form-control" type="text">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动简要：</label>
                            <div class="col-sm-8">
                                <textarea id="infomation" name="infomation" placeholder="不超过500字" class="form-control" rows="4">${(activity.infomation)!''}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">活动规则：</label>
                            <div class="col-sm-8">
                                <textarea id="rule" name="rule" class="form-control" placeholder="不超过500字" rows="4">${(activity.rule)!''}</textarea>
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
                logo:{
                 required: true
                },
                title:{
                 required: true
                },
                linkUrl:{
                 required: true
                },
                type:{
                 required: true
                },
                rule:{
                 required: true
                },
                infomation:{
                 required: true
                }
            }, 
            messages: {},
            submitHandler:function(form){
                layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/activity/save",
                    async: true,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: new FormData($('#frm')[0]),
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



    //图片预览功能
    function previewImage(file,imgNum)
    {
        var MAXWIDTH  = 200;
        var MAXHEIGHT = 200;
        var div = document.getElementById('preview'+imgNum);
        if (file.files && file.files[0])
        {
            div.innerHTML ='<img id=imghead'+imgNum+'>';
            var img = document.getElementById('imghead'+imgNum+'');
            img.onload = function(){
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
                img.style.marginTop = rect.top+'px';
            }
            var reader = new FileReader();
            reader.onload = function(evt){img.src = evt.target.result;}
            reader.readAsDataURL(file.files[0]);
        }
    }
    function clacImgZoomParam( maxWidth, maxHeight, width, height ){
        var param = {top:0, left:0, width:width, height:height};
        if( width>maxWidth || height>maxHeight )
        {
            rateWidth = width / maxWidth;
            rateHeight = height / maxHeight;

            if( rateWidth > rateHeight )
            {
                param.width =  maxWidth;
                param.height = Math.round(height / rateWidth);
            }else
            {
                param.width = Math.round(width / rateHeight);
                param.height = maxHeight;
            }
        }
        param.left = Math.round((maxWidth - param.width) / 2);
        param.top = Math.round((maxHeight - param.height) / 2);
        return param;
    }

</script>

</body>

</html>
</@html>
