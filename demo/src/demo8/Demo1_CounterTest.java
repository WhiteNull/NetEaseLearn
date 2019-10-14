package demo8;


public class Demo1_CounterTest {

    public static void main(String[] args) throws Exception {
        final Counter ct = new Counter();
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