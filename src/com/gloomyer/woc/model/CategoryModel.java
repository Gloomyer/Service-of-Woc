package com.gloomyer.woc.model;

import java.util.List;

/**
 * Created by Gloomy on 2016/10/27.
 */
public class CategoryModel {
    private int categoryId;
    private String categoryName;
    private List<ArticleModel> articles;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModel> articles) {
        this.articles = articles;
    }
}
