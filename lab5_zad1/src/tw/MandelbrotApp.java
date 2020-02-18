package tw;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static java.lang.Math.sqrt;

public class MandelbrotApp extends JFrame {

    static int MAX_ITER = 10;
    static double ZOOM = 150;
    private static int THREADS = 4;
    private BufferedImage I;

    public MandelbrotApp() throws ExecutionException, InterruptedException {

        super("Mandelbrot Set");
        setBounds(10,10,60,80);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        ArrayList<Long> times = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            long time1 = System.currentTimeMillis();
            ExecutorService pool = Executors.newFixedThreadPool(getHeight()*getWidth());
            Set<Future<MandelbrotPixel>> futureSet = new HashSet<>();

            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    Callable<MandelbrotPixel> callable = new MandelbrotPixelProcessor(x, y);
                    Future<MandelbrotPixel> future = pool.submit(callable);
                    futureSet.add(future);
                }
            }

            long time2 = System.currentTimeMillis();
            times.add(time2 - time1);
            for (Future<MandelbrotPixel> future : futureSet) {
                MandelbrotPixel mandelbrotPixel = future.get();
                I.setRGB(mandelbrotPixel.getX(), mandelbrotPixel.getY(), mandelbrotPixel.getRgb());
            }

            System.out.println(i);
        }

        long average = 0;
        long sigma2= 0;
        for(Long time: times){
            average += time;
        }
        average /= times.size();

        for(Long time: times){
            sigma2 += (time - average) * (time - average);
        }
        sigma2 /= times.size();

        System.out.println("Average: "+Long.toString(average));
        System.out.println("Standard deviation: "+sqrt(sigma2));;

    }

    @Override
    public void paint(Graphics g){
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new MandelbrotApp().setVisible(true);
    }
}
