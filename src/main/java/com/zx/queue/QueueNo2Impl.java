package com.zx.queue;

import com.zx.Entity.MainImage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 队列2
 * 保存每个套图的实体信息
 */
public class QueueNo2Impl implements Queue<MainImage>{

    private final LinkedBlockingQueue<MainImage> linkedBlockingQueue = new LinkedBlockingQueue<>();

    @Override
    public void put(MainImage mainImage) throws Exception {
        linkedBlockingQueue.offer(mainImage,10, TimeUnit.SECONDS);
    }

    @Override
    public MainImage take() throws Exception {
        return linkedBlockingQueue.poll(10,TimeUnit.SECONDS);
    }

    public int size(){
        return linkedBlockingQueue.size();
    }
}
