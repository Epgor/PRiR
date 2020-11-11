import java.util.ArrayList;
import java.util.Scanner;

abstract class Calka extends Thread
{
    double wynik;
    @Override
    public void run() {
    }
    public double result()
    {
        return wynik;
    }
}   
class M_prostokatow extends Calka
{

    double aii;
    double bii;
    int nii;
    int ii;

    public M_prostokatow(double ai, double bi, int ni, int i)
    {
        aii = ai;
        bii = bi;
        nii = ni;
        ii = i;

    }
    @Override
    public void run()
    {
        wynik = Metody.Kwadr(aii, bii, nii);
        System.out.println("Koniec wątku: "+ii);
    }
    @Override
    public double result()
    {

        return wynik;
    }
}

class M_trapezow extends Calka
{
    double aii;
    double bii;
    int nii;
    int ii;

    public M_trapezow(double ai, double bi, int ni, int i)
    {
        aii = ai;
        bii = bi;
        nii = ni;
        ii = i;
    }
    @Override
    public void run()
    {
        wynik = Metody.Trap(aii, bii, nii);
        System.out.println("Koniec wątku: "+ii);
    }
    @Override
    public double result()
    {
        
        return wynik;
    }
}

class M_Simpsona extends Calka
{
    double aii;
    double bii;
    int nii;
    int ii;
    
    public M_Simpsona(double ai, double bi, int ni, int i)
    {
        aii = ai;
        bii = bi;
        nii = ni;
        ii = i;
    }
    @Override
    public void run()
    {      
        
        wynik = Metody.Simp(aii, bii, nii);
        System.out.println("Koniec wątku: "+ii);
    }
    @Override
    public double result()
    {
        return wynik;
    }
}


class Metody 
{

    private static int ni;
    private static double x;
    private static double g;

    public static double round(double value, int places) {

        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
       
       }

    public static void main(String[] args)
    {

        float distance;

        Scanner s = new Scanner(System.in);
        ArrayList<Calka> watki = new ArrayList<>();


        int ni = 8; //dokładność modyfikowalna z tego poziomu
        double suma = 0;

        System.out.println("Program do całkowania wielowątkowego");
        System.out.println("0 - Met. Prostokątów");
        System.out.println("1 - Met. Trapezów");
        System.out.println("2 - Met. Simpsona");

        int met = s.nextInt();

        System.out.println("Podaj a: "); 
        int ai = s.nextInt(); 

        System.out.println("Podaj b: "); 
        int bi = s.nextInt(); 

        System.out.println("Podaj ilosc watkow: ");
        
        int ilosc_watkow = s.nextInt(); 

        float [] ait = new float[ilosc_watkow];
        float [] bit = new float[ilosc_watkow];

        distance = (float)(bi - ai)/ilosc_watkow;
        distance = (float)round(distance,3);

        for(int i = 0; i<ilosc_watkow; i++)
        {
            ait[i] = (float)round(ai+i*distance,3);
            bit[i] = (float)round(ai+(i+1)*distance,3);
        }

        for(int i = 0; i<ilosc_watkow; i++)
        {

            if(met == 0)
            {
                System.out.println("Wpuszczam wątek met. Prostokatow: "+ (i+1));
                watki.add(new M_prostokatow (ait[i], bit[i], ni, i+1));
            }
            else if(met == 1)
            {
                System.out.println("Wpuszczam wątek met. Trapezow: "+ (i+1));
                watki.add(new M_trapezow(ait[i], bit[i], ni, i+1));
            }
            else if(met == 2)
            {
                System.out.println("Wpuszczam wątek met. Simpsona: "+ (i+1));
                watki.add(new M_Simpsona(ait[i], bit[i], ni, i+1));
            }   
            else
            {
                break;
            }
        }

        for(int i = 0; i < watki.size(); i++)
        {
            watki.get(i).start();
        }

        try
        {
            for(int i = 0; i < watki.size(); i++)
            {
                watki.get(i).join();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println ("error = " + e);
        }

        for(int i = 0; i < watki.size(); i++)
        {
            suma += watki.get(i).result();
        }
    /*
        
        for (int i = 0; i < 10; i++)
        {
            System.out.println(ait[i]);
            System.out.println(bit[i]);
        }

     */   
        System.out.println("Wynik: "+ suma);
        s.close();
    }

    static double funkcja(double x) 
    {        //tutaj def.funkcji
        return (Math.sqrt(1.3*x+2.1))/Math.log(x+0.9);
    }


    static double Simp(double aii, double bii, int ni) {
        double xx = aii;
        double xy = bii;
        double h;
        int n = ni;
        double calka;


 

        h = (xy - xx) / (double) n;
        calka = 0;
        g = 0;

        for (int i = 1; i < n; i++) {
            x = xx + i * h;
            g = g + funkcja(x - h / 2);
            calka = calka + funkcja(x);
        }

        g = g + funkcja(xy - h / 2);
        calka = (h / 6) * (funkcja(xx) + funkcja(xy) + 2 * calka + 4 * g);
        return calka;
    }

    static double Trap(double ai, double bi, int ni) 
    {
        double xx= ai;
        double xy= bi; 
        double h;
        double calka;
        Metody.ni = ni;
        int m = Metody.ni;



        h = (xy - xx)/m;        
        calka = 0;
        
        for (int i=1; i<m; i++) 
        {
            calka = calka + funkcja(xx + i * h);
        }
        
        calka = calka + (funkcja(xx) + funkcja(xy)) / 2;
        calka = calka * h;

        return calka;
    }

    
    public static double gauss(double poczatek, double koniec, double ilosc, double[] ww, double[] tt)
    {
        double a = (koniec - poczatek) /2;   
        double b = (koniec + poczatek) /2;
        double wynik = 0;

        for(int i=0; i<ilosc; i++)
        {
            wynik = wynik + ww[i]*funkcja(a*tt[i] + b);
        }
        
        return wynik * a;
    }
        
    
    static double Kwadr(double ai, double bi, int ni)
    {

        int ilosc = ni;      
        double[] tt = new double[ilosc];
        double[] ww = new double[ilosc];
        double poczatek = ai;
        double koniec= bi; 

        tt[0] = -0.1834346424956498;
        tt[1] = 0.1834346424956498;
        tt[2] = -0.5255324099163290;
        tt[3] = 0.5255324099163290;
        tt[4] = -0.7966664774136267;
        tt[5] = 0.7966664774136267;
        tt[6] = -0.9602898564975363;
        tt[7] = 0.9602898564975363;
        ww[0] = 0.3626837833783620;
        ww[1] = 0.3626837833783620;
        ww[2] = 0.3137066458778873;
        ww[3] = 0.3137066458778873;
        ww[4] = 0.2223810344533745;
        ww[5] = 0.2223810344533745;
        ww[6] = 0.1012285362903763;
        ww[7] = 0.1012285362903763;

        return gauss(poczatek, koniec, 8, ww, tt);

        
        
    }
    
    

}
