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
                    <h5>管理员信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(admin.id)!''}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机号码：</label>
                            <div class="col-sm-8">
                                <input id="mobile" name="mobile" value="${(admin.mobile)!''}" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名称：</label>
                            <div class="col-sm-8">
                                <input id="username" name="username" value="${(admin.username)!''}" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">登录密码：</label>
                            <div class="col-sm-8">
                                <input id="password" name="password" value="${(admin.password)!''}" class="form-control" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-8">
                                <input id="realname" name="realname" value="${(admin.realname)!''}" class="form-control" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <select name="status">
                                    <option value="10" <#if admin.status.code == 10>selected</#if> >正常</option>
                                    <option value="-10" <#if admin.status.code == -10>selected</#if> >停用</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">角色：</label>
                            <div class="col-sm-8">
                                <#list roles as r>
                                    <#assign isWrite=false>
                                    <#list myRoles as mr> <!-- 遍历管理员拥有的权限 -->
                                        <#if r.id == mr.id> <!-- 有此权限的话, 复选框默认打钩 -->
                                            <#assign isWrite=true>
                                            <div class="checkbox-inline">
                                                <label><input type="checkbox" checked name="roleIds" valid="valid"  value="${r.id}">${r.name}</label>
                                            </div>
                                        </#if>
                                    </#list>
                                    <#if !isWrite>
                                        <div class="checkbox-inline">
                                            <label><input type="checkbox" name="roleIds" valid="valid"  value="${r.id}">${r.name}</label>
                                        </div>
                                    </#if>
                                </#list>
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
    function initial() {
        var id = "${(admin.id)!''}";
        console.log("current id is " + id);
        if (id) {
            console.log("coming if ");
            $("#mobile").attr("readonly", true);
        }
    }

    $(document).ready(function () {
        initial();
        $("#frm").validate({
            rules: {
                mobile: {
                    required: true,
                    minlength: 11,
                    maxlength: 11
                },
                username: {
                    required: true,
                    minlength: 3,
                    maxlength: 32
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 32
                }
            },
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/admin/save",
                    data: $(form).serialize(),
                    success: function(msg){
                        layer.msg(msg.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
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