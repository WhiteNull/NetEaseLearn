package demo3;

public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        StopThread th = new StopThread();
        th.start();
        Thread.sleep(1000);
//        th.stop();//错误的终止
        th.interrupt();//正确的终止
        while (th.isAlive()) {

        }
        th.print();
    }
}