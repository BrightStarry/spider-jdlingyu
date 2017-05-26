package com.zx.task;

import com.zx.Entity.MainImage;
import com.zx.Main.Main;
import com.zx.queue.Queue;
import com.zx.util.HttpClientUtil;
import com.zx.util.ResolveUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 套图详情 任务类
 * url ：http://www.jdlingyu.moe/xxxxx/
 */
public class MainImageTask implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(MainImageTask.class);

    //队列操作接口--取
    private Queue<MainImage> queue2;
    //队列操作接口--存
    private  Queue<MainImage> queue3;

    public MainImageTask(Queue<MainImage> queue2,Queue<MainImage> queue3) {
        this.queue2 = queue2;
        this.queue3 = queue3;
    }

    @Override
    public void run() {

        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        ResolveUtil resolveUtil = ResolveUtil.getInstance();
        MainImage mainImage = null;
        try {
            //从quque2取出mainImage，访问url获取html
            mainImage = queue2.take();

            LOGGER.info("Task 3:" + "正在爬取的网站：" + mainImage.getUrl());
            String html = httpClientUtil.sendGetRequestForHtml(mainImage.getUrl());
            if(StringUtils.isEmpty(html)){
                throw new Exception("html string 为空");
            }
            mainImage.setHtml(html);

            //将html放回mainImage,再将该实体放入queue3
            queue3.put(mainImage);

        } catch (Exception e) {
            LOGGER.error("MainImageTask 获取html失败");
            try {
                queue2.put(mainImage);
                Main.imageCount.incrementAndGet();
            } catch (Exception e1) {
            }
        }

    }
}
