package com.revauc.revolutionbuy.network.request.auth;

/*
Copyright © 2017 Block Partee. All rights reserved.
Developed by Appster.
*/

public class ProductSearchRequest {

    private int page;
    private int limit;
    private String keyword;
    private String catId;

    public ProductSearchRequest(int page, int limit, String keyword, String catId) {
        this.page = page;
        this.limit = limit;
        this.keyword = keyword;
        this.catId = catId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}
