package tw;

public class Counter {

    private int variable;
    private Semaphore semaphore;

    public Counter(int variable, Semaphore semaphore){
        this.variable = variable;
        this.semaphore = semaphore;
    }

    public Semaphore semaphore(){
        return this.semaphore;
    }

    public String toString(){
        return Integer.toString(this.variable);
    }

    public void decrement(){
        variable--;
    }

    public void increment(){
        variable++;
    }

}
