package demo8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock {

    volatile int i = 0;

    //synchronized关键字就是通过lock来实现的
    Lock lock = new ReentrantLock();

    public void add() {
        lock.lock();
        i++;
        lock.unlock();
    }
}
