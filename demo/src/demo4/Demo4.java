package demo4;

public class Demo4 {
    private volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                while (flag) {
                    System.out.println("运行中");
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(3000L);
        flag = false;
        System.out.println("程序运行结束");
    }
}