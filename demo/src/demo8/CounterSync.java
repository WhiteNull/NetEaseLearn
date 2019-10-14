package demo8;

public class CounterSync {

    volatile int i = 0;

    public synchronized void add() {
        i++;
    }
}
