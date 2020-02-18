public class Counter {

    private static int variable;

    public Counter(int variable){
        this.variable = variable;
    }

    public String toString(){
        return Integer.toString(this.variable);
    }

    public synchronized void decrement(){
        variable--;
    }

    public synchronized void increment(){
        variable++;
    }

}
