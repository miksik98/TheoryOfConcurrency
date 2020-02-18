package tw;

public class Printer {

    private boolean reserved = false;

    public boolean isReserved() {
        return reserved;
    }

    public void reserve(){
        reserved = true;
    }

    public void release(){
        reserved = false;
    }
}
