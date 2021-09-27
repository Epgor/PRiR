import java.util.*;
import java.util.concurrent.Semaphore ;

abstract class Filozof extends Thread 
{
    int MAX;
    static Semaphore [] widelec = new Semaphore [100] ;
    int mojNum;
    //Random losuj ;
    protected Filozof ( int nr, int max) 
    {
        mojNum=nr ;
        MAX = max;
        
    }
    public abstract void run ();
}

class FilozofNiesym extends Filozof
{
    
    public FilozofNiesym(int mojNum, int MAX)
    {
        super(mojNum, MAX);
    }
    public void run()
    {
      
        while ( true ) //Niesym
        {
            // myslenie
            System.out.println ( "Mysle ¦ " + mojNum) ;
            try 
            {
                Thread.sleep ( ( long ) (5000 * Math.random( ) ) ) ;
            } 
            catch ( InterruptedException e ) 
            {

            }
            if (mojNum == 0) 
            {
                widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
                widelec [mojNum].acquireUninterruptibly ( ) ;
            } 
            else 
            {
                widelec [mojNum].acquireUninterruptibly ( ) ;
                widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
            }
            // jedzenie
            System.out.println ( "Zaczyna jesc "+mojNum) ;
            try 
            {
                Thread.sleep ( ( long ) (3000 * Math.random( ) ) ) ;
            } 
            catch ( InterruptedException e ) 
            {

            }
            System.out.println ( "Konczy jesc "+mojNum) ;
            widelec [mojNum].release ( ) ;
            widelec [ (mojNum+1)%MAX].release ( ) ;
        }

    }
}

class FilozofMonet extends Filozof
{
    Random losuj ;
    
    public FilozofMonet(int mojNum, int MAX)
    {
        super(mojNum, MAX);
        losuj = new Random(mojNum) ;
    }
    public void run()
    {     
        
        while ( true ) //Moneta
        {
            
            // myslenie
            System.out.println ( "Mysle ¦ " + mojNum) ;
            try {Thread.sleep ( ( long ) (5000 * Math.random( ) ) ) ;
            } 
            catch ( InterruptedException e ) 
            {

            }
            
            int strona = losuj.nextInt ( 2 ) ;
            boolean podnioslDwaWidelce = false ;
            do 
            {
                if ( strona == 0) 
                {
                    widelec [mojNum].acquireUninterruptibly ( ) ;
                    if( ! ( widelec [ (mojNum+1)%MAX].tryAcquire ( ) ) ) 
                    {
                        widelec[mojNum].release ( ) ;
                    } 
                    else {podnioslDwaWidelce = true ;
                    }
                } 
                else 
                {
                    widelec[(mojNum+1)%MAX].acquireUninterruptibly ( ) ;
                    if ( ! (widelec[mojNum].tryAcquire ( ) ) ) {widelec[(mojNum+1)%MAX].release ( ) ;
                    }
                    else {podnioslDwaWidelce = true ;
                    }
                }
            } 
            while ( podnioslDwaWidelce == false ) ;
            System.out.println ( "Zaczyna jesc "+mojNum) ;
            try {Thread.sleep ( ( long ) (3000 * Math.random( ) ) ) ;
            }
            catch ( InterruptedException e ) 
            {

            }
            System.out.println ( "Konczy jesc "+mojNum) ;
            widelec [mojNum].release ( ) ;
            widelec [ (mojNum+1)%MAX].release ( ) ;
        }  

    }
}


class FilozofSym extends Filozof
{
    
    public FilozofSym(int mojNum, int MAX)
    {
        super(mojNum, MAX);
    }
    public void run()
    {
      
        while ( true ) //Sym
        {
           // myslenie
           System.out.println ( "Mysle ¦ " + mojNum) ;
           try {
               Thread.sleep ( ( long ) (7000* Math.random( ) ) ) ;
           } 
           catch ( InterruptedException e ) 
           {

           }
           widelec [mojNum].acquireUninterruptibly ( ) ;
           //przechwycenie L widelca
           widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
           //przechwycenie P widelca
           // jedzenie 
           System.out.println ( "Zaczyna jesc "+mojNum) ;
           try {
               Thread.sleep ( ( long ) (5000* Math.random( ) ) ) ;
           } 
           catch ( InterruptedException e ) 
           {

           }
           System.out.println ( "Konczy jesc "+mojNum) ;
           widelec [mojNum].release ( ) ;
           //zwolnienie L widelca
           widelec [ (mojNum+1)%MAX].release ( ) ;
           //zwolnienie P widelca
        }
    }
}



public class FilozofCombo
{
    public static void main ( String [] args ) 
    {

        Scanner s = new Scanner(System.in);
        System.out.println("Podaj ilość wątków: "); 
        int ai = s.nextInt();

        for ( int i =0; i<ai; i++) 
        {
            Filozof.widelec [ i ]=new Semaphore ( 1 ) ;
        }

        System.out.println("Podaj preferowany typ algorytmu filozofów:");
        System.out.println("0 - Rozwiązanie klasyczne");
        System.out.println("1 - Rozwiązanie niesymetrycze");
        System.out.println("2 - Rozwiązanie poprzez rzut monetą");
        int xi = s.nextInt();

        if (xi == 0)
        {
            for ( int i =0; i<ai; i++) 
            {
                new FilozofSym(i, ai).start();
            
            }
        }
        else if (xi == 1)
        {
            for ( int i =0; i<ai; i++) 
            {
                new FilozofNiesym(i, ai).start();
            
            }
        }
        else
        {
            for ( int i =0; i<ai; i++) 
            {
                new FilozofMonet(i, ai).start();
            
            }
        }
    }

}
