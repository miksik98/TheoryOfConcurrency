package tw;

import java.util.List;

import static tw.Main.numberOfBaskets;

public class Shop {

    private Semaphore countSemaphore = new Semaphore(numberOfBaskets);
    private BinarySemaphore basketsSemaphore = new BinarySemaphore();
    private List<Basket> baskets;

    public Shop(List<Basket> baskets){
        this.baskets = baskets;
    }

    public Basket takeBasket(Client client){

        Basket concreteBasket = new Basket(-1);


        basketsSemaphore.P();
        boolean success = false;
        do {
            for (Basket basket : baskets) {
                if (!basket.taken) {
                    concreteBasket = basket;
                    basket.taken = true;
                    success = true;
                    break;
                }
            }
        } while(!success);
        basketsSemaphore.V();

        countSemaphore.P();
        System.out.println(client+" takes "+concreteBasket);
        return concreteBasket;
    }

    public void returnBasket(Client client, Basket basket){
        System.out.println(client+" returns "+basket);
        basket.taken = false;
        countSemaphore.V();
    }

}
