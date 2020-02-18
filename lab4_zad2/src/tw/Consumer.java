package tw;

import static java.lang.Thread.sleep;

public class Consumer implements Runnable{
    private Buffer buf;
    private int num;

    public Consumer(Buffer buf, int num) {
        this.buf = buf;
        this.num = num;
    }

    public void run() {
            buf.get((int) (Math.random() * 10 + 1), num);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

    }
}
