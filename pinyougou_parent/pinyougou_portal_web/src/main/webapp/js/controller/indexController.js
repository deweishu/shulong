app.controller("indexController",function ($scope,contentService) {
    
    //基于广告分类id查询广告数据
    $scope.findContentByCategoryId=function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            $scope.contentList=response;
        });
    }

    //搜索方法定义
    $scope.search=function () {
        //angularjs地址路由传参，注意在问号前面加#
        location.href="http://search.pinyougou.com/search.html#?keywords="+$scope.keywords;
    }

})