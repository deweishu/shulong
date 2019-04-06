 //控制层 
app.controller('goodsController' ,function($scope,$controller ,goodsService,itemCatService,typeTemplateService,uploadService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
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

    //添加商品操作
    $scope.add=function(){

        $scope.entity.goodsDesc.introduction=editor.html();
        goodsService.add( $scope.entity  ).success(
            function(response){
                if(response.success){
                    //添加成功，清空输入中的内容
                    $scope.entity={};
                    editor.html("");//清空富文本编辑器中的内容
                }else{
                    alert(response.message);
                }
            }
        );
    }
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
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
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//查询一级分类的方法
	$scope.findCat1List=function () {
		itemCatService.findByParentId(0).success(function (response) {
			$scope.itemCat1List=response;
        });
    }

    //基于选择的一级分类，查询该分类下的二级分类
    //参数一：监控的模型数据  参数二：发生变化后，执行的操作
    $scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List=response;
        });
    });

    //基于选择的二级分类，查询该分类下的三级分类
    //参数一：监控的模型数据  参数二：发生变化后，执行的操作
    $scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat3List=response;
        });
    });

    //基于选择的三级分类，查询该分类对应的模板数据
    //参数一：监控的模型数据  参数二：发生变化后，执行的操作
    $scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
    	//注意：三级分类与模板是一对一业务关系，所以基于分类id查询分类数中的模板id
        itemCatService.findOne(newValue).success(function (response) {
           $scope.entity.goods.typeTemplateId=response.typeId;
        });
    });

    //基于模板id，查询该模板数据
    //参数一：监控的模型数据  参数二：发生变化后，执行的操作
    $scope.$watch("entity.goods.typeTemplateId",function (newValue,oldValue) {
       	typeTemplateService.findOne(newValue).success(function (response) {
       		//处理json格式品牌数据
			$scope.brandList=JSON.parse(response.brandIds);
			//处理扩展属性  [{"text":"内存大小"},{"text":"颜色"}]
           $scope.entity.goodsDesc.customAttributeItems= JSON.parse(response.customAttributeItems);
        });
       	//查询模板关联的规格列表
        typeTemplateService.findSpecList(newValue).success(function (response) {
			$scope.specList=response;
        })

    });
	//初始化图片对象
    $scope.imageEntity={};
    //图片上传
	$scope.upload=function () {
		uploadService.upload().success(function (response) {
			if(response.success){
				//接收后端返回的图片请求地址
				$scope.imageEntity.url=response.message;

                //js清空上传文件操作
                var file = document.getElementById("file");
                // for IE, Opera, Safari, Chrome
                if (file.outerHTML) {
                    file.outerHTML = file.outerHTML;
                } else { // FF(包括3.5)
                    file.value = "";
                }
			}else{
				alert(response.message);
			}
        })
    }

    //初始化entity实体对象
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]},itemList:[]};
    
    //图片添加图片列表操作
	$scope.addImage=function () {
		$scope.entity.goodsDesc.itemImages.push($scope.imageEntity);
    }

    //图片从图片列表删除操作
    $scope.deleImage=function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //规格选项勾选和取消勾选操作
	$scope.updateAttribute=function ($event,specName,specOption) {
/*
	1、基于规格名称从规格列表中判断该规格是否存在
		1.1 存在
			判断规格选项是勾选还是取消勾选
				1.1.1勾选规格选项
					在已存在的规格选项列表中添加勾选的规格选项数据

				1.1.2取消勾选规格选项
					判断是否全部取消了规格选项数据
						1.1.2.1
							如果不是全部取消
								在已存在的规格选项列表中移除勾选的规格选项数据

						1.1.2.2
							如果全部取消勾选选项数据
								从规格列表中移除该规格对象

		1.2 不存在
			在规格列表中新增规格对象
				{"attributeName":"机身内存","attributeValue":["16G"]}
	*/

       var specObject= $scope.getObjectByValue($scope.entity.goodsDesc.specificationItems,"attributeName",specName);

       if(specObject!=null){
       		//存在
		   //判断规格选项是勾选还是取消勾选
		   if($event.target.checked){
		   		//勾选规格选项
               specObject.attributeValue.push(specOption);
		   }else {
		   		//取消勾选规格选项
               //判断是否全部取消了规格选项数据
			   var index=specObject.attributeValue.indexOf(specOption);
               specObject.attributeValue.splice(index,1);

               if(specObject.attributeValue.length<=0){
               	//全部取消了规格选项数据
				   var index1=$scope.entity.goodsDesc.specificationItems.indexOf(specObject);
                   $scope.entity.goodsDesc.specificationItems.splice(index1,1);
			   }
		   }
	   }else {
       		//不存在  在规格列表中新增规格对象
           $scope.entity.goodsDesc.specificationItems.push({"attributeName":specName,"attributeValue":[specOption]});
	   }

    }

    //sku列表生成
	$scope.createItemList=function () {
		//[{"attributeName":"网络","attributeValue":["移动3G","移动4G"]}]  勾选的规格集合
       var specs= $scope.entity.goodsDesc.specificationItems;
		//初始化需要组装的sku列表
		$scope.entity.itemList=[{spec:{},price:0,num:99999,status:1,isDefault:0}];

		if(specs.length<=0){
            $scope.entity.itemList=[];
		}

		for(var i=0;i<specs.length;i++){
			//specs[i].attributeName="网络"   specs[i].attributeValue=["移动3G","移动4G"]
            $scope.entity.itemList= addColumn($scope.entity.itemList,specs[i].attributeName,specs[i].attributeValue);
		}
    }

    addColumn=function (list,specName,specOptions) {
		var newList=[];
		for(var i=0;i<list.length;i++){
			//{spec:{},price:0,num:99999,status:1,isDefault:0}
			var oldItem=list[i];

			//获取勾选的规格选择值
			//组装item对象中的spec对象  spec={"机身内存":"16G","网络":"联通3G"}
			for(var j=0;j<specOptions.length;j++){
				//js深克隆代码
				//{spec:{},price:0,num:99999,status:1,isDefault:0}
				var newItem =JSON.parse(JSON.stringify(oldItem));
				//spec:{"网络":"联通3G"}
                newItem.spec[specName]=specOptions[j];

                newList.push(newItem);
			}

		}

		return newList;

    }

    //商品状态处理
	$scope.status=['未审核','已审核','审核未通过','关闭'];

    $scope.catgoryList=[];

	//查询需要展示的商品分类列表
	$scope.findCatgeoryList=function () {
        itemCatService.findAll().success(function (response) {
			for(var i=0;i<response.length;i++){
				//catgoryList=["","图书、音像、电子书刊","电子书刊"];
                $scope.catgoryList[response[i].id]=response[i].name;
			}
        })
    }

    //商品上下架状态
    $scope.isMarketable=['下架','上架'];

    //商品上下架操作
    $scope.updateIsMarketable=function (isMarketable) {
        goodsService.updateIsMarketable($scope.selectIds,isMarketable).success(function (response) {
            if(response.success){
                $scope.reloadList();//刷新列表
                $scope.selectIds=[];//清空选择保存id的数组
            }else{
                alert(response.message);
            }
        });
    }
	
});	
