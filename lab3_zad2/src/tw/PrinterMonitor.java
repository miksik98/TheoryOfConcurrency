package tw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {

    final private Lock lock = new ReentrantLock();
    final Condition notReservedExists = lock.newCondition();
    final Condition reservedExists = lock.newCondition();

    private int reservedCounter; // default := 0


    List <Printer> printers = new ArrayList<>(Main.PRINTERS_NUMBER);

    public PrinterMonitor(){
        for (int i = 0; i < Main.PRINTERS_NUMBER; i++){
            printers.add(new Printer());
        }
    }


    public int reserve() throws InterruptedException {

        int resultNumber = -1;
        lock.lock();
        try {
            while (reservedCounter == Main.PRINTERS_NUMBER)
                notReservedExists.await();

            for (int i = 0; i < Main.PRINTERS_NUMBER; i++) {
                if (!printers.get(i).isReserved()) {
                    printers.get(i).reserve();
                    ++reservedCounter;
                    reservedExists.signal();
                    resultNumber = i;
                    break;
                }
            }

            if (resultNumber == -1) throw new IllegalThreadStateException();
            return resultNumber;
        } finally {
            System.out.println("Printer "+resultNumber+" reserved");
            lock.unlock();
        }
    }

    public void release(int printerNumber) throws InterruptedException {
        lock.lock();
        try {
            while(reservedCounter == 0)
                reservedExists.await();
            printers.get(printerNumber).release();
            --reservedCounter;
            notReservedExists.signal();
        } finally {
            System.out.println("Printer "+printerNumber+" released");
            lock.unlock();
        }
    }
}
