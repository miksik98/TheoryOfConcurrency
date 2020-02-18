package tw;

public class Main {
    static Buffer buffer = new Buffer();
    static int ILOSC = 10;

    public static void main(String[] args) {
        Consumer consumer = new Consumer(buffer);
        Producer producer = new Producer(buffer);
        Thread consumerThread = new Thread(consumer);
        Thread producerThread = new Thread(producer);

        consumerThread.start();
        producerThread.start();
    }
}
