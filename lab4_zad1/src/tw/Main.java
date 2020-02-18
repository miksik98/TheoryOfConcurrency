package tw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int BUFFER_SIZE = 100;
    private static final int PROCESSES_NUMBER = 6; //1st is producer, last is consumer


    public static void main(String[] args) throws InterruptedException {
        Semaphore producerSemaphore = new Semaphore(0);
        Semaphore consumerSemaphore = new Semaphore(0);
        Buffer buffer = new Buffer(BUFFER_SIZE, consumerSemaphore, producerSemaphore);

        Thread producerThread = new Thread(new Producer(buffer));

        Thread consumerThread = new Thread(new Consumer(buffer));

        Semaphore[] semaphores = new Semaphore[PROCESSES_NUMBER];
        semaphores[0] = consumerSemaphore;
        semaphores[PROCESSES_NUMBER-1] = producerSemaphore;
        for (int i = 1; i < PROCESSES_NUMBER-1; i++) {
            semaphores[i] = new Semaphore(0);
        }

        List<Thread> processorThreads = new ArrayList<>();

        for (int i = 0; i < PROCESSES_NUMBER-1; i++) {
            processorThreads.add(new Thread(new CellProcessor(semaphores[i], semaphores[i + 1], buffer)));
        }

        producerThread.start();
        consumerThread.start();
        for (int i = 0; i < PROCESSES_NUMBER-1; i++) {
            processorThreads.get(i).start();
        }

        producerThread.join();
        consumerThread.join();
        for (int i = 0; i < PROCESSES_NUMBER-1; i++) {
            processorThreads.get(i).join();
        }
    }

}
