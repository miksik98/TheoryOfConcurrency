package com.company;

import java.util.concurrent.Semaphore;

public class Main {
    public static int numberOfEatProcesses = 5;
    public static int PhilosophersNumber = 5;
    public static Semaphore waiter = new Semaphore(PhilosophersNumber-1);
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[PhilosophersNumber];
        Fork[] forks = new Fork[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < philosophers.length; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork, i);

            Thread t
                    = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}
