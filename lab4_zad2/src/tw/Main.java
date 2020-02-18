package tw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        int M = 1000;
        int P = 10;
        int K = 10;

        Buffer buf = new Buffer(M);
        ExecutorService service = Executors.newFixedThreadPool(P+K);

        for(int i=0; i<P; i++) {
            service.submit(new Producer(buf, i));
        }

        for(int i=0; i<K; i++) {
            service.submit(new Consumer(buf, i));
        }

        service.shutdown();
    }
}
