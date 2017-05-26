package com.zx.Main;

import com.zx.Entity.ImageDetail;
import com.zx.Entity.MainImage;
import com.zx.Entity.MainPage;
import com.zx.queue.*;
import com.zx.task.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主函数
 */
public class Main {
    public static Queue<MainPage> queue1 = new QueueNo1Impl();
    public static Queue<MainImage> queue2 = new QueueNo2Impl();
    public static Queue<MainImage> queue3 = new QueueNo3Impl();
    public static Queue<ImageDetail> queue4 = new QueueNo4Impl();
    public static Queue<ImageDetail> queue5 = new QueueNo5Impl();

    //套图总数1
    public static AtomicInteger imageCount = new AtomicInteger(0);
    //套图总数2
    public static AtomicInteger imageCountCopy = null;
    public static AtomicInteger imageCountCopy2 = null;

    //门闩，等待thread2的250个线程都执行完毕
    public static CountDownLatch latch = new CountDownLatch(250);
    //门闩，等待2500个套图都解析完成
    public static CountDownLatch latch2  = null;

    public static void main(String[] args) throws InterruptedException {



        //爬取 每个分页的 html
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService service = Executors.newFixedThreadPool(8);

                for (int i = 1; i <= 250; i++) {
                    service.submit(new MainPageTask(queue1, i));
                }
            }
        });

        //解析 上面每个html
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService service = Executors.newFixedThreadPool(4);
                for (int i = 1; i <= 250; i++) {
                    service.submit(new MainPageResolveTask(queue1, queue2, imageCount, latch));
                }
            }
        });

        //获取每个套图详情页面的html
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService service = Executors.newFixedThreadPool(8);
                while(imageCount.getAndDecrement() > 0){
                    service.submit(new MainImageTask(queue2, queue3));
                }
            }
        });

        //解析出每个套图的详细信息
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService service = Executors.newFixedThreadPool(4);
                while(imageCountCopy.getAndDecrement() >0){
                    service.submit(new MainImageResolveTask(queue3, queue4,latch2));
                }
            }
        });



        //爬取每个套图中每个图片的输入流
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService service = Executors.newFixedThreadPool(4);
                ExecutorService ioService = Executors.newFixedThreadPool(4);
                while(imageCountCopy2.getAndDecrement() >0){
                    service.submit(new DownloadmageTask(queue4, queue5, ioService));
                }
            }
        });

        thread1.start();
        thread2.start();
        //等待thread2所有线程，执行完毕
        latch.await();
        imageCountCopy = new AtomicInteger(imageCount.get());
        imageCountCopy2 = new AtomicInteger(imageCount.get());
        latch2 = new CountDownLatch(imageCount.get());
        thread3.start();
        thread4.start();
        latch2.await();
        System.out.println("-------------------------------------------------------------------------------------");
        thread5.start();



    }
}
