package com.zx.task;


import com.zx.Entity.ImageDetail;
import com.zx.Entity.MainImage;
import com.zx.queue.Queue;
import com.zx.util.ResolveUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 套图页面解析 任务
 */
public class MainImageResolveTask implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(MainImageResolveTask.class);
    //队列操作接口--取
    private Queue<MainImage> queue3;
    //队列操作接口--存
    private  Queue<ImageDetail> queue4;

    private CountDownLatch latch2;

    public MainImageResolveTask(Queue<MainImage> queue3, Queue<ImageDetail> queue4,CountDownLatch latch2){
        this.queue3 = queue3;
        this.queue4 = queue4;
        this.latch2 = latch2;
    }

    @Override
    public void run() {

        ResolveUtil resolveUtil = ResolveUtil.getInstance();
        ImageDetail imageDetail = new ImageDetail();
        try {
            MainImage mainImage = queue3.take();

            //设置预览人数
            imageDetail.setViews(mainImage.getViews());
            //设置url
            imageDetail.setUrl(mainImage.getUrl());
            //提取出id
            String[] split = mainImage.getUrl().split("/");
            imageDetail.setId(Integer.valueOf(split[3]));

            Element bodyElement = resolveUtil.getElement(mainImage.getHtml(), "#single > div.main > div > div.post.image");

            //设置标题
            Element titleElement = bodyElement.select("div.post.image > div.main-header > h2.main-title").first();
            imageDetail.setTitle(titleElement.html());
            //设置分类
            imageDetail.setCategory(bodyElement.select("div.post.image > div.main-header > div.main-meta > span.post-category > a").first().html());


            //设置图片
            Elements imageElements = bodyElement.select("div.post.image  > div.main-body > p > a");
            List<String> images = new ArrayList<>();
            for(Element tempElement : imageElements){
                images.add(tempElement.attr("href"));
            }
            imageDetail.setImages(images);


            //入队
            queue4.put(imageDetail);
            LOGGER.info("Task 4:" + "套图：" + imageDetail.getTitle());



        } catch (Exception e) {
            LOGGER.error("MainImageResolveTask 解析失败！");
        }finally {
            latch2.countDown();
        }


    }












}
