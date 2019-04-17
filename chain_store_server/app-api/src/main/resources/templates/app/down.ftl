<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${static}/chain_store/h5/css/down.css" />
    <title>注册完成</title>
    <style type="text/css">
        *{margin:0; padding:0;}
        a{text-decoration: none;}
        img{max-width: 100%; height: auto;}
        .weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
        .weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
        #downBtn1{

            width: 2.77rem;
            height: 0.74rem;
        }

        #downBtn2{

            width: 2.77rem;
            height: 0.74rem;
        }
    </style>
    <link rel="icon" href="${static}/chain_store/h5/img/favicon.png" />
</head>

<body>
<div id="body-all">

    <div class="body-main">
        <div class="body-content">
            <h1>&nbsp;恭喜注册成功！</h1>
            <ul>
                <li>恭喜您已获得<strong>${regCandyNum!}</strong>糖果</li>
                <li>登陆后可进入"我的" → "糖果明细"查看</li>
                <li>邀请好友注册可获得<strong>${invetNum!}</strong>糖果</li>
                <li>马上去下载应用邀请好友吧！</li>
            </ul>
        </div>

        <div class="body-download">
            <div class="download-IOS">

                <!-- 	<img src="https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/h5/img/IOS.png" /> -->
                <img style="width: 1.87rem;height: 1.87rem;" src="data:image/png;base64,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" />



            </div>
            <div class="download-Android" style="display: flex;flex-direction: column;
             justify-content: center;">
                <a href="javascript:downApk();">
                    <img id="downBtn1" src="data:image/png;base64,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">


                    </img>
                    <a href="javascript:downPlist();">
                        <img id="downBtn2" src="data:image/png;base64,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">

                        </img>
                    </a>
            </div>
        </div>
    </div>



</div>
<div class="weixin-tip">
    <p>
        <img src="${static}/chain_store/h5/img/live_weixin.png" alt="微信打开"/>
    </p>
</div>
<script type="text/javascript" src="${static}/chain_store/h5/js/jquery-1.11.3.min.js" ></script>
<script type="text/javascript" src="${static}/chain_store/h5/js/flexible.js" ></script>
<script>
    $(function(){
        $('body').height($('body')[0].clientHeight);
    });
</script>

<script>
    var isWeixin;
    var winHeight = $(window).height();
    $(window).on("load",function(){
        function is_weixin() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        }
        isWeixin = is_weixin();

    })
    function downApk(){
        if(isWeixin){
            $(".weixin-tip").css("height",winHeight);
            $(".weixin-tip").show();
        }else{
            var src = '${android.downloadUrl!}';
            var form = document.createElement('form');
            form.action = src;
            document.getElementsByTagName('body')[0].appendChild(form);
            form.submit();
        }
    }

    function downPlist(){
        if(isWeixin){
            $(".weixin-tip").css("height",winHeight);
            $(".weixin-tip").show();
        }else{
            var src = 'https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/ios/chain_store.plist';
            window.location.href="itms-services://?action=download-manifest&url="+src;
        }

    }
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
