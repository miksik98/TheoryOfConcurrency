package tw;

import static java.lang.Thread.sleep;

public class Producer implements Runnable{
    private Buffer buf;
    private int num;

    public Producer(Buffer buf, int num)  {
        this.buf = buf; this.num = num;
    }

    public void run() {
        int a[] = new int[(int) (Math.random() * 10 + 1)];
        for(int i=0; i<a.length; i++) {
            a[i] = (int)(Math.random() * 10) + 1;
        }

            buf.put(a, num);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

    }
}
