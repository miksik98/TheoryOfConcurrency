package tw;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CellProcessor implements Runnable {

    private Semaphore currentProcessorSemaphore;

    private Semaphore nextProcessorSemaphore;

    private Buffer buffer;

    private int delay;


    public CellProcessor(Semaphore currentProcessorSemaphore, Semaphore nextProcessorSemaphore, Buffer buffer) {
        this.currentProcessorSemaphore = currentProcessorSemaphore;
        this.nextProcessorSemaphore = nextProcessorSemaphore;
        this.buffer = buffer;
        Random random = new Random();
        this.delay = (Math.abs(random.nextInt())%4)*500;
    }

    @Override
    public void run() {
        for(int i = 0; i < this.buffer.getData().length; i++) {
            try {
                nextProcessorSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //PROCESSING EXAMPLE
            buffer.getData()[i] += 1;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentProcessorSemaphore.release();
        }
    }
}
