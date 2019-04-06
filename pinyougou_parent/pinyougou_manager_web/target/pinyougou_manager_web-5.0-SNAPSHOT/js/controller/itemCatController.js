 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
            $scope.entity.parentId=$scope.parentId;
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.findByParentId($scope.parentId);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
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
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	//定义父id，为分类录入准备
	$scope.parentId=0;

	//查询下级分类列表
	$scope.findByParentId=function (parentId) {
        $scope.parentId=parentId;
        itemCatService.findByParentId(parentId).success(function (response) {
            $scope.list=response;
        })
    }

    //初始化分类
	$scope.grade=1;

	//设置分类级别操作
	$scope.setGrade=function (grade) {
        $scope.grade=grade;
    }

    //面包屑导航栏功能实现 {id:0}
	$scope.selectItemCatList=function (p_entity) {
		//一级分类
		if($scope.grade==1){
			$scope.entity_2=null;//二级分类实体对象
			$scope.entity_3=null;//三级分类实体对象
		}

        //二级分类
        if($scope.grade==2){
            $scope.entity_2=p_entity;//二级分类实体对象
            $scope.entity_3=null;//三级分类实体对象
        }

        //三级分类
        if($scope.grade==3){
            $scope.entity_3=p_entity;//三级分类实体对象
        }

        //查询下级分类
        $scope.findByParentId(p_entity.id);
		
    }

    //查询模板下拉列表
	$scope.selectTypeList=function () {
		typeTemplateService.findAll().success(function (response) {
			$scope.typeList=response;
        })
    }

});	
