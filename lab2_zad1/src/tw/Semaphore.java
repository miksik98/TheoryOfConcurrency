package tw;

public class Semaphore {
    private boolean state;

    public Semaphore() {
        this.state = true;
    }

    public synchronized void P() throws InterruptedException { //opuszczenie semafora
        while(!state) {
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
        state = false; //opuszczony
    }

    public synchronized void V() { //podniesienie semafora
        state = true;
        this.notifyAll();
         //podniesiony
    }
}