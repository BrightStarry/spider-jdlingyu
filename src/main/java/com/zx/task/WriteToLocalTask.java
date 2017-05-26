package com.zx.task;

import com.zx.Entity.ImageDetail;
import com.zx.util.HttpClientUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 将输入流写入本地
 */
public class WriteToLocalTask implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteToLocalTask.class);

    //根路径
    public static final String ROOT_PATH = "F:" +File.separator + "spider" + File.separator;

    //实体类
    private ImageDetail imageDetail;

    public WriteToLocalTask(ImageDetail imageDetail){
        this.imageDetail = imageDetail;
    }



    @Override
    public void run() {
        try {
            writer(imageDetail.getInputStreams(),imageDetail.getId(),imageDetail.getTitle());
            LOGGER.info("写入文件到本地成功！---id:" + imageDetail.getId());
        } catch (IOException e) {
            LOGGER.error("WriteToLocalTask 写入异常！");
        }

    }

    public void writer(List<CloseableHttpResponse> inputStreams, int id, String title) throws IOException {
        HttpClientUtil httpClientUtil = null;
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            httpClientUtil = HttpClientUtil.getInstance();
            int size = inputStreams.size();

            for (int i = 0; i < size; i++) {
                File file = new File(ROOT_PATH + id + "-" + title + File.separator + (i + 1) + ".jpg");
                response = inputStreams.get(i);
                inputStream = response.getEntity().getContent();
                FileUtils.copyToFile(inputStream,file);

                //关闭输入流
                httpClientUtil.closeResponseAndIn(inputStream,response);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            httpClientUtil.closeResponseAndIn(inputStream,response);
        }
    }
}
