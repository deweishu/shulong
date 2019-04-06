package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页的pojo
 * @param <T>
 */
public class PageResult<T> implements Serializable {

    private Long total;  //总数量
    private List<T> rows; //一页显示的列表

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
