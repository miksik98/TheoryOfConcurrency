package tw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static int PAIR_NUMBER = 10;

    public static void ownMatters(){

        Random generator = new Random();
        System.out.println("\t\tOwn Matters");
        try {
            Thread.sleep(1000+generator.nextInt(10)*500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void eating(){
        Random generator = new Random();
        System.out.println("\tMmm, delicious!");
        try {
            Thread.sleep(1000+generator.nextInt(10)*500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Person[] people = new Person[PAIR_NUMBER*2];

        for (int index=0; index < PAIR_NUMBER*2; index++){
            people[index] = new Person(index);
            if(index%2==1) {
                people[index].setPairIndex(index / 2);
                people[index - 1].setPairIndex(index / 2);
            }
        }

        Waiter waiter = new Waiter();

        List <Thread> threads = new ArrayList<>(PAIR_NUMBER*2);

        for (Person person: people){
            threads.add(new Thread(()->{
                while(true){
                    ownMatters();
                    waiter.wantsTable(person);
                    eating();
                    waiter.release(person);
                }
            }));
        }

        for (Thread t: threads){
            t.start();
        }
    }
}
