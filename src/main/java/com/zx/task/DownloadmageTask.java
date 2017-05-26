package com.zx.task;

import com.zx.Entity.ImageDetail;
import com.zx.Main.Main;
import com.zx.queue.Queue;
import com.zx.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 下载图片任务
 */
public class DownloadmageTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadmageTask.class);
    //取出 实体类
    private Queue<ImageDetail> queue4;
    //存入实体类
    private Queue<ImageDetail> queue5;

    private ExecutorService executorService;

    public DownloadmageTask(Queue<ImageDetail> queue4, Queue<ImageDetail> queue5, ExecutorService executorService){
        this.queue4 = queue4;
        this.queue5 = queue5;
        this.executorService = executorService;
    }


    @Override
    public void run() {
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        ImageDetail imageDetail = null;
        try {
            imageDetail = queue4.take();

            LOGGER.info("开始获取输入流：" + imageDetail.toString());

            /**
             * 获取每个实体类所有图片url的输入流
             */
            List<CloseableHttpResponse> inputStreams = new ArrayList<>();
            for (String tempUrl : imageDetail.getImages()){
                CloseableHttpResponse response = httpClientUtil.sendGetRequestForResponse(tempUrl);
                if(null == response){
                    throw new Exception();
                }
                inputStreams.add(response);
            }
            //存回实体类
            imageDetail.setInputStreams(inputStreams);

            queue5.put(imageDetail);

            /**
             * 获取到每个输入流后，使用线程池，另起一个线程去IO
             */
            executorService.submit(new WriteToLocalTask(imageDetail));
        } catch (Exception e) {

            LOGGER.error("DownloadmageTask 获取输入流错误！" + e.getMessage());
            try {
                queue4.put(imageDetail);
                Main.imageCountCopy2.incrementAndGet();
            } catch (Exception e1) {
            }
        }

    }
}
