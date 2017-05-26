package com.zx.Entity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.InputStream;
import java.util.List;

/**
 * 套图详情实体类
 */
public class ImageDetail {
    private String title;
    private String url;
    private Integer id;
    private String category;
    private Integer views;
    private List<String> images;//图片url
    private List<CloseableHttpResponse> inputStreams;//输入流集合

    //百度云的帐号密码解压密码，不一定有
    private String baiDuYunUrl;
    private String baiDuYunPWD;
    private String zipPWD;

    public List<CloseableHttpResponse> getInputStreams() {
        return inputStreams;
    }

    public void setInputStreams(List<CloseableHttpResponse> inputStreams) {
        this.inputStreams = inputStreams;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getBaiDuYunUrl() {
        return baiDuYunUrl;
    }

    public void setBaiDuYunUrl(String baiDuYunUrl) {
        this.baiDuYunUrl = baiDuYunUrl;
    }

    public String getBaiDuYunPWD() {
        return baiDuYunPWD;
    }

    public void setBaiDuYunPWD(String baiDuYunPWD) {
        this.baiDuYunPWD = baiDuYunPWD;
    }

    public String getZipPWD() {
        return zipPWD;
    }

    public void setZipPWD(String zipPWD) {
        this.zipPWD = zipPWD;
    }

    @Override
    public String toString() {
        return "ImageDetail{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                ", category='" + category + '\'' +
                ", views=" + views +
                ", images=" + images +
                ", inputStreams=" + inputStreams +
                ", baiDuYunUrl='" + baiDuYunUrl + '\'' +
                ", baiDuYunPWD='" + baiDuYunPWD + '\'' +
                ", zipPWD='" + zipPWD + '\'' +
                '}';
    }
}
