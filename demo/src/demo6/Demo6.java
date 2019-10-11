package demo6;

public class Demo6 {

    public static ThreadLocal<String> value = new ThreadLocal<>();


    public void threadLocalTest1() throws Exception {
        value.set("主线程1value123");
        String v = value.get();
        System.out.println("主线程1threadlocal:" + v);

        new Thread(() -> {
            String tv = value.get();
            System.out.println("线程1threadlocal:" + tv);
            value.set("线程1value456");
            tv = value.get();
            System.out.println("线程1新threadlocal:" + tv);
            System.out.println("线程1结束");
        }).start();

        Thread.sleep(2000L);
        v = value.get();
        System.out.println("主线程1再次threadlocal:" + v);
    }

    public void threadLocalTest2() throws Exception {
        String v = value.get();
        System.out.println("主线程2threadlocal:" + v);

        new Thread(() -> {
            String tv = value.get();
            System.out.println("线程1threadlocal:" + tv);
            value.set("线程1valueAAA");
            tv = value.get();
            System.out.println("线程1新threadlocal:" + tv);
            System.out.println("线程1结束");
        }).start();

        Thread.sleep(2000L);
        v = value.get();
        System.out.println("主线程2再次threadlocal:" + v);
    }


    public static void main(String[] args) throws Exception {
        new Demo6().threadLocalTest1();
        new Demo6().threadLocalTest2();
    }
}