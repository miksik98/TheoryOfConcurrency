package tw;

import java.util.concurrent.Semaphore;

public class Buffer {

    private int[] data;

    private int readIterator;

    private int writeIterator;

    Semaphore consumerSemaphore;

    Semaphore producerSemahore;

    public Buffer(int size, Semaphore consumerSemaphore, Semaphore producerSemahore) {
        this.data = new int[size];
        this.readIterator = 0;
        this.writeIterator = 0;
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemahore = producerSemahore;
    }

    public int[] getData() {
        return this.data;
    }

    public void put(int dataValue) {
        this.data[writeIterator++] = dataValue;
        producerSemahore.release();
    }

    public int take() {
        try {
            consumerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = data[readIterator];
        data[readIterator++] = -1;
        return result;
    }
}
