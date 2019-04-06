 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller   ,typeTemplateService,brandService,specificationService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
               /* response={
                    "brandIds": "[{\"id\":1,\"text\":\"联想\"},{\"id\":2,\"text\":\"华为\"},{\"id\":3,\"text\":\"三星\"},{\"id\":4,\"text\":\"小米\"},{\"id\":9,\"text\":\"苹果\"}]",
                    "customAttributeItems": "[{\"text\":\"三年保修\"}]",
                    "id": 39,
                    "name": "平板电脑",
                    "specIds": "[{\"id\":32,\"text\":\"机身内存\"},{\"id\":27,\"text\":\"网络\"}]"
                }

                */
				$scope.entity= response;
				//需要将返回的品牌、规格、扩展属性json格式字符串转为json对象
                $scope.entity.brandIds=JSON.parse($scope.entity.brandIds);
                $scope.entity.specIds=JSON.parse($scope.entity.specIds);
                $scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTemplateService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

    $scope.brandList = {
        data: []
    };

	//查询模板关联的品牌下拉列表
	$scope.selectBrandList=function () {
		brandService.selectBrandOptions().success(function (response) {
            $scope.brandList.data=response;
        })
    }

    $scope.specList = {
        data: []
    };

    //查询模板关联的品牌下拉列表
    $scope.selectSpecList=function () {
        specificationService.selectSpecOptions().success(function (response) {
            $scope.specList.data=response;
        })
    }

    $scope.entity={customAttributeItems:[]};

    //新增扩展属性
    $scope.addRow=function () {
        $scope.entity.customAttributeItems.push({});
    }

	//删除扩展属性
    $scope.deleRow=function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    }

});	
