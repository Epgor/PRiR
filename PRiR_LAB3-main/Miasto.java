public class Miasto 
{
    static int Miasto=1;
    static int START=2;
    static int LOT=3;
    static int KONIEC_LOTU=4;
    static int KATASTROFA=5;
    int ilosc_domow;
    int ilosc_zajetych;
    int ilosc_Obywatelow;
    int licznik;
    int x;
    int y;
    Miasto(int ilosc_domow,int ilosc_Obywatelow, int x, int y)
    {
        this.ilosc_domow=ilosc_domow;
        this.ilosc_Obywatelow=ilosc_Obywatelow;
        this.ilosc_zajetych=0;
        this.licznik = ilosc_Obywatelow;
        this.x = x;
        this.y = y;
    }
    synchronized int start(int numer)
    {
        ilosc_zajetych--;
        System.out.println("Obywatel nr: "+numer+" [Opuszczenie domu]");
        return START;
    }
    synchronized int  laduj()
    {
        try
        {
            Thread.currentThread().sleep(1000);
        }
        catch(Exception ie)
        {

        }
        if(ilosc_zajetych<ilosc_domow)
        {
            ilosc_zajetych++;
            System.out.println("Obywatel nr: "+ilosc_zajetych+" [Powrót do domu]");
            return Miasto;
        }
        else 
        {
            return KONIEC_LOTU;
        }
    }
    synchronized void zmniejsz()
    {
        ilosc_Obywatelow--;
        System.out.println("Obywatel zginął");
        if(ilosc_Obywatelow==ilosc_domow) 
            System.out.println("!!! Stosunek Obywatel - Dom 1:1!!!");
    }

    synchronized void wyburz()
    {
        ilosc_domow--;
        System.out.println("Zburzono Dom [!]");
    }

    synchronized void zbuduj()
    {
        ilosc_domow++;
        System.out.println("Zbudowano Dom [!]");
    }

    synchronized void dodaj_obywatela()
    {
        System.out.println("Nowy obywatel [!]");
        ilosc_Obywatelow++;
        new Obywatel(licznik,2000,this, x, y).start();
        licznik++;
    }
}


