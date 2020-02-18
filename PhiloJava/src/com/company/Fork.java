package com.company;

import java.util.concurrent.Semaphore;

public class Fork {
    private int id;
    public Semaphore mutex = new Semaphore(1);

    public Fork(int id){
        this.id = id;
    }

    void grab() {
        try {
            mutex.acquire();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    void release() {
        mutex.release();
    }

    boolean isFree() {
        return mutex.availablePermits() > 0;
    }

}
