package com.zx.task;

import com.zx.Entity.MainPage;
import com.zx.queue.Queue;
import com.zx.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 任务类--第一阶段--NO.1
 * 爬取主页的每个分页的任务类
 * http://www.jdlingyu.moe/page/x
 * 使用future，实现Callable接口，
 * 直接获取到页面的string，丢到队列中去
 */
public class MainPageTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainPageTask.class);

    //队列操作接口
    private Queue<MainPage> queue;

    //爬取的url,固定格式，再在最后加个页码即可
    private static final String URL = "http://www.jdlingyu.moe/page/";
    //页码
    private Integer page;

    public MainPageTask(Queue<MainPage> queue, Integer page) {
        this.queue = queue;
        this.page = page;
    }


    /**
     * 执行线程，获取每个套图的URL
     */
    @Override
    public void run() {

        /**
         * 获取到网站的html string
         */
        try {
            String url = URL + page;
            LOGGER.info("Task 1 ：" + "正在爬取的网页：" + URL + page);
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            String html = httpClientUtil.sendGetRequestForHtml(url);
            if(StringUtils.isEmpty(html)){
                throw new Exception("html string 为空");
            }

            /**
             * 组装成对象，丢到队列里去
             */
            MainPage mainPage = new MainPage(page,html,url);
            queue.put(mainPage);
        } catch (Exception e) {
            LOGGER.error("MainPageTask 访问html 失败！");
        }


    }
}
