package com.zx.queue;

import com.zx.Entity.ImageDetail;
import com.zx.Entity.MainImage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 队列2
 * 保存每个套图的实体信息
 */
public class QueueNo4Impl implements Queue<ImageDetail>{

    private final LinkedBlockingQueue<ImageDetail> linkedBlockingQueue = new LinkedBlockingQueue<>();

    @Override
    public void put(ImageDetail imageDetail) throws Exception {
        linkedBlockingQueue.offer(imageDetail,10, TimeUnit.SECONDS);
    }

    @Override
    public ImageDetail take() throws Exception {
        return linkedBlockingQueue.poll(10,TimeUnit.SECONDS);
    }

    public int size(){
        return linkedBlockingQueue.size();
    }
}
