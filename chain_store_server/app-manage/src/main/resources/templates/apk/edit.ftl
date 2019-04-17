<#include "/macro/frame.ftl">
<#setting number_format="#">
<@html>
    <@head title="系统">

    </@head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>创建/更新应用信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(apk.id)!''}">
                        <input type="hidden" name="customerId" value="${(customerId)!''}">
                        <input type="hidden" name="status" value="${(apk.status)!''}">
                        <input id="apkResource" name="apkResource" value="${(apk.apkResource)!''}"  type="hidden">
                        <input id="plistFile" name="plistFile" value="${(apk.plistFile)!''}"  type="hidden">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用分类：</label>
                            <div class="col-sm-8">
                                <select id="categoryId" name="categoryId">
                                    <option value="">---请选择---</option>
                                    <#list categoryList as cate>
                                        <option value="${cate.id!}"  <#if apk.categoryId==cate.id>selected</#if> >${cate.name!}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用名字：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" <#if apk.name!=null >readonly</#if>  value="${(apk.name)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">客户端类型：</label>
                            <div class="col-sm-8">
                                <select name="clientType" id="clientType" onchange="changeClient(this)">
                                    <option value="">---请选择客户端类型----</option>
                                    <#list typeList as type>
                                        <option value="${type.code!}" <#if type.code==apk.clientType>selected</#if> >${type.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>


                        <div class="form-group" id="packageDiv" >
                            <label class="col-sm-3 control-label">安卓包名：</label>
                            <div class="col-sm-8">
                                <input id="packageName" name="packageName" <#if apk.packageName!=null && (apk.status==10 || apk.status==30) >readonly</#if>  value="${(apk.packageName)!''}" class="form-control" type="text">
                            </div>
                        </div>



                        <div class="form-group" id="buildDiv"  >
                            <label class="col-sm-3 control-label">Build Id：</label>
                            <div class="col-sm-8">
                                <input id="buildId" name="buildId" <#if apk.packageName!=null  && (apk.status==10 || apk.status==30) >readonly</#if>  value="${(apk.buildId)!''}" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">LOGO：</label>
                            <div class="col-sm-8">
                                <div id="preview2">
                                    <#if apk.logo!=null >
                                        <input type="hidden" name="logo" value="${(apk.logo)!''}" />
                                        <img src="${(apk.logo)!""}" width="100px" height="100px" id="imghead2">
                                    </#if>
                                </div>
                                <input type="file" onchange="previewImage(this,2)" name="logoFile" id="logoFile" accept="image/*" >
                                <br/><br/>
                                <span>建议尺寸：100*100</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用主图：</label>
                            <div class="col-sm-8">
                                <div id="preview3">
                                    <#if apk.mainImg!=null >
                                        <input type="hidden" name="mainImg" value="${(apk.mainImg)!''}" />
                                        <img src="${(apk.mainImg)!""}" width="300px" height="100px" id="imghead3">
                                    </#if>
                                </div>
                                <input type="file" onchange="previewImage(this,3)" name="mainImgFile" id="mainImgFile" accept="image/*" >
                                <br/><br/>
                                <span>首页的应用介绍图，建议尺寸：1020*400</span>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有广告：</label>
                            <div class="col-sm-8">
                                <select name="haveAd" id="haveAd">
                                    <option value="">---请选择--</option>
                                    <option value="true" <#if apk.haveAd==true>selected</#if>>是</option>
                                    <option value="false" <#if apk.haveAd==false>selected</#if>>否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有插件：</label>
                            <div class="col-sm-8">
                                <select name="havePlugin" id="havePlugin">
                                    <option value="">---请选择--</option>
                                    <option value="true" <#if apk.havePlugin==true>selected</#if>>是</option>
                                    <option value="false" <#if apk.havePlugin==false>selected</#if>>否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否人工亲测：</label>
                            <div class="col-sm-8">
                                <select name="havePeople" id="havePeople">
                                    <option value="">---请选择--</option>
                                    <option value="true" <#if apk.havePeople==true>selected</#if>>是</option>
                                    <option value="false" <#if apk.havePeople==false>selected</#if>>否</option>
                                </select>
                            </div>
                        </div>

                        <#if partner==false>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">初始下载量：</label>
                                <div class="col-sm-8">
                                    <input id="downNum" name="downNum" value="${(apk.downNum)!''}" class="form-control" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">下载奖励糖果数量：</label>
                                <div class="col-sm-8">
                                    <input id="candyNum" name="candyNum" value="${(apk.candyNum)!''}" placeholder="不奖励，请输入0" maxlength="5" class="form-control" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">分享奖励糖果数量：</label>
                                <div class="col-sm-8">
                                    <input id="shareCandy" name="shareCandy" value="${(apk.shareCandy)!''}" placeholder="不奖励，请输入0" maxlength="5" class="form-control" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">每天分享奖励次数：</label>
                                <div class="col-sm-8">
                                    <input id="shareMaxNum" name="shareMaxNum" value="${(apk.shareMaxNum)!''}" placeholder="每天分享奖励次数" maxlength="5" class="form-control" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">评论送糖果数：</label>
                                <div class="col-sm-8">
                                    <input id="commentCandy" name="commentCandy" value="${(apk.commentCandy)!''}" placeholder="评论送糖果数" maxlength="5" class="form-control" type="text">
                                </div>
                            </div>
                        </#if>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">开发者名称：</label>
                            <div class="col-sm-8">
                                <input id="devName" name="devName"  value="${(apk.devName)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">开发者官网：</label>
                            <div class="col-sm-8">
                                <input id="devUrl" name="devUrl"    value="${(apk.devUrl)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">适用年龄：</label>
                            <div class="col-sm-8">
                                <input id="applyAge" name="applyAge"   value="${(apk.applyAge)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">语言：</label>
                            <div class="col-sm-8">
                                <input id="laguage" name="laguage"   value="${(apk.laguage)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">应用描述：</label>
                            <div class="col-sm-8">
                                <textarea id="describe" name="describe" placeholder="不超过1000字" class="form-control" rows="6">${(apk.describe)!''}</textarea>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-sm-3 control-label">软件特色：</label>
                            <div class="col-sm-8">
                                <textarea id="special" name="special" placeholder="不超过500字" class="form-control" rows="4">${(apk.special)!''}</textarea>
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
                name:{
                    maxlength:25,
                    required: true
                },
                logo:{
                    required: true
                },
                clientType:{
                    required: true
                },
                describe:{
                    maxlength:1000,
                    required: true
                },
                haveAd:{
                    required: true
                },
                havePlugin:{
                    required: true
                },
                havePeople:{
                    required: true
                },
                candyNum:{
                    number:true,
                    required: true
                },
                special:{
                    maxlength:500,
                    required: true
                },
                categoryId:{
                    required: true
                }
            },
            messages: {},
            submitHandler:function(form){
                var addIndex=layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/apk/save",
                    async: true,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: new FormData($('#frm')[0]),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name);
                            if(msg.code==0){
                                parent.layer.close(index);
                            }
                            layer.close(addIndex);
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

    function changeClient(obj) {
        var val=$(obj).val();
        if(val==10){
            $("#packageDiv").show();
            $("#buildDiv").hide();
        }else if(val==20){
            $("#packageDiv").hide();
            $("#buildDiv").show();
        }else{
            $("#packageDiv").show();
            $("#buildDiv").show();
        }
    }

</script>

</body>

</html>
</@html>
