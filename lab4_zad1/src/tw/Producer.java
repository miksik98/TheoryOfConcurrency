package tw;

import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.buffer.getData().length; i++) {
            buffer.put(i);
        }
    }
}