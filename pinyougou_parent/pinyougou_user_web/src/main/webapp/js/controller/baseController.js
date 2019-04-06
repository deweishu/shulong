//父controller
app.controller("baseController",function ($scope) {
    //分页配置
    $scope.paginationConf = {
        currentPage:1,  				//当前页
        totalItems:10,					//总记录数
        itemsPerPage:10,				//每页记录数
        perPageOptions:[10,20,30,40,50], //分页选项，下拉选择一页多少条记录
        onChange:function(){			//页面变更后触发的方法
            $scope.reloadList();		//启动就会调用分页组件
        }
    };

    $scope.reloadList=function () {
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }

//记录保存品牌id列表的数组
    $scope.selectIds=[];

    //更新勾选和取消勾选复选框状态，将id从id列表中添加或移除  $event事件对象，获取操作的事件源对象($event.target)
    $scope.updateSelection=function ($event,id) {
        //判断复选框是勾选还是取消勾选  checked
        //获取操作的事件源对象($event.target) input type="checked"
        if($event.target.checked){
            //勾选
            $scope.selectIds.push(id);
        }else {
            //取消勾选
            //获取当前元素索引
            var index= $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index,1);
        }
    }

    //抽取方法：从对象数组中，基于对象的key获取该对象的value，以逗号拼接
    //[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
    $scope.getValueByKey=function (jsonString,key) {
        var jsonArray = JSON.parse(jsonString);
        var value="";
        for(var i=0;i<jsonArray.length;i++){
            //根据对象key取value   有两种方式 object.key   object[key]
            if(i>0){
                //如果key是变量时，取值方式只能是object[key]
                value+=","+jsonArray[i][key];
            }else{
                value+=jsonArray[i][key];
            }
        }
        return value;
    }

    /*
        [
			{"attributeName":"网络","attributeValue":["移动3G","移动4G"]},
			{"attributeName":"机身内存","attributeValue":["16G","32G"]}
		]
		*/
    //从数组中基于数组中对象的属性值，判断该对象是否存在，并将对象返回
    $scope.getObjectByValue=function (list,key,value) {

        for(var i=0;i<list.length;i++){
            //list[i]={"attributeName":"网络","attributeValue":["移动3G","移动4G"]}
            if(list[i][key]==value){
                return list[i];
            }
        }

        return null;
    }


})