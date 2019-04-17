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
                    <h5>更新合作商信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(customer.id)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">客户名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" value="${(customer.name)!''}" placeholder="合作商公司名称" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机号：</label>
                            <div class="col-sm-8">
                                <input id="phone" name="phone" value="${(customer.phone)!''}" placeholder="登录账号" class="form-control" type="text">
                                <span style="color:red">登录密码默认是123456</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <select name="status">
                                    <option value="true" <#if customer.status==true></#if>>正常</option>
                                    <option value="false" <#if customer.status==false></#if>>封禁</option>
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
                name:{
                    maxlength:50,
                    required: true,
                },
                phone:{
                    required: true
                },
                status:{
                    required: true
                }
            }, 
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/customer/save",
                    data: $(form).serialize(),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            if(msg.msg=='保存成功'){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            }else{
                                return false;
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
