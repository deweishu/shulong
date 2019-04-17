<#include "/macro/frame.ftl">

<@html>
    <@head title="Chain Store管理后台">
    <link href="${static}/common/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css"/>
    </@head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>角色信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" enctype="multipart/form-data" method="post" >
                        <input type="hidden" name="id" value="${(role.id)!''}">
                        <div id="permissionGroups"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">角色名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" value="${(role.name)!''}" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">角色状态：</label>
                            <div class="col-sm-8">
                                <select name="status">
                                    <option value="10" <#if role.status.code == 10>selected</#if> >正常</option>
                                    <option value="-10" <#if role.status.code == -10>selected</#if> >停用</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">角色权限：</label>
                            <div class="col-sm-8">
                                <div id="checkTree">
                                    <ul>
                                        <#list permissionGroups as pg>
                                            <li id="p_${pg.code}" data-jstree='{"opened":true}'>${pg.name}
                                                <ul>
                                                    <#list pg.permissions as p>
                                                        <li id="${p.code}" data-jstree='{<#if p.have>"selected":true,</#if>"type":"file"}'>${p.name}</li>
                                                    </#list>
                                                </ul>
                                            </li>
                                        </#list>
                                        <!--
                                        <li id="ab" data-jstree='{"opened":true}'>Root node
                                            <ul>
                                                <li id="aa" data-jstree='{"icon":"fa fa-warning icon-state-danger icon-md"}'>Custom icon</li>
                                                <li id="bb" data-jstree='{"type":"file"}'>Child node</li>
                                            </ul>
                                        </li>
                                        -->
                                    </ul>
                                </div>
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
<!-- jstree plugin javascript-->
    <@jstree></@jstree>
<script type="text/javascript">
    $('#checkTree').jstree({
        'core' : {
            'themes' : {
                'responsive': false
            }
        },
        'types' : {
            'default' : {
                'icon' : 'fa fa-folder icon-state-info icon-md'
            },
            'file' : {
                'icon' : 'fa fa-file icon-state-default icon-md'
            }
        },
        'plugins' : ['types', 'checkbox']
    });


    $(document).ready(function () {
        $("#frm").validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 32
                }
            },
            messages: {},
            submitHandler:function(form){
                // get selected options
                var ps = $("#checkTree").jstree("get_checked");
                if(ps.length == 0){
                    layer.alert('请选择权限!');
                    return false;
                }
                var h = '';
                for(var i in ps){
                    if(ps[i].startsWith('p_')){
                        continue;
                    }
                    h += '<input type="hidden" name="permissionCodes" value="'+ps[i]+'">';
                }
                $('#permissionGroups').html(h);
                // end selected options
                /*console.log("permissionGroups-->" + $('#permissionGroups').html());*/

                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/role/save",
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