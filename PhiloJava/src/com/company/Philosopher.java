package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {

    private Fork leftFork;
    private Fork rightFork;
    private int number;
    private int numberOfEatProcesses = Main.numberOfEatProcesses;
    private long totalTime = 0;

    public Philosopher(Fork leftFork, Fork rightFork, int number) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.number = number;
    }

    public void run(){
        System.out.println("Hi! I'm philosopher #" + number);

        while (numberOfEatProcesses>0) {
            long start = System.currentTimeMillis();
            //FAMINE version
            /*while(!leftFork.isFree() || !rightFork.isFree()){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            grabAll();*/

            //ASYMETRIC
            /*if(number%2==0){
                rightFork.grab();
                System.out.println("Philosopher #" + number + " grabs right fork.");
                leftFork.grab();
                System.out.println("Philosopher #" + number + " grabs left fork.");
            }
            else{
                leftFork.grab();
                System.out.println("Philosopher #" + number + " grabs left fork.");
                rightFork.grab();
                System.out.println("Philosopher #" + number + " grabs right fork.");
            }*/

            //NAIVE
            /*leftFork.grab();
            System.out.println("Philosopher #" + number + " grabs left fork.");
            rightFork.grab();
            System.out.println("Philosopher #" + number + " grabs right fork.");*/

            //WAITER
            /*askForPermission();
            grabAll();*/
            long end = System.currentTimeMillis();
            totalTime += end-start;
            eat();
            leftFork.release();
            System.out.println("Philosopher #" + number + " releases left fork.");
            rightFork.release();
            System.out.println("Philosopher #" + number + " releases right fork and think again.");
        }
        System.out.println("Philosopher #" + number + " average time: "+totalTime/Main.numberOfEatProcesses);
    }

    public void askForPermission(){
        try {
            Main.waiter.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void grabAll(){
        leftFork.grab();
        System.out.println("Philosopher #" + number + " grabs left fork.");
        rightFork.grab();
        System.out.println("Philosopher #" + number + " grabs right fork.");
    }

    void eat() {
        try {
            int sleepTime = ThreadLocalRandom.current().nextInt(1, 4)*1000;
            System.out.println("Philosopher #"+number + " eats for " + sleepTime);
            Thread.sleep(sleepTime);
            numberOfEatProcesses--;
            //Main.waiter.release(); // WAITER
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}