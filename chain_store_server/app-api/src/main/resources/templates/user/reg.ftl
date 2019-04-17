<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${static}/chain_store/h5/css/register.css" />
    <title>邀请注册</title>
    <link rel="icon" href="${static}/chain_store/h5/img/favicon.png" />
    <style>
        .form-img{
            width: 40%;
            height: 0.8rem;
            margin: auto 0 auto 0.13rem;
        }
    </style>
</head>

<body>
<div id="body-all">
    <div class="register-form">
        <form class="form-main">
            <div class="form-group">
                <input type="text" name="phone" maxlength="11" id="phone" placeholder="请输入手机号" class="form-phone"/>
            </div>
            <div class="form-group1">
                <input type="text" name="imgCode" maxlength="4" id="imgCode" placeholder="请输入图形验证码" class="form-identifying" style="width: 53%!important;" />
                <img src="${ctx!}/kaptcha/img/code" id="regImg" name="regImg" onclick="getImg(this)" class="form-img" >
            </div>
            <div class="form-group1">
                <input type="text" name="smsCode" maxlength="4" id="smsCode" placeholder="请输入短信验证码" class="form-identifying"/>
                <button type="button" onclick="getCode()" id="getCodeBtn" name="getCodeBtn"  class="btn-gain">获取验证码</button>
            </div>
            <div class="form-group">
                <input type="password" name="password" id="password" placeholder="请设置登录密码" class="form-password" maxlength="20"/>
            </div>
            <div class="form-group">
                <input type="text"  placeholder="推荐码：${invetCode!}" class="form-code" readonly="readonly" />
            </div>
            <button type="button" onclick="register()" class="btn-register">立即注册</button>
            <input type="hidden" name="invetCode" id="invetCode" value="${invetCode!}">
            <input type="hidden" name="isSend" id="isSend" value="0">
        </form>
    </div>
</div>

<script type="text/javascript" src="${static}/chain_store/h5/js/jquery-1.11.3.min.js" ></script>
<script type="text/javascript" src="${static}/chain_store/h5/js/flexible.js" ></script>
<script>
    $(function(){
        $('body').height($('body')[0].clientHeight);
    });

    function register() {
        var phone=$("#phone").val();
        var isSend=$("#isSend").val();
        if(isSend==0){
            alert('请先获取验证码');
            return;
        }
        var password=$("#password").val();
        var smsCode=$("#smsCode").val();
        if(smsCode.length==0){
            alert('请输入短信验证码')
        }
        var invetCode=$("#invetCode").val();
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data : JSON.stringify({'mobile':phone,'code':smsCode,'passWord':password,'invetCode':invetCode}),
            dataType: "json",
            url: "${ctx!}/h5/user/submit/reg",
            success: function(msg){
                if(msg.code!=0){
                    alert(msg.msg);
                }else {
                    alert('注册成功，去下载APP')
                    window.location="${ctx!}/h5/app/down";
                }
            }
        });

    }
    function getImg(obj) {
        $(obj).attr("src","${ctx!}/kaptcha/img/code?date="+new Date());
    }
    function getCode() {
        var phone=$("#phone").val();
        var imgCode=$("#imgCode").val();
        if(phone.length!=11){
            alert('请输入11位手机号');
            return;
        }
        if(imgCode.length!=4){
            alert('请输入图形验证码');
            return;
        }
        var invetCode=$("#invetCode").val();
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data : JSON.stringify({'mobile':phone,'moduleType':'10','imgCode':imgCode,'invetCode':invetCode}),
            dataType: "json",
            url: "${ctx!}/h5/user/code",
            success: function(msg){
                if(msg.code!=0){
                    alert(msg.msg);
                    $("#regImg").attr("src","${ctx!}/kaptcha/img/code?date="+new Date());
                }else {
                    settime();
                    $("#isSend").val(1)
                }
            }
        });
    }
    var countdown=60;
    var _generate_code = $("#getCodeBtn");
    function settime() {
        if (countdown == 0) {
            _generate_code.attr("disabled",false);
            _generate_code.text("获取验证码");
            countdown = 60;
            return false;
        } else {
            $("#getCodeBtn").attr("disabled", true);
            _generate_code.text( countdown + "s");
            countdown--;
        }
        setTimeout(function() {
            settime();
        },1000);
    }
</script>

<script>
    $('input[type="text"],textarea').on('click', function () {
        var target = this;
        setTimeout(function(){
            target.scrollIntoViewIfNeeded();
            console.log('scrollIntoViewIfNeeded');
        },400);
    });
</script>
</body>
</html>
