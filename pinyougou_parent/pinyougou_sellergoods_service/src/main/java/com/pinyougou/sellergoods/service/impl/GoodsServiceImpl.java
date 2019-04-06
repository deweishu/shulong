package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;
import entity.PageResult;
import groupEntity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbBrandMapper brandMapper;

	@Autowired
	private TbSellerMapper sellerMapper;

	@Autowired
	private TbItemMapper itemMapper;

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		//保存tb_goods表的数据
		TbGoods tbGoods = goods.getGoods();
		//录入的商品是未审核状态
		tbGoods.setAuditStatus("0");
		goodsMapper.insert(tbGoods);

		//保存tb_goods_desc数据
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		goodsDesc.setGoodsId(tbGoods.getId());
		goodsDescMapper.insert(goodsDesc);
		//判断是否启用规格
		if("1".equals(tbGoods.getIsEnableSpec())){//启用规格
			//保存tb_item表中的数据
			List<TbItem> itemList = goods.getItemList();
			for (TbItem item : itemList) {
			/*
			*  `title` varchar(100) NOT NULL COMMENT '商品标题',  //商品名称 + 商品规格选项名称 中间以空格隔开
			 `image` varchar(2000) DEFAULT NULL COMMENT '商品图片',  //商品搜索结果中，需要展示的图片  取图片列表第一张
			`categoryId` bigint(10) NOT NULL COMMENT '所属类目，叶子类目',  //三级分类id
			 `create_time` datetime NOT NULL COMMENT '创建时间',
			  `update_time` datetime NOT NULL COMMENT '更新时间',
			  `goods_id` bigint(20) DEFAULT NULL,
			  `seller_id` varchar(30) DEFAULT NULL,
			  //以下三个字段与搜索相关
			  `category` varchar(200) DEFAULT NULL,
			  `brand` varchar(100) DEFAULT NULL,
			  `seller` varchar(200) DEFAULT NULL,*/
				String title=tbGoods.getGoodsName();
				//title中规格名称从spec对象中获取{"机身内存":"16G","网络":"联通3G"}
				Map<String,String> specMap = JSON.parseObject(item.getSpec(), Map.class);
				for(String key : specMap.keySet()){
					title+=" "+specMap.get(key);
				}
				//组装item数据
				item.setTitle(title);
				setItemValue(tbGoods, goodsDesc, item);


				itemMapper.insert(item);
			}
		}else{//未启用规格
			//需要组装item数据
			TbItem item = new TbItem();
			item.setTitle(tbGoods.getGoodsName());
			/**
			 * 	页面传递参数
			 *  `spec` varchar(200) DEFAULT NULL,
			 `price` decimal(20,2) NOT NULL COMMENT '商品价格，单位为：元',
			 `num` int(10) NOT NULL COMMENT '库存数量',
			 `status` varchar(1) NOT NULL COMMENT '商品状态，1-正常，2-下架，3-删除',
			 `is_default` varchar(1) DEFAULT NULL,
			 */
			item.setPrice(tbGoods.getPrice());
			item.setNum(99999);
			item.setStatus("1");
			item.setIsDefault("1");
			item.setSpec("{}");
			setItemValue(tbGoods,goodsDesc,item);
			itemMapper.insert(item);

		}

	}

	//组装item数据
	private void setItemValue(TbGoods tbGoods, TbGoodsDesc goodsDesc, TbItem item) {
		//商品图片 [{"color":"灰色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVurS8GAQRRHAACuI4TeyLI219.jpg"}]
		String itemImages = goodsDesc.getItemImages();
		List<Map> imageList = JSON.parseArray(itemImages, Map.class);
		if(imageList.size()>0){
            String image= (String) imageList.get(0).get("url");
            item.setImage(image);
        }

		item.setCategoryid(tbGoods.getCategory3Id());

		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());

		item.setGoodsId(tbGoods.getId());
		item.setSellerId(tbGoods.getSellerId());
		//分类名称封装 三级分类
		TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
		item.setCategory(tbItemCat.getName());

		TbBrand brand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
		item.setBrand(brand.getName());
		//商家店铺名称
		TbSeller tbSeller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
		item.setSeller(tbSeller.getNickName());
	}


	/**
	 * 修改
	 */
	@Override
	public void update(TbGoods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoods findOne(Long id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//更新is_delete字段，默认为null时，未删除商品，删除时，需要将is_delete设置为1
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();

		//无论有没有输入条件查询数据，都要查询未删除状态的商品
			criteria.andIsDeleteIsNull();
		if(goods!=null){
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				//criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusEqualTo(goods.getAuditStatus());
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids,String status) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination addItemSolrDestination;

	@Autowired
	private Destination deleItemSolrDestination;

	@Autowired
	private Destination addItemPageDestination;

	@Autowired
	private Destination deleItemPageDestination;

	@Override
	public void updateIsMarketable(Long[] ids, String isMarketable) {
		for (Long id : ids) {
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			//注意：审核通过的商品才能上下架
			if("1".equals(tbGoods.getAuditStatus())){
				//上架
				if("1".equals(isMarketable)){
					//将上架商品同步索引库
					jmsTemplate.send(addItemSolrDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(id+"");
						}
					});

					//将上架商品生成静态页
					jmsTemplate.send(addItemPageDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(id+"");
						}
					});
				}

				//下架
				if("0".equals(isMarketable)){
					//商品下架时，同步删除下架索引库商品数据
					jmsTemplate.send(deleItemSolrDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(id+"");
						}
					});

					//商品下架时，同步删除商品静态页
					jmsTemplate.send(deleItemPageDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(id+"");
						}
					});
				}

				tbGoods.setIsMarketable(isMarketable);
				goodsMapper.updateByPrimaryKey(tbGoods);
			}else{
				throw new RuntimeException("只有审核通过的商品才能上下架");
			}
		}
	}

}
