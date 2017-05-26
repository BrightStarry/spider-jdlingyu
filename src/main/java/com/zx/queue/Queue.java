package com.zx.queue;

import com.zx.Entity.MainPage;

/**
 * 队列接口
 * 使用该接口是为了方便替换队列实现。
 * 比如我目前只打算用Concurrent包下的并发队列储存，但如果后面想换成redis之类的也行
 */
public interface Queue<T> {
    /**
     * 将html string 加到NO.1队列尾部
     */
    void put(T obj) throws Exception;

    /**
     * 从No.1队列移除一条数据
     */
    T take() throws Exception;

    int size();
}
