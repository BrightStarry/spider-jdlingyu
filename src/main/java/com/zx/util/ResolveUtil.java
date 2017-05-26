package com.zx.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 页面解析工具类
 * 单例
 */
public class ResolveUtil {

    /**
     * 抽取html中单一的某个元素
     * 元素中的第一个
     */
    public  Element getElement(String html, String selector){
        Elements elements = getElementArray(html, selector);
        Element element = null;
        if(elements != null){
            element  = elements.first();
        }
        return element;
    }

    /**
     * 抽取html中的元素集合
     */
    public Elements getElementArray(String html, String selector){
        final Document document = stringToDocument(html);
        Elements elements = document.select(selector);
        return elements;
    }

    /**
     * 将string 转为 document对象
     */
    public Document stringToDocument(String html){
        return Jsoup.parse(html);
    }


    /**
     * 静态内部类 实现单例模式
     */
    static class ResolveUtilSingleton{
        public static final ResolveUtil resolveUtil = new ResolveUtil();
    }
    private ResolveUtil(){
    }
    public static ResolveUtil getInstance(){
        return ResolveUtilSingleton.resolveUtil;
    }

}
