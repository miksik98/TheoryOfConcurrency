package tw;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    final private Lock lock = new ReentrantLock();
    final private Condition isTableAvailable = lock.newCondition();
    final private Condition[] bothPeopleWantsTable = new Condition[Main.PAIR_NUMBER];

    private int[] requests = new int[Main.PAIR_NUMBER];
    private int numberOfClients = 0;

    public Waiter(){
        for (int i = 0; i < Main.PAIR_NUMBER*2; i=i+2){
            bothPeopleWantsTable[i/2] = lock.newCondition();
        }
    }

    public void wantsTable(Person person) {
        int pairIndex = person.getPairIndex();
        lock.lock();
        try{
            requests[pairIndex]++;

            if(requests[pairIndex]<2){
                bothPeopleWantsTable[pairIndex].await();
            }
            else {
                while (numberOfClients > 0){
                    isTableAvailable.await();
                }
            }
            numberOfClients = 2;
            requests[pairIndex] = 0;
            System.out.println("Pair "+Integer.toString(pairIndex)+": person "+person.getIndex()+" sat down");
            bothPeopleWantsTable[pairIndex].signalAll();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
            finally{
            lock.unlock();
        }

    }

    public void release(Person person){
        lock.lock();
        try {
            numberOfClients--;
            System.out.println("Person "+Integer.toString(person.getIndex())+" from pair "+Integer.toString(person.getPairIndex())+" gets up");
            if (numberOfClients == 0){
                isTableAvailable.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}
