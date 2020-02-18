import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();
    final Condition notFull = lock.newCondition();

    boolean isEmpty = true;
    String message = "";

    public void put(String message) throws InterruptedException {
        lock.lock();
        try {
            while(!isEmpty)
                notFull.await();
            this.message = message;
            isEmpty = false;
            notEmpty.signal();
        } finally {
            System.out.println("I'm producing "+message);
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        String result = "";
        lock.lock();
        try {
            while(isEmpty)
                notEmpty.await();
            result = this.message;
            this.message = "";
            isEmpty = true;
            notFull.signal();
            return result;
        } finally {
            System.out.println("I'm consuming "+result);
            lock.unlock();
        }
    }
}