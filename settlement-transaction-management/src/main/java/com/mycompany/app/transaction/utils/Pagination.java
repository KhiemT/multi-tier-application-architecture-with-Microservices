package com.mycompany.app.transaction.utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T>{

    public List<T> content;
    public int page;
    public long totalResult;
    public long totalPages;

    public List<T> getContent() {
        if(content==null){
            content = new ArrayList<T>();
        }
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(long totalResult) {
        this.totalResult = totalResult;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
