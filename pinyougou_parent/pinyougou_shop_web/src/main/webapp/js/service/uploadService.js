app.service("uploadService",function ($http) {

    this.upload=function () {
        //基于html5中的对象获取(追加)上传文件
        var formData = new FormData();
        //参数一：后端接收文件的参数名称 参数二：获取文件，其中file代表<input type="file" id="file" />中的id
        formData.append("file",file.files[0]);

        return $http({
            method:"post",
            url : "../upload.do",
            data : formData,
            headers : {'Content-Type' : undefined}, //上传文件必须是这个类型，默认text/plain  作用:相当于设置enctype="multipart/form-data"
            transformRequest : angular.identity  //对整个表单进行二进制序列化
        });
    }

})