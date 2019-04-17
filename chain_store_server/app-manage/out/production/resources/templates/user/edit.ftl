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
                        <input type="hidden" name="id" value="${(user.id)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主键：</label>
                            <div class="col-sm-8">
                                <input id="id" name="id" value="${(user.id)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机号：</label>
                            <div class="col-sm-8">
                                <input id="mobile" name="mobile" value="${(user.mobile)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">登录密码：</label>
                            <div class="col-sm-8">
                                <input id="passWord" name="passWord" value="${(user.passWord)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否封号：</label>
                            <div class="col-sm-8">
                                <input id="status" name="status" value="${(user.status)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">头像url：</label>
                            <div class="col-sm-8">
                                <input id="headImg" name="headImg" value="${(user.headImg)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户糖果余额：</label>
                            <div class="col-sm-8">
                                <input id="candyAmount" name="candyAmount" value="${(user.candyAmount)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-8">
                                <input id="nickName" name="nickName" value="${(user.nickName)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">邀请人id：</label>
                            <div class="col-sm-8">
                                <input id="invetId" name="invetId" value="${(user.invetId)!''}" class="form-control" type="text">
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
		id:{
		 required: true
		},
		mobile:{
		 required: true
		},
		passWord:{
		 required: true
		},
		status:{
		 required: true
		},
		headImg:{
		 required: true
		},
		candyAmount:{
		 required: true
		},
		nickName:{
		 required: true
		},
		invetId:{
		 required: true
		}
            }, 
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/请求路径，记得修改",
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
