package com.pxene.pap.domain.model.custom;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;

public class PaginationResult
{
    @JsonProperty("items")
    private List<?> items;
    
    @JsonIgnore
    private Page<?> page;
    
    @JsonProperty("pager")
    private Pager pager;
    
    
    public List<?> getItems()
    {
        return items;
    }
    public void setItems(List<?> items)
    {
        this.items = items;
    }
    public Page<?> getPage()
    {
        return page;
    }
    public void setPage(Page<?> page)
    {
        this.page = page;
    }
    
    
    public PaginationResult(List<?> items, Page<?> page)
    {
        super();
        this.items = items;
        this.page = page;
        
        if (page != null)
        {
            this.pager = new Pager(page.getPageNum(), page.getPageSize(), page.getTotal());
        }
    }
    
    
    @Override
    public String toString()
    {
        return "PaginationResult [items=" + items + ", pager=" + pager + "]";
    }
    
    public class Pager
    {
        private int page;
        private int pageSize;
        private long totcal;
        
        
        public int getPage()
        {
            return page;
        }
        public void setPage(int page)
        {
            this.page = page;
        }
        public int getPageSize()
        {
            return pageSize;
        }
        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }
        public long getTotcal()
        {
            return totcal;
        }
        public void setTotcal(long totcal)
        {
            this.totcal = totcal;
        }
        
        
        public Pager(int page, int pageSize, long totcal)
        {
            super();
            this.page = page;
            this.pageSize = pageSize;
            this.totcal = totcal;
        }
        
        
        @Override
        public String toString()
        {
            return "Pager [page=" + page + ", pageSize=" + pageSize + ", totcal=" + totcal + "]";
        }
    }
}
