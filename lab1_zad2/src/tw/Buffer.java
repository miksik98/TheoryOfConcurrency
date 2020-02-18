package tw;

public class Buffer {

    private String message;
    private boolean isEmpty;

    public Buffer(){
        isEmpty = true;
    }

    public synchronized void put(String message){
        while(!this.isEmpty){
            try{
                wait();
            }
            catch (InterruptedException e){
                System.out.println("Exception: "+e);
            }
        }
        System.out.println("I'm producing "+message);
        this.message = message;
        isEmpty = false;
        notify();
    }

    public synchronized String take(){
        while(this.isEmpty){
            try{
                wait();
            }
            catch (InterruptedException e){
                System.out.println("Exception: "+e);
            }
        }
        System.out.println("I'm consuming "+message);
        isEmpty = true;
        notify();
        return this.message;
    }

}

