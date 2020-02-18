package tw;

public class Basket {
    private int number;
    protected boolean taken;

    public Basket(int number){
        this.number = number;
        this.taken = false;
    }

    public String toString(){
        return "Basket "+Integer.toString(number);
    }
}
