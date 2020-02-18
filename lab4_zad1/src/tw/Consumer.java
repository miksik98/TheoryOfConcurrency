package tw;

import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.buffer.getData().length; i++) {
            System.out.println(buffer.take());
        }
    }
}