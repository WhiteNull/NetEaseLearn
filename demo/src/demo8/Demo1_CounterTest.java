package demo8;


public class Demo1_CounterTest {

    public static void main(String[] args) throws Exception {
//        final Counter ct = new Counter();

//        //通过Synchronized加锁
//        final CounterSync ct = new CounterSync();

//        //通过Lock加锁
//        final CounterLock ct = new CounterLock();

//        //通过AtomicInteger实现原子操作
//        final CounterAtomic ct = new CounterAtomic();

        //通过cas方法实现原子操作
        final CounterUnsafe ct = new CounterUnsafe();
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ct.add();
                }
                System.out.println("done...");
            }
            ).start();
        }
        Thread.sleep(2000L);
        System.out.println(ct.i);
    }
}