package demo5;

import java.util.concurrent.locks.LockSupport;

public class Demo53 {

    public static Object baozidian = null;

    //正常
    public void parkUnparkTest1() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                System.out.println("1.进入等待");
                LockSupport.park();
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        LockSupport.unpark(th);
        System.out.println("3.通知消费之");
    }

    //拿到锁后挂起
    public void parkUnparkTest2() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                synchronized (this) {
                    System.out.println("1.进入等待");
                    LockSupport.park();
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            LockSupport.unpark(th);
        }
        System.out.println("3.通知消费之");
    }

    public static void main(String[] args) throws Exception {
//        new Demo53().parkUnparkTest1();
        new Demo53().parkUnparkTest2();
    }
}
