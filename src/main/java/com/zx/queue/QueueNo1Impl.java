package com.zx.queue;

import com.zx.Entity.MainPage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 队列实现类
 */
public class QueueNo1Impl implements Queue<MainPage>{

    private final LinkedBlockingQueue<MainPage> linkedBlockingQueue = new LinkedBlockingQueue<>();


    /**
     * 在放入数据
     */
    @Override
    public  void put(MainPage mainPage) throws Exception {
        linkedBlockingQueue.offer(mainPage,10, TimeUnit.SECONDS);
    }

    /**
     * 从移除数据
     */
    @Override
    public MainPage take() throws Exception {
        return linkedBlockingQueue.poll(10,TimeUnit.SECONDS);
    }

    public int size(){
        return linkedBlockingQueue.size();
    }
}
