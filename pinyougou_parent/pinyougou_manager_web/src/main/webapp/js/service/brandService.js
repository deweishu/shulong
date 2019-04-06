//定义service层代码 发起http请求与后端交互
app.service("brandService",function ($http) {
    //查询所有
    this.findAll=function () {
        return  $http.get("../brand/findAll.do");
    }

    this.findPage=function (pageNum,pageSize) {
        return $http.get("../brand/findPage.do?pageNum="+pageNum+"&pageSize="+pageSize);
    }

    this.add=function (entity) {
        return $http.post("../brand/add.do",entity);
    }

    this.update=function (entity) {
        return $http.post("../brand/update.do",entity);
    }

    this.findOne=function (id) {
        return $http.get("../brand/findOne.do?id="+id);
    }

    this.dele=function (ids) {
        return $http.get("../brand/delete.do?ids="+ids);
    }

    this.search=function (pageNum,pageSize,searchEntity) {
        return $http.post('../brand/search.do?pageNum='+pageNum+"&pageSize="+pageSize,searchEntity);
    }

    this.selectBrandOptions=function () {
        return $http.get("../brand/selectBrandOptions.do");
    }

})