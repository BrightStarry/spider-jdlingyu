package com.zx.queue;

import com.zx.Entity.ImageDetail;
import com.zx.Entity.MainPage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 队列实现类
 */
public class QueueNo5Impl implements Queue<ImageDetail>{

    private final LinkedBlockingQueue<ImageDetail> linkedBlockingQueue = new LinkedBlockingQueue<>();


    /**
     * 在放入数据
     */
    @Override
    public  void put(ImageDetail imageDetail) throws Exception {
        linkedBlockingQueue.offer(imageDetail,10, TimeUnit.SECONDS);
    }

    /**
     * 从移除数据
     */
    @Override
    public ImageDetail take() throws Exception {
        return linkedBlockingQueue.poll(10,TimeUnit.SECONDS);
    }

    public int size(){
        return linkedBlockingQueue.size();
    }
}
