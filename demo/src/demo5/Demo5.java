package demo5;

public class Demo5 {

    public static Object baozidian = null;

    //正常
    public void suspendResumeTest1() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                System.out.println("1.进入等待");
                synchronized (this) {
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        th.resume();
        System.out.println("3.通知消费之");
    }

    //拿锁后线程刮起
    public void suspendResumeTest2() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                System.out.println("1.进入等待");
                synchronized (this) {
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            th.resume();
        }
        System.out.println("3.通知消费之");
    }

    //supspend比resume后执行
    public void suspendResumeTest3() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                System.out.println("1.进入等待");
                synchronized (this) {
                    try {
                        Thread.sleep(5000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        th.resume();
        System.out.println("3.通知消费之");
    }


    public static void main(String[] args) throws Exception {
        //正常调用
//        new Demo5().suspendResumeTest1();
        //锁被挂起
//        new Demo5().suspendResumeTest2();
        //supspend比resume后执行
        new Demo5().suspendResumeTest3();
    }
}