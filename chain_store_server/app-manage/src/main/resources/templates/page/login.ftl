<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>ChainStore后台登录界面</title>
    <link rel="stylesheet" href="${static}/chain_store/h5/css/back_login.css" />
    <link rel="icon" href="${static}/chain_store/h5/img/favicon.png" />
    <link href="${static}/jia/css/bootstrap.min.css" rel="stylesheet">
    <link href="${static}/jia/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${static}/jia/css/animate.css" rel="stylesheet">
    <link href="${static}/jia/css/style.css" rel="stylesheet">
    <link href="${static}/jia/css/login.css" rel="stylesheet">
</head>
<body>
<div class="body-all">
    <div class="all-content">
        <div class="content-body">
            <h1><img src="${static}/chain_store/h5/image/logo.png" title="ChainStore Logo" /></h1>
            <h2>使用ChainStore账号登录开放平台</h2>
            <#if errMsg?exists >
                <div class="alert alert-danger" style="padding: 10px;">
                ${errMsg!}
                </div>
            </#if>
            <form method="post" action="${ctx!}/login" id="frm">
                <div class="div-username"><input class="input-username" type="text" name="username" placeholder="请输入登录账号" autocomplete="off" /></div>
                <div class="div-password" style="margin-top: 30px;"><input class="input-password" type="password" name="password" placeholder="请输入密码" autocomplete="off" /></div>
                <#--<div class="div-checkbox">-->
                    <#--<input class="input-checkbox" id="input-checkbox" type="checkbox" />-->
                    <#--<label for="input-checkbox">记住密码</label>-->
                <#--</div>-->
                <button class="content-btn" style="margin-top: 30px;" type="submit" >登录</button>
            </form>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="${static}/jia/js/jquery.min.js?v=2.1.4"></script>
<script src="${static}/jia/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${static}/jia/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${static}/jia/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${static}/jia/js/plugins/validate/messages_zh.min.js"></script>
<script type="text/javascript">
    $().ready(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#frm").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 2
                },
                password: {
                    required: true,
                    minlength: 6
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    minlength: "用户名必需由两个或者以上的字母组成"
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 6 位数"
                }
            },
            submitHandler:function(form){
                form.submit();
            }
        });
    });

</script>
</body>
</html>
