package demo7;

import java.util.concurrent.*;
import java.util.List;

public class Demo7 {

    /**
     * 1.线程池信息：核心线程数5，最大数量10，无界队列，超过核心线程数数量的线程存活时间：5秒，指定拒绝策略
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest1() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        testCommon(threadPoolExecutor);
    }

    /**
     * 2.线程池信息：核心线程数5，最大数量10，队列大小3，超过核心线程数数量的线程存活时间：5秒，指定拒绝策略
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest2() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("有任务被拒绝执行了");
            }
        });
        testCommon(threadPoolExecutor);

    }

    /**
     * 3.线程池信息：核心线程数5，最大数量5，无界队列，超过核心线程数数量的线程存活时间：5秒
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest3() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        testCommon(threadPoolExecutor);
    }

    /**
     * 4.线程池信息：核心线程数0，最大数量Integer.Max_VALUE,SynchronousQueue队列，超过核心线程数数量的线程存活时间：60秒
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest4() throws Exception {
        //SynchronousQueue队列，会新建线程，这些线程可复用，在线程存活时间结束后销毁。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        testCommon(threadPoolExecutor);
        Thread.sleep(60000L);
        System.out.println("60秒后，再看线程池中的数量：" + threadPoolExecutor.getPoolSize());
    }

    /**
     * 5.线程池信息：核心线程数5，最大数量Integer.Max_VALUE,DelayedWorkQueue延时队列，超过核心线程数数量的线程存活时间：0秒
     * 定时执行线程信息：3秒后执行，一次性任务，到点了就执行
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest5() {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        threadPoolExecutor.schedule(() -> {
            System.out.println("任务被执行，现在时间：" + System.currentTimeMillis() / 1000);
        }, 3000, TimeUnit.MILLISECONDS);
        System.out.println("定时任务，提交成功，时间是：" + System.currentTimeMillis() / 1000 + ",当前线程池中线程数量：" + threadPoolExecutor.getPoolSize());
    }

    /**
     * 6.线程池信息：核心线程数5，最大数量Integer.Max_VALUE,DelayedWorkQueue延时队列，超过核心线程数数量的线程存活时间：0秒
     * 定时执行线程信息：线程固定数量5
     */
    private void threadPoolExecutorTest6() {
        //效果1：提交后，2秒后开始第一次执行，之后每隔1秒，固定执行一次。（如果上次任务没执行完，在上次任务执行完后，立即执行这次任务）
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        threadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1 被执行，现在时间：" + System.currentTimeMillis() / 1000);
        }, 2000, 1000, TimeUnit.MILLISECONDS);

        //效果2：提交后，2秒后开始第一次执行，之后每隔1秒，固定执行一次。（如果上次任务没执行完，在上次任务执行完后，等待1秒，再执行）
        threadPoolExecutor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2 被执行，现在时间：" + System.currentTimeMillis() / 1000);
        }, 2000, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 7.线程池信息：核心线程数5，最大数量10，队列大小3，超过核心线程数数量的线程存活时间：5秒，指定拒绝策略
     * 终止线程：shutdown
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest7() throws Exception {
        //创建一个 核心线程数为5，最大数量为10，等待队列为3的线程池，及最大容纳13个任务
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("有任务被拒绝执行了");
            }
        });
        //测试：提交15个任务，每个需要执行3秒，看超过大小2个，对应的处理情况
        for (int i = 0; i < 15; i++) {
            int n = i;
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("开始执行：" + n);
                    Thread.sleep(3000L);
                    System.err.println("执行结束：" + n);
                } catch (InterruptedException e) {
                    System.out.println("异常：" + e.getMessage());
                }
            });
            System.out.println("任务提交成功：" + i);
        }
        //1秒后终止线程池
        Thread.sleep(1000L);
        threadPoolExecutor.shutdown();
        //再次提交
        threadPoolExecutor.submit(() -> {
            System.out.println("追加一个任务");
        });
    }

    /**
     * 8.线程池信息：核心线程数5，最大数量10，队列大小3，超过核心线程数数量的线程存活时间：5秒，指定拒绝策略
     * 终止线程：shutdownNow
     *
     * @throws Exception
     */
    private void threadPoolExecutorTest8() throws Exception {
        //创建一个 核心线程数为5，最大数量为10，等待队列为3的线程池，及最大容纳13个任务
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("有任务被拒绝执行了");
            }
        });
        //测试：提交15个任务，每个需要执行3秒，看超过大小2个，对应的处理情况
        for (int i = 0; i < 15; i++) {
            int n = i;
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("开始执行：" + n);
                    Thread.sleep(3000L);
                    System.err.println("执行结束：" + n);
                } catch (InterruptedException e) {
                    System.out.println("异常：" + e.getMessage());
                }
            });
            System.out.println("任务提交成功：" + i);
        }
        //1秒后终止线程池
        Thread.sleep(1000L);
        List<Runnable> ls = threadPoolExecutor.shutdownNow();
        //再次提交
        threadPoolExecutor.submit(() -> {
            System.out.println("追加一个任务");
        });
        System.out.println("未结束的任务有：" + ls.size());
    }


    public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
        for (int i = 0; i < 15; i++) {
            int n = i;
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("开始执行：" + n);
                    Thread.sleep(3000L);
                    System.err.println("执行结束：" + n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("任务提交成功：" + i);
        }
        //查看线程数量
        Thread.sleep(500L);
        System.out.println("当前线程池线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待数量为：" + threadPoolExecutor.getQueue().size());
        Thread.sleep(15000L);
        System.out.println("当前线程池线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待数量为：" + threadPoolExecutor.getQueue().size());
    }

    public static void main(String[] args) throws Exception {
//        new Demo7().threadPoolExecutorTest1();
//        new Demo7().threadPoolExecutorTest2();
//        new Demo7().threadPoolExecutorTest3();
//        new Demo7().threadPoolExecutorTest4();
//        new Demo7().threadPoolExecutorTest5();
//        new Demo7().threadPoolExecutorTest6();
//        new Demo7().threadPoolExecutorTest7();
        new Demo7().threadPoolExecutorTest8();
    }
}