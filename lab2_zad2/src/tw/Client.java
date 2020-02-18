package tw;

import java.util.Random;

public class Client implements Runnable {

    private int number;
    private static Shop shop;

    public Client(int number, Shop shop){
        this.number = number;
        this.shop = shop;
    }

    public String toString() {
        return "Client "+Integer.toString(number);
    }

    @Override
    public void run() {
        Basket myBasket = shop.takeBasket(this);
        try {
            Random rand = new Random();
            Thread.sleep(3000+rand.nextInt(10)*1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        shop.returnBasket(this, myBasket);
    }
}
