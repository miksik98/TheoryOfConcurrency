package tw;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();
        Counter counter = new Counter(0, semaphore);

        Thread threadIncrement = new Thread(() -> IntStream.range(0, 100000000).forEach(i -> {
            try {
                counter.semaphore().P();
                counter.decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                counter.semaphore().V();
            }
        }));

        Thread threadDecrement = new Thread(() -> IntStream.range(0, 100000000).forEach(i -> {
            try {
                counter.semaphore().P();
                counter.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                counter.semaphore().V();
            }
        }));

        threadIncrement.start();
        threadDecrement.start();
        try {
            threadIncrement.join();
            threadDecrement.join();
        } catch(InterruptedException ie){
            System.out.println(ie.getMessage());
        }

        System.out.println(counter);
    }
}
