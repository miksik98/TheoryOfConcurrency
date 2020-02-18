package tw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static int PRINTERS_NUMBER = 5;
    public static int THREADS_NUMBER = 10;

    private static void print(int printerNumber){
        Random generator = new Random();
        System.out.println("\tPrinting by " + Integer.toString(printerNumber));
        try {
            Thread.sleep(1000 + generator.nextInt(2) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createNewTaskToPrint(){
        Random generator = new Random();
        try {
            Thread.sleep(1000 + generator.nextInt(10) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\t\tnew thing to print");
    }

    public static void main(String[] args) {

        PrinterMonitor printerMonitor = new PrinterMonitor();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

            for(int i = 0; i < THREADS_NUMBER; i++){
                threads.add(new Thread(() -> {
                    while (true) {
                        try {
                            createNewTaskToPrint();
                            int printerNumber = printerMonitor.reserve();
                            print(printerNumber);
                            printerMonitor.release(printerNumber);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }));
            }

            for (Thread t: threads){
                t.start();
            }


    }
}
