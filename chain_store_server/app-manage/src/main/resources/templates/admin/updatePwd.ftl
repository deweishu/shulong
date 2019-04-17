<#include "/macro/frame.ftl">

<@html>
    <@head title="Chain Store管理后台">

    </@head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>修改登录密码</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/admin/user/updatePwd">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">原始密码：</label>
                                <div class="col-sm-8">
                                    <input id="originalPwd" name="originalPwd" class="form-control" type="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新 密 码：</label>
                                <div class="col-sm-8">
                                    <input id="newPwd" name="newPwd" class="form-control" type="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">确认密码：</label>
                                <div class="col-sm-8">
                                    <input id="confirmPwd" name="confirmPwd" class="form-control" type="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="submit">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <@footerJs>
        <#-- OTHER JS -->
    </@footerJs>
    <script type="text/javascript">
    $(document).ready(function () {
	    $("#frm").validate({
    	    rules: {
    	    	originalPwd: {
    	        required: true,
    	        minlength: 6,
    	    	maxlength: 20
    	      },
    	      newPwd: {
    	        required: true,
    	        minlength: 6,
    	    	maxlength: 20
    	      },
    	      confirmPwd: {
    	        required: true,
    	        minlength: 6,
    	    	maxlength: 20
    	      }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    	$.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/admin/update/pwd",
   	    		   data: $(form).serialize(),
   	    		   success: function(result){
   	    		   		if(result.code ==0){
                            window.location.href = "${ctx!}/logout";
                            layer.msg(result.msg, {time: 2000},function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index);
                            });
   	    		   		} else {
                            layer.msg(result.msg, {time: 2000});
                        }

   	    		   }
   	    		});
            } 
    	});
    });
    </script>

</body>

</html>
</@html>