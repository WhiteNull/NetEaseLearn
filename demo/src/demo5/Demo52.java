package demo5;

public class Demo52 {
    public static Object baozidian = null;

    //正常
    public void waitNotifyTest1() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                synchronized (this) {
                    try {
                        System.out.println("1.进入等待");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            this.notify();
        }
        System.out.println("3.通知消费之");
    }

    //notify先执行
    public void waitNotifyTest2() throws Exception {
        Thread th = new Thread(() -> {
            if (baozidian == null) {
                try {
                    Thread.sleep(5000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    try {
                        System.out.println("1.进入等待");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2.买到包子，回家");
        });
        th.start();
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            this.notify();
        }
        System.out.println("3.通知消费之");
    }

    public static void main(String[] args) throws Exception {
//        new Demo52().waitNotifyTest1();
        new Demo52().waitNotifyTest2();
    }
}
