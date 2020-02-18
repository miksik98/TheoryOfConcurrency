package tw;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for(int i = 0; i < Main.ILOSC; i++) {
            buffer.put("message "+i);
        }
    }
}