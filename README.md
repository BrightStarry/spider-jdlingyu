#### 爬虫 ---http://www.jdlingyu.moe/
简单的说下。

1. 可以构建HttpClient连接池，取出来的连接无论访问http还是https都是可以的，并且据说性能好。
具体看util包下的HttpClientUtil类
2. 所有的response必须关闭，记住，任何情况下。否则该httpClient会被阻塞。
3. 连接池可以配置最大连接数量和路由数量。路由数量是指，如果你访问www.baidu.com，如果路由设置成20，就最多只能有20个连接并发。
即使最大连接数量设置成200，也只能有20个连接生效。
4. ！！！！！HttpResponse获取到的InputStream无法复用，也就是被读取（输出)一次后，就不能再输出第二次了。
可以使用BuffferedInputStream包装，或者将InputStream转为ByteArrayInputStream，然后输出到ByteArrayOutputStream
中，然后复用这个输出对象就可以了。