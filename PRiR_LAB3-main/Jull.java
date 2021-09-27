import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Jull extends Thread {

    final static int N = 8192;
    final static int CUTOFF = 100;
    static int[][] set = new int[N][N];

    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        Jull thread0 = new Jull(0);
        Jull thread1 = new Jull(1);
        Jull thread2 = new Jull(2);
        Jull thread3 = new Jull(3);
        Jull thread4 = new Jull(4);


        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try
        {
            thread0.join();
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            System.out.println("Koniec 5 watkow");
        }
        catch (Exception e)
        {
            System.out.println("Error: "+e);
        }


        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenia zakończone w czasie " + (endTime - startTime) + " millisekund");

        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int k = set[i][j];
                float level;
                if (k < CUTOFF)
                {
                    level = (float) k / CUTOFF;
                }
                else
                {
                    level = 0;
                }
                float green = (1 - level )/10;
                if (green >= 1)
                    green = 1;
                if (green <= 0)
                    green = 0;
                Color c = new Color(level, green, level);
                img.setRGB(i, j, c.getRGB());
            }
        }

        ImageIO.write(img, "PNG", new File("D://Jull.png"));
    }

    int me;

    public Jull(int me)
    {
        this.me = me;
    }

    public void run()
    {
        int begin = 0, end = 0;

        if (me == 0)
        {
            begin = 0;
            end = (N / 4) * 1;
        }

        else if (me == 1)
        {
            begin = (N / 4) * 1;
            end = (N / 4) * 2;
        }

        else if (me == 2)
        {
            begin = (N / 4) * 2;
            end = (N / 4) * 3;
        }

        else if (me == 3)
        {
            begin = (N / 4) * 3;
            end = N;
        }

        for (int i = begin; i < end; i++)
        {
            for (int j = 0; j < N; j++)
            {
                double C_r = -0.1;
                double ci = 0.65;
                double Z_r = i * (1.25 - -1.25) / N + -1.25;
                double Z_i = j * (1.25 - -1.25) / N + -1.25;
                int k = 0;

                while (k < CUTOFF && Z_r * Z_r + Z_i * Z_i < 4.0)
                {
                    double new_rrr = C_r + Z_r * Z_r - Z_i * Z_i;
                    double new_iii = ci + 2 * Z_r * Z_i;
                    Z_r = new_rrr;
                    Z_i = new_iii;
                    k++;
                }
                set[i][j] = k;
            }
        }
    }
}
