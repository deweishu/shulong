package cn.itcast.solr.test;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.solr.util.SolrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private SolrUtil solrUtil;

    @Test
    public void dataImportTest(){
        solrUtil.dataImport();
    }


    /**
     * 添加和修改索引库数据都是调用saveBean，当id存在时，执行修改操作，当id不存在时，执行添加
     */
    @Test
    public void addItem(){

        TbItem item = new TbItem();

        item.setId(2L);
        item.setTitle("华为荣耀20 移动3G 64G");
        item.setBrand("华为");
        item.setSeller("华为旗舰店1");

        //保存数据到索引库
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    /**
     * 基于id查询
     */
    @Test
    public void queryById(){

        TbItem item = solrTemplate.getById(1L, TbItem.class);

        System.out.println(item.getId()+"   "+item.getBrand()+"   "+item.getTitle()+"   "+item.getSeller());

    }

    /**
     * 删除
     */
    @Test
    public void delete(){

        solrTemplate.deleteById("1");
        solrTemplate.commit();

    }

    /**
     * 删除所有
     */
    @Test
    public void deleteAll(){

        SolrDataQuery query= new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();

    }

    @Test
    public void addBatchItem(){
        List<TbItem> itemList = new ArrayList<>();

        for(long i=1;i<=100;i++){
            TbItem item = new TbItem();
            item.setId(i);
            item.setTitle(i+"华为荣耀20 移动3G 64G");
            item.setBrand("华为");
            item.setSeller("华为"+i+"号旗舰店");
            itemList.add(item);

        }
        //保存数据到索引库
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
    }

    /**
     * 分页查询
     */
    @Test
    public void pageQueryTest(){
        Query query = new SimpleQuery("*:*");

        //设置分页查询条件
        query.setOffset(2);//查询起始值
        query.setRows(3);//每页查询记录数据

        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        List<TbItem> itemList = page.getContent();

       System.out.println("满足条件的总页数"+page.getTotalPages());
       System.out.println("满足条件的总记录"+page.getTotalElements());

        for (TbItem item : itemList) {
            System.out.println(item.getId()+"   "+item.getBrand()+"   "+item.getTitle()+"   "+item.getSeller());
        }


    }

    /**
     * 条件查询  附带高亮处理
     */
    @Test
    public void mutilQueryTest(){
        //高亮查询对象
        HighlightQuery query = new SimpleHighlightQuery();
        //封装查询条件对象
        //Criteria criteria = new Criteria("item_title").contains("5").and("item_seller").contains("9");
        Criteria criteria = new Criteria("item_title").contains("华为");
        query.addCriteria(criteria);

        //设置高亮
        //高亮条件对象
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");//添加高亮显示字段
        highlightOptions.setSimplePrefix("<font color='red'>");//高亮前缀
        highlightOptions.setSimplePostfix("</font>");//高亮前缀

        query.setHighlightOptions(highlightOptions);

        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);

        List<TbItem> itemList = page.getContent();
        for (TbItem item : itemList) {
            //处理高亮结果
            List<HighlightEntry.Highlight> highlights = page.getHighlights(item);

            if(highlights!=null && highlights.size()>0){
                //高亮实体对象
                HighlightEntry.Highlight highlight = highlights.get(0);

                List<String> snipplets = highlight.getSnipplets();
                //替换高亮内容
                if(snipplets!=null && snipplets.size()>0){
                    item.setTitle(snipplets.get(0));
                }

            }

            System.out.println(item.getId()+"   "+item.getBrand()+"   "+item.getTitle()+"   "+item.getSeller());
        }


    }


}
