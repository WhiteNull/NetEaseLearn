package demo8;

public class Counter {

    volatile int i = 0;

    public void add() {
        i++;
    }
}
