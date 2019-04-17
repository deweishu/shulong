<#macro html>
<!DOCTYPE html>
<html>
    <#nested>
</html>
</#macro>

<#macro head title>
<head>
    <#-- meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <#-- 标题 -->
    <title>${title?if_exists}</title>
    <link rel="icon" href="${static}/chain_store/img/manage-icon.png" />
    <link href="${static}/jia/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${static}/jia/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${static}/jia/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${static}/jia/css/animate.css" rel="stylesheet">
    <link href="${static}/jia/css/style.css?v=4.1.0" rel="stylesheet">
        <style>
            .my-container {
                float: left;
                display: inline-block;
                margin-right:30px;
            }

            .myLabel-content ,.myText-content,.myBtn-content{
                display: inline-block;
                margin-left: 10px;
            }
            .myLabel-content,.myText-content input[type='text'],.myBtn-content .btn {
                height: 30px;
                font-size: 12px;
            }
            .myBtn-content .btn {
                margin-bottom: 10px;
            }
        </style>

    <#nested>
</head>
</#macro>

<#macro body>
<body class="page-header-fixed">
    <#nested>
</body>
</#macro>


<#-- js -->
    <#macro footerJs>
    <!-- 全局js -->
    <script src="${static}/jia/js/jquery.min.js?v=2.1.4"></script>
    <script src="${static}/jia/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${static}/jia/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${static}/jia/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${static}/jia/js/plugins/layer/layer.min.js"></script>
    <!-- jQuery Validation plugin javascript-->
    <script src="${static}/jia/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${static}/jia/js/plugins/validate/messages_zh.min.js"></script>

    <#nested>
</#macro>

<#macro jstree>
<script src="${static}/common/plugins/jstree/jstree.min.js"></script>
</#macro>