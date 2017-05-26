package com.zx.Entity;

/**
 * 主分页的每一页 实体类
 */
public class MainPage {
    //页码
    private Integer page;
    //页面string
    private String html;
    //url
    private String url;

    public MainPage() {
    }

    public MainPage(Integer page, String html, String url) {
        this.page = page;
        this.html = html;
        this.url = url;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

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

    @Override
    public String toString() {
        return "MainPage{" +
                "page=" + page +
                ", html='" + html + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
