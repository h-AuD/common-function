package AuD.common.function.web;

import java.util.List;

/**
 * Description: 用于页面展示的table数据的结构,通常与{@link RulePageFilterModel}结合使用. <br>
 *
 * @author AuD/胡钊
 * @ClassName WebPageDataConfig
 * @date 2021/2/5 18:55
 * @Version 1.0
 */
public class PageDataModel {

    /** 总页数,需要计算获取,参见{@link PageDataModel#calcPageCount(int)} */
    private int totalPages;

    /** 数据总条数,必传的参数 */
    private int totalNum;

    /** 当前页数,必传的参数 */
    private int pageNo;

    /** 每页的数据量 */
    private int pageSize;

    private List<?> data;

    /**
     *
     * @param pageNo 当前页码
     * @param totalNum  总数
     * @param pageSize  每页容量
     * @param data  数据
     */
    public PageDataModel(int pageNo, int totalNum, int pageSize,List<?> data) {
        this.pageNo = pageNo;
        this.totalNum = totalNum;
        calcPageCount(totalNum);
        this.pageSize = pageSize;
        this.data = data;
    }


    /** 计算 totalPages */
    private void calcPageCount(int totalNum){
        int res = totalNum/pageSize;
        this.totalPages = totalNum%pageSize==0? res:++res;
    }

    /** 设置(填充)数据 */
    public <T> void data(List<T> data){
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
