import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);

        Thread threadIncrement = new Thread(() -> IntStream.range(0, 100000000).forEach(i -> counter.increment()));
        Thread threadDecrement = new Thread(() -> IntStream.range(0, 100000000).forEach(i -> counter.decrement()));

        threadIncrement.start();
        threadDecrement.start();

        threadIncrement.join();
        threadDecrement.join();

        System.out.println(counter);
    }
}
