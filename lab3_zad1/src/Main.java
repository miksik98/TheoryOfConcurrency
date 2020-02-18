public class Main {

    static BoundedBuffer buffer = new BoundedBuffer();
    static int ILOSC = 10;

    public static void main(String[] args) {
        Consumer consumer = new Consumer(buffer);
        Producer producer = new Producer(buffer);
        Thread consumerThread = new Thread(consumer);
        Thread producerThread = new Thread(producer);

        consumerThread.start();
        producerThread.start();
        try {
            consumerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
