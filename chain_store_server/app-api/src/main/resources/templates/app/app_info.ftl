<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!-- H5页面窗口自动调整到设备宽度，并禁止用户缩放页面 -->
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <!-- 忽略将页面中的数字识别为电话号码 -->
    <meta name="format-detection" content="telephone=no">
    <!-- 忽略Android平台中对邮箱地址的识别 -->
    <meta name="format-detection" content="email=no">
    <!-- 设置网页可以被搜索引擎搜索到 -->
    <meta name="robots" content="all">
    <!-- 强制竖屏 -->
    <meta name="x5-orientation" content="portrait">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <script type="text/javascript" src="${static}/chain_store/h5/js/jquery-3.1.1.min.js" ></script>
    <link rel="stylesheet" href="${static}/chain_store/h5/css/info.css" />
    <link rel="stylesheet" href="${static}/chain_store/h5/css/swiper.min.css" />
    <link rel="icon" href="${static}/chain_store/h5/img/favicon.png" />
    <title>链商店</title>
    <style type="text/css">
        *{margin:0; padding:0;}
        a{text-decoration: none;}
        img{max-width: 100%; height: auto;}
        .weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
        .weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
    </style>
</head>
<body>
<div id="picb">
    <div class="swiper-container swiper-1 swiper-container2">
        <div class="swiper-wrapper">
            <#list imgList as img>
                <div class="swiper-slide">
                    <img src="${img.imgUrl!}" width="1170px" height="2340px" />
                </div>
            </#list>
        </div>
    </div>
</div>

<div class="body-all">

    <div class="body-header clearfix">
        <div class="header-logo">
            <img src="${appInfo.logo!}" />
        </div>
        <div class="header-title">
            <ul>
                <li>${appInfo.name!}</li>
                <li>${appInfo.downNum!}次下载</li>
                <li>大小：${version.size!}</li>
            </ul>
        </div>
        <div class="header-official">
            <img src="${static}/chain_store/h5/img/official.png"/>
        </div>
    </div>

    <div class="body-banner">
        <div class="swiper-container swiper-1 swiper-container1 swiper-container-horizontal" id="banner-pic">
            <div class="swiper-wrapper">
            <#list imgList as img>
                <div class="swiper-slide">
                    <img src="${img.imgUrl!}" width="1170px" height="2340px" />
                </div>
            </#list>
            </div>
        </div>
    </div>

    <div class="body-information">
        <ul>
            <li>基本信息</li>
            <li>当前版本：${version.versionName!}</li>
            <li>更新时间：${version.createTime!}</li>
        </ul>
    </div>

    <div class="body-desc">
        <ul>
            <li>应用描述</li>
            <li>
                <div id="o-desc" style="line-height: 0.6rem;">
                    <p>${appInfo.describe!}</p>
                </div>
            </li>
        </ul>
    </div>

    <div class="body-recommend">
        <ul>
            <li>应用特色</li>
            <li>
                <div id="o-desc1">
                    <p>${appInfo.special!}</p>
                </div>
            </li>
        </ul>
    </div>

    <div class="body-footer">
        <div class="footer-right">
            <a href="javascript:downApk();" class="btn">立即下载</a>
        </div>
        <div class="footer-left clearfix">
            <div class="footer-pic">
                <img src="${static}/chain_store/h5/img/download1.png"/>
            </div>
            <div class="footer-title">
                <h3>ChainStore</h3>
                <h4>应用下载神器</h4>
            </div>
        </div>
    </div>

</div>
<div class="weixin-tip">
    <p>
        <img src="${static}/chain_store/h5/img/live_weixin.png" alt="微信打开"/>
    </p>
</div>
<script type="text/javascript" src="${static}/chain_store/h5/js/flexible.js"></script>
<script type="text/javascript" src="${static}/chain_store/h5/js/swiper.min.js" ></script>

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
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            var src = '${android.downloadUrl!}';
            if(isiOS){
                src="https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/ios/chain_store.plist";
                window.location.href="itms-services://?action=download-manifest&url="+src;
            }else {
                var form = document.createElement('form');
                form.action = src;
                document.getElementsByTagName('body')[0].appendChild(form);
                form.submit();
            }
        }
    }

    var swiper = new Swiper('.swiper-container1', {
        slidesPerView: 'auto',
        spaceBetween: 0,
    });


    var swiper3 = new Swiper('.all-recommend', {
        slidesPerView: 'auto',
        spaceBetween: 0,
    });

    $("#dshow").click(function(){
        dshow($(this));
    });
    // function dshow(data){
    //      var h=data.parent().find('div').html();
    //      var t=data.parent().find('div').data('title');
    //     data.parent().find('div').html(t);
    //     data.parent().find('div').data('title',h);
    //     data.html()=='更多'? data.html('收起'): data.html('更多');
    //
    // }
    $("#picb").hide();
    $("#banner-pic img").each(function(index){
        $(this).click(function(){
            // $(".display_none").show();
            $("#picb").show();
            var swiper2 = new Swiper('.swiper-container2', {
                initialSlide:index,
            });
        })
    })


    $("#picb").click(function(){
        // $(".display_none").hide();
        $("#picb").hide();
    })


    //判断是ios还是android的方法
    var u = navigator.userAgent.toLowerCase();
    var isApple = /(iphone|ipad|ipod|ios)/i.test(u);
    var isAndroid = /android/i.test(u);
    // var con=$(".container-tel a").html();

    var myArray = new Array();
    if($("input[name=isfx]").val()==null){
        var isfx=0;
    }


    function aclick(data){

        var obj2 = {
            id: data.attr('data-id'),
            app_name:data.attr('data-app_name'),
            wallet:data.attr('data-wallet'),//糖果数
            ios_url:data.attr('data-ios_url'),
            type:data.attr('data-type'),
            // url:$(this).attr('data-url'),
            andr_apk:data.attr('data-andr_apk'),
            andr_url:data.attr('data-andr_url'),
            andr_banben:data.attr('data-andr_banben'),
            andr_baoname:data.attr('data-andr_baoname'),
        };

        clickObj(obj2);
    };


    //点击交互
    $(".all-recommend-list a").click(function(){
        var obj2 = {
            id: $(this).attr('data-id'),
            app_name:$(this).attr('data-app_name'),
            wallet:$(this).attr('data-wallet'),//糖果数
            ios_url:$(this).attr('data-ios_url'),
            type:$(this).attr('data-type'),
            // url:$(this).attr('data-url'),
            andr_apk:$(this).attr('data-andr_apk'),
            andr_url:$(this).attr('data-andr_url'),
            andr_banben:$(this).attr('data-andr_banben'),
            andr_baoname:$(this).attr('data-andr_baoname'),
        }
        clickObj(obj2);
    });


    function clickObj(obj){
        if (isApple) {
            obj=JSON.stringify(obj);

            //apple终端
            window.webkit.messageHandlers.iOSDownid.postMessage(obj);
        } else if (isAndroid) {
            //安卓终端
            obj=JSON.stringify(obj).replace('"{', '{').replace('}"', '}')
            test.js_Downid(obj);
        }
    }
</script>
</body>
</html>
