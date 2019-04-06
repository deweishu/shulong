app.service("loginService",function ($http) {

    //获取用户名
    this.getLoginName=function () {
        return $http.get("../login/getLoginName.do");
    }
});