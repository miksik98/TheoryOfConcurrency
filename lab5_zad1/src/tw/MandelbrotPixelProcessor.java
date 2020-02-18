package tw;

import java.util.concurrent.Callable;

public class MandelbrotPixelProcessor implements Callable {

    private int x;
    private int y;

    public MandelbrotPixelProcessor(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Object call() {
        double zx = 0;
        double zy = 0;
        double cX = (x - 400) / MandelbrotApp.ZOOM;
        double cY = (y - 300) / MandelbrotApp.ZOOM;
        int iter = MandelbrotApp.MAX_ITER;

        while (zx * zx + zy * zy < 4 && iter > 0){
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return new MandelbrotPixel(x, y, iter | (iter << 8));
    }
}
