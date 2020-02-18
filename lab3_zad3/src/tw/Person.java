package tw;

public class Person {
    private int index;
    private int pairIndex;

    public Person(int index){
        this.index = index;
    }

    public void setPairIndex(int pairIndex) {
        this.pairIndex = pairIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPairIndex() {
        return pairIndex;
    }

    @Override
    public String toString() {
        return "Person with id "+Integer.toString(index);
    }
}
