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
                        <input type="hidden" name="id" value="${(banner.id)!''}">
                        <input type="hidden" name="status" value="${(banner.status)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span style="color:red">*</span>banner主图：</label>
                            <div class="col-sm-8">
                                <div id="preview2">
                                    <#if banner.logo!=null >
                                        <input type="hidden" name="logo" value="${(banner.logo)!''}" />
                                        <img src="${(banner.logo)!""}" width="450px" height="150px" id="imghead2">
                                    </#if>
                                </div>
                                <input type="file" onchange="previewImage(this,2)" name="logoFile" id="logoFile" accept="image/*" >
                                <br/><br/>
                                <span>建议尺寸：1080*700，如果图片过大， 建议先压缩再上传，压缩地址：https://tinypng.com/</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span style="color:red">*</span>连接类型：</label>
                            <div class="col-sm-8">
                                <select name="linkType" id="linkType">
                                    <#list typeList as type>
                                        <option value="${type.code!}" <#if banner.linkType==type.code>selected</#if> >${type.name!}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" id="link">
                            <label class="col-sm-3 control-label">连接url：</label>
                            <div class="col-sm-8">
                                <input id="url" name="url" value="${(banner.url)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group" id="appselect" style="display: none;">
                            <label class="col-sm-3 control-label">应用选择：</label>
                            <div class="col-sm-8">
                                <input id="apkId" name="apkId" value="${(banner.apkId)!''}" class="form-control" type="hidden">
                                <input id="apkName" name="apkName" value="${(banner.apkName)!''}"  onclick="selectApp();" readonly class="form-control" type="text">
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
                url:{
                 required: true
                },
                linkType:{
                 required: true
                },
                apkId:{
                 required: true
                }
            }, 
            messages: {},
            submitHandler:function(form){
                layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/banner/save",
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
        
        $("#linkType").change(function () {
            var v=$(this).val();
            if(v==10){
                $("#link").show();
                $("#appselect").hide();
            }else if(v==20){
                $("#link").hide();
                $("#appselect").show();
            }else if(v==30){
                $("#link").hide();
                $("#appselect").hide();
            }
        })

        var stype="${banner.linkType!}";
        console.log(stype);
        if(stype==20){
            $("#appselect").show();
            $("#link").hide();
        }
        else if(stype==10){
            $("#link").show();
            $("#appselect").hide();
        }else{
            $("#link").hide();
            $("#appselect").hide();
        }
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
