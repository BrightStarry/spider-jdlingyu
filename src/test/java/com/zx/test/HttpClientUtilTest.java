package com.zx.test;

import com.zx.util.HttpClientUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.junit.Test;

/**
 * httpClientUtil测试类
 */
public class HttpClientUtilTest {


    @Test
    public void test1() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

        CloseableHttpResponse response = httpClientUtil.sendGetRequestForResponse("https://www.baidu.com");
        System.out.println(httpClientUtil.responseToString(response));

        HttpClientContext httpClientContext = HttpClientContext.create();
    }
}
