import java.util.Random;
public class Obywatel extends Thread 
{
    //definicja stanu Obywatelu
    static int Miasto=1;
    static int START=2;
    static int RUCH=3;
    static int KONIEC_RUCHU=4;
    static int ZGON=5;
    static int KARM=1000;
    static int GLODNY=500;
    //zmienne pomocnicze
    int numer;
    int jedzenie;
    int stan;
    Miasto l;
    Random rand;
    int x;
    int y;
    public Obywatel(int numer, int jedzenie, Miasto l, int x, int y)
    {
        this.numer=numer;
        this.jedzenie=jedzenie;
        this.stan=RUCH;
        this.l=l;
        this.x = x;
        this.y = y;
        rand=new Random();
    }
    public void run()
    {
        while(true)
        {
            if(rand.nextInt(2)==1)
            {
                l.zbuduj();
            }
            else
            {
                if(rand.nextInt(2)==1)
                    l.zbuduj();
                else
                    l.wyburz();
            }
            if(rand.nextInt(x)==1)           
            {
                l.dodaj_obywatela();
            }
            if(stan==Miasto)
            {
                if(rand.nextInt(2)==1)
                {
                    stan=START;
                    jedzenie=KARM;
                    System.out.println("Obywatel "+numer+"[Prośba o możliwość opuszczenia domu]");
                    stan=l.start(numer);
                }
                else
                {
                    System.out.println("[Obywatel oczekuje]");
                }
            }
            else if(stan==START)
            {
                System.out.println("Obywatel: "+numer+"[Zgłasza opuszczenie miejsca zamieszkania]");
                stan=RUCH;
            }
            else if(stan==RUCH)
            {
                jedzenie-=rand.nextInt(500);
                System.out.println("Obywatel "+numer+" [W ruchu]");
                if(jedzenie<=GLODNY)
                {
                    stan=KONIEC_RUCHU;
                }
                else try
                {
                    sleep(rand.nextInt(1000));
                }
                catch (Exception e)
                {
                    //exception
                }
            }
            else if(stan==KONIEC_RUCHU)
            {
                System.out.println("Obywatel: "+numer+"[Prośba o możliwość powrotu] Stan: "+(jedzenie/10)+"%");
                stan=l.laduj();
                if(stan==KONIEC_RUCHU)
                {
                    jedzenie-=rand.nextInt(500);
                    if(jedzenie<=y) 
                    {
                        stan=ZGON;                       
                    }
              
                }
            }
            else if(stan==ZGON)
            {
                System.out.println("Obywatel: "+numer+" [Status: Martwy]");
                l.zmniejsz();
                this.stop(); //uśmiercanie wątku
                
            }
        }
        
    } 
}
