package com.zx.task;

import com.zx.Entity.MainImage;
import com.zx.Entity.MainPage;
import com.zx.queue.Queue;
import com.zx.util.ResolveUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解析 主页分页的html string 提取出 所有的套图url
 */
public class MainPageResolveTask implements  Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(MainPageResolveTask.class);
    //队列操作接口--取
    private Queue<MainPage> queue1;
    //队列操作接口--存
    private  Queue<MainImage> queue2;

    private  AtomicInteger imageCount;

    private CountDownLatch latch;


    public MainPageResolveTask(Queue<MainPage> queue1, Queue<MainImage> queue2, AtomicInteger imageCount, CountDownLatch latch){
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.imageCount = imageCount;
        this.latch = latch;
    }


    @Override
    public void run() {
        ResolveUtil resolveUtil = ResolveUtil.getInstance();

        /**
         * 解析页面，获取url 和 views(预览人数)
         */
        MainPage mainPage = null;
        try {
            mainPage  = queue1.take();
            if(null == mainPage){
                return;
            }
        } catch (Exception e) {
            LOGGER.error("MainPageResolveTask 从队列获取数据失败！");
        }


        try {
            String html = mainPage.getHtml();
            Element element = resolveUtil.getElement(html, "#postlist");
            //获取到个套图的div集合
            Elements elements = element.select("#postlist > div");
            for(Element tempElement : elements){
                MainImage mainImage = new MainImage();
                Element urlElement = tempElement.select(".pin > div > a").first();
                //把url 放入实体类
                mainImage.setUrl(urlElement.attr("href"));

                Element viewElement = tempElement.select(".pin > div > div > a.viewsButton > span:nth-child(2)").first();
                //获取到预览人数，但如果超过999，会有,  所以要删除,
                String[] strs = viewElement.html().split(" ");
                String views = strs[0].replace(",","");
                //放入实体类
                mainImage.setViews(Integer.valueOf(views));

                //入队
                queue2.put(mainImage);

                //统计+1
                imageCount.incrementAndGet();

            }

        } catch (Exception e) {
            LOGGER.error("MainPageResolveTask 解析失败！");
        }finally{
            //门闩-1
            latch.countDown();
        }



    }
}
