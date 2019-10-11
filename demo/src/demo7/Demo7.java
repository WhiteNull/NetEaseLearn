package demo7;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo7 {


    /**
     * 1.线程池信息：核心线程数5，最大数量10，无界队列，超过核心线程数数量的线程存活时间：5秒，指定拒绝策略
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest1() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());

    }
}