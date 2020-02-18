package tw;

public class Semaphore {
    private int state;

    public Semaphore(int firstState) {
        this.state = firstState;
    }

    public synchronized void P() { //opuszczenie semafora
        while(state==0) {
            try {
                wait();
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
        state--; //opuszczony
    }

    public synchronized void V() { //podniesienie semafora
        state++;
        this.notifyAll();
        //podniesiony
    }
}