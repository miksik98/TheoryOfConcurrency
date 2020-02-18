package tw;

public class BinarySemaphore {
        private boolean state;

        public BinarySemaphore() {
            this.state = true;
        }

        public synchronized void P() { //opuszczenie semafora
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
