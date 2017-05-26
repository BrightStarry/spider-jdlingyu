package com.zx.Entity;

/**
 * 每个套图主要信息 实体类
 */
public class MainImage {
    private String url;//url
    private Integer views;//预览人数
    private String html;//html string


    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "MainImage{" +
                "url='" + url + '\'' +
                ", views=" + views +
                '}';
    }
}
