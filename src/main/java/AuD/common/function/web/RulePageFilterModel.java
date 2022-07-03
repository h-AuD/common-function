package AuD.common.function.web;

import java.util.LinkedHashMap;

/**
 * @Description: 通用分页查询(可以附带查询条件),主要应用于mybatis中SQL查询条件.
 *
 * @@author AuD/胡钊
 * @date 2021/2/24 18:22
 */
public class RulePageFilterModel {

    /** 默认分页大小(10),即每页数据量 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /** 当前页数 */
    private int pageNo;

    /** 分页SQL中limit起始值 */
    private int startNum;

    /** 分页SQL中size */
    private int pageSize;

    /**
     * 排序规则
     * 1.使用LinkedHashMap保证迭代顺序与put顺序一致
     * KEY -- table_field_name
     * VALUE -- orderStrategy(ase or desc)
     */
    private LinkedHashMap<String,OrderStrategyEnum> orderStrategy = new LinkedHashMap<>(0);


    /**
     * 计算分页SQL中的起始值和偏移量,即 limit ?,?
     */
    public void calcStartAndEndNum() {
        if(pageSize<=0){
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNo <=0) {
            this.pageNo = 1;
        } else {
            this.startNum = (pageNo - 1) * pageSize;
        }
    }


    /**
     *
     */
    public enum OrderStrategyEnum {

        ASC("ASE"),DESC("DESC")
        ;
        private String orderStrategy;

        OrderStrategyEnum(String orderStrategy){
            this.orderStrategy = orderStrategy.toUpperCase();
        }

        public String getOrderStrategy() {
            return orderStrategy;
        }

        public void setOrderStrategy(String orderStrategy) {
            this.orderStrategy = orderStrategy.toUpperCase();
        }
    }


    /* ====================== getter & setter ======================= */
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

}
