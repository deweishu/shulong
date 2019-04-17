<#include "/macro/frame.ftl">

<@html>
    <@head title="系统">
        <style>

            .btn{
                color: #fff;
                background-color: #337ab7;
                border-color: #2e6da4;
                display: inline-block;
                padding: 6px 12px;
                margin-bottom: 0;
                font-size: 14px;
                font-weight: 400;
                line-height: 1.42857143;
                text-align: center;
                white-space: nowrap;
                text-decoration: none;
                vertical-align: middle;
                -ms-touch-action: manipulation;
                touch-action: manipulation;
                cursor: pointer;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
                background-image: none;
                border: 1px solid transparent;
                border-radius: 4px;
            }
            a.btn:hover{
                background-color: #3366b7;
            }
            .progress{
                margin-top:2px;
                width: 200px;
                height: 14px;
                margin-bottom: 10px;
                overflow: hidden;
                background-color: #f5f5f5;
                border-radius: 4px;
                -webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
                box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
            }
            .progress-bar{
                background-color: rgb(92, 184, 92);
                background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.14902) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.14902) 50%, rgba(255, 255, 255, 0.14902) 75%, transparent 75%, transparent);
                background-size: 40px 40px;
                box-shadow: rgba(0, 0, 0, 0.14902) 0px -1px 0px 0px inset;
                box-sizing: border-box;
                color: rgb(255, 255, 255);
                display: block;
                float: left;
                font-size: 12px;
                height: 20px;
                line-height: 20px;
                text-align: center;
                transition-delay: 0s;
                transition-duration: 0.6s;
                transition-property: width;
                transition-timing-function: ease;
                width: 266.188px;
            }
        </style>
    </@head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>发布新版本</h5>
                </div>
                <div class="ibox-content">
                    <input type="hidden" name="dirname" id="dirname" value="${apkPath!}"/>
                    <input type="radio" name="myradio" value="random_name" checked=true style="display: none;" />
                    <form class="form-horizontal m-t" id="frm"  method="post" >
                        <input type="hidden" name="id" value="${(version.id)!''}">
                        <input type="hidden" name="apkId" value="${apkId!}">
                        <input type="hidden" name="size" id="size" />


                        <div class="form-group">
                            <label class="col-sm-3 control-label">版本号：</label>
                            <div class="col-sm-8">
                                <input id="versionCode" name="versionCode" placeholder="例如：10" value="${(version.versionCode)!''}" class="form-control"
                                       onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " type="text">
                                <br/>
                                <span style="color:red">注意：版本更新将通过版本号来进行判断，请认真填写</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">版本名：</label>
                            <div class="col-sm-8">
                                <input id="versionName" name="versionName" placeholder="例如：v1.1" value="${(version.versionName)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">连接类型：</label>
                            <div class="col-sm-8">
                                <select name="linkType" id="linkType">
                                    <#list typeList as type>
                                        <option value="${type.code!}" <#if version.linkType==type.code>selected</#if> >${type.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" id="urlDiv" style="display: none">
                            <label class="col-sm-3 control-label">链接URL：</label>
                            <div class="col-sm-8">
                                <input id="downUrl" name="downUrl" value="${(version.downUrl)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">客户端类型：</label>
                            <div class="col-sm-8">
                                <select name="apkType" id="apkType">
                                    <#list apkTypeList as type>
                                        <option value="${type.code!}" <#if version.linkType==type.code>selected</#if> >${type.name}</option>
                                    </#list>
                                </select>
                                <span style="color:red">ios安装包要企业证书</span>
                            </div>
                        </div>

                        <div class="form-group" id="apkUpload">
                            <label class="col-sm-3 control-label">安装包上传：</label>
                            <div class="col-sm-8">
                                <div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>
                                <#--<input type="file"  name="apkFile" id="apkFile" accept="apk/*" >-->
                                <div id="container">
                                    <a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
                                    <a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
                                    <P style="font-size: 12px;color:red;">(温馨提示：请先选择文件，然后点击上传)</P>
                                </div>
                                <pre id="console"></pre>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">更新内容：</label>
                            <div class="col-sm-8">
                                <textarea id="versionContent" name="versionContent" placeholder="不超过1000字" class="form-control" rows="6">${(version.versionContent)!''}</textarea>
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


<script type="text/javascript" src="${static}/chain_store/ossfile/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="${static}/chain_store/ossfile/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="${static}/chain_store/ossfile/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="${static}/chain_store/ossfile/base64.js"></script>
<script type="text/javascript" src="${static}/chain_store/ossfile/plupload-2.1.2/js/plupload.full.min.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
        $("#frm").validate({
            rules: {
                versionCode:{
                    number:true,
                    required: true
                },
                versionName:{
                    required: true
                },
                versionContent:{
                    required: true

                }
            }, 
            messages: {},
            submitHandler:function(form){
                var downUrl =$("#downUrl").val();
                if(downUrl.length==0){
                    layer.msg("下载源不能为空");
                    return;
                }
                var apkIndex=layer.load(1);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/version/save",
                    data: $(form).serialize(),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            if(msg.code==0){
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            }else{
                                layer.close(apkIndex);
                            }
                        });
                    }
                });
            }
        });

       $("#linkType").change(function(){
           var tv=$(this).val();
           console.log(tv);
           if(tv==10 || tv==20){
                $("#urlDiv").show();
                $("#apkUpload").hide();
           }else{
               $("#urlDiv").hide();
               $("#apkUpload").show();
           }
       })
    });


    accessid= 'LTAIc3oUI9V0c5h0';
    accesskey= 'ztSGWYr2CEcTOIN9VWTRYthRRmoqgS';
    host = 'https://chainstore.oss-cn-shenzhen.aliyuncs.com';

    g_dirname = ''
    g_object_name = ''
    g_object_name_type = ''
    now = timestamp = Date.parse(new Date()) / 1000;

    var policyText = {
        "expiration": "2020-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
        "conditions": [
            ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
        ]
    };

    var policyBase64 = Base64.encode(JSON.stringify(policyText))
    message = policyBase64
    var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
    var signature = Crypto.util.bytesToBase64(bytes);

    function check_object_radio() {
        var tt = document.getElementsByName('myradio');
        for (var i = 0; i < tt.length ; i++ )
        {
            if(tt[i].checked)
            {
                g_object_name_type = tt[i].value;
                break;
            }
        }
    }

    function get_dirname()
    {
        dir = document.getElementById("dirname").value;
        if (dir != '' && dir.indexOf('/') != dir.length - 1)
        {
            dir = dir + '/'
        }
        //alert(dir)
        g_dirname = dir
    }

    function random_string(len) {
        len = len || 32;
        var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        var maxPos = chars.length;
        var pwd = '';
        for (i = 0; i < len; i++) {
            pwd += chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd;
    }

    function get_suffix(filename) {
        pos = filename.lastIndexOf('.')
        suffix = ''
        if (pos != -1) {
            suffix = filename.substring(pos)
        }
        return suffix;
    }

    function calculate_object_name(filename)
    {
        if (g_object_name_type == 'local_name')
        {
            g_object_name += "${filename}"
        }
        else if (g_object_name_type == 'random_name')
        {
            suffix = get_suffix(filename)
            g_object_name = g_dirname + random_string(10) + suffix
        }
        return ''
    }

    function get_uploaded_object_name(filename)
    {
        if (g_object_name_type == 'local_name')
        {
            tmp_name = g_object_name
            tmp_name = tmp_name.replace("${filename}", filename);
            return tmp_name
        }
        else if(g_object_name_type == 'random_name')
        {
            return g_object_name
        }
    }

    function set_upload_param(up, filename, ret)
    {
        g_object_name = g_dirname;
        if (filename != '') {
            suffix = get_suffix(filename)
            calculate_object_name(filename)
        }
        new_multipart_params = {
            'key' : g_object_name,
            'policy': policyBase64,
            'OSSAccessKeyId': accessid,
            'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
            'signature': signature,
        };

        up.setOption({
            'url': host,
            'multipart_params': new_multipart_params
        });

        up.start();
    }

    var uploader = new plupload.Uploader({
        runtimes : 'html5,flash,silverlight,html4',
        browse_button : 'selectfiles',
        multi_selection: false,
        container: document.getElementById('container'),
        flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
        silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
        url : 'https://oss.aliyuncs.com',
        filters: {
            mime_types : [ //只允许apk文件
                { title : "apk files", extensions : "apk,plist,ipa" }
            ],
            max_file_size : '120mb', //最大只能上传120mb的文件
            prevent_duplicates : true //不允许选取重复文件
        },
        init: {
            PostInit: function() {
                document.getElementById('ossfile').innerHTML = '';
                document.getElementById('postfiles').onclick = function() {
                    set_upload_param(uploader, '', false);
                    return false;
                };
            },

            FilesAdded: function(up, files) {

                plupload.each(files, function(file) {
                    document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                            +'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                            +'</div>';
                    console.log(plupload.formatSize(file.size));
                    $("#size").val(plupload.formatSize(file.size));
                    if (up.files.length > 1) {
                        up.splice(0,1);
                    }else {
                        return;
                    }
                });
            },

            BeforeUpload: function(up, file) {
                check_object_radio();
                get_dirname();
                set_upload_param(up, file.name, true);
            },
            FilesRemoved:function(up,file){
                $("#" + file[0].id).remove();  //删除视图文件
            },
            UploadProgress: function(up, file) {
                var d = document.getElementById(file.id);
                d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                var prog = d.getElementsByTagName('div')[0];
                var progBar = prog.getElementsByTagName('div')[0]
                progBar.style.width= 2*file.percent+'px';
                progBar.setAttribute('aria-valuenow', file.percent);
            },

            FileUploaded: function(up, file, info) {
                if (info.status == 200)
                {
                    var fileName=get_uploaded_object_name(file.name);
                    var path=host+"/"+fileName;
                    console.log("file path: {}",path);
                    // document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:';
                    layer.msg('上传成功');
                    $("#downUrl").val(path);
                }
                else
                {
                    layer.msg('上传失败,'+info.response);
                    // document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
                }
            },

            Error: function(up, err) {
                if (err.code == -600) {
                    layer.msg('选择的文件太大了');
                    // document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了"));
                }
                else if (err.code == -601) {
                    layer.msg('只能上传apk文件');
                    // document.getElementById('console').appendChild(document.createTextNode("\n只能上传apk文件"));
                }
                else if (err.code == -602) {
                    layer.msg('这个文件已经上传过一遍了');
                }
                else
                {
                    document.getElementById('console').appendChild(document.createTextNode("\n 其他错误:" + err.response));
                }
            }
        }
    });

    uploader.init();

</script>

</body>

</html>
</@html>
