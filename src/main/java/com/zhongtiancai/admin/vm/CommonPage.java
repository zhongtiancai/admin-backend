package com.zhongtiancai.admin.vm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 分页数据封装类
 * Created by macro on 2019/4/19.
 */
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> of(List<T> list,Integer pageNum,Integer pageSize,Long total) {
        CommonPage<T> commonPage = new CommonPage<>();
        commonPage.setList( list);
        commonPage.setPageNum( pageNum);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(total);
        return commonPage;
    }

    public static <T> CommonPage<T> of(Page<T> page) {
        CommonPage<T> commonPage = new CommonPage<>();
        commonPage.setList(page.getContent());
        commonPage.setPageNum(page.getNumber());
        commonPage.setPageSize(page.getSize());
        commonPage.setTotal(page.getTotalElements());
        return commonPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }



    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
