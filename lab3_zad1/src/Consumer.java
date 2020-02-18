public class Consumer implements Runnable {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for(int i = 0; i < Main.ILOSC; i++) {
            try {
                String message = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}