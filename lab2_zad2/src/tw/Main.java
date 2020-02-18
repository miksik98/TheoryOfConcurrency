package tw;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int numberOfBaskets = 10;
    public static int numberOfClients = 30;

    public static void main(String[] args) {
        List<Basket> baskets = new ArrayList<>(numberOfBaskets);
        for (int i = 0; i < numberOfBaskets; i++){
            baskets.add(new Basket(i));
        }
        Shop shop = new Shop(baskets);

        for (int i = 0; i < numberOfClients; i++){
            Client client = new Client(i, shop);

            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }
}
