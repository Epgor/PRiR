import java.util.*;
public class Main 
{
    static int ilosc_Obywatelow=10;
    static int ilosc_domow=5;
    static Miasto Miasto;

    public Main() {}
    public static void main(String[] args)
    {
        System.out.println("Witam w symulatorze Miasta!");
        System.out.println("Programu używasz na własną odpowiedzialność!*");
        System.out.println("*Program potrafi tworzyć nieskończoną ilość wątków w przypadku przyjaznych dla 'obywateli' warunków...");
        System.out.println("...Używaj rozważnie");
        int x, y;
        Scanner s = new Scanner(System.in);
        System.out.println("Podaj początkową ilosc obywateli: "); 
        ilosc_Obywatelow = s.nextInt(); 

        System.out.println("Podaj podaj początkową ilosc domow: "); 
        ilosc_domow = s.nextInt(); 

        System.out.println("Ustal szansę na urodzenie nowego obywatela [zalecane 2-100, wyższa wartość = niższa szansa] : "); 
        x = s.nextInt(); 

        System.out.println("Ustal próg śmiertelności [zalecane 0 - 500, niższa wartość = niższa śmiertelość] : "); 
        y = s.nextInt(); 

        Miasto=new Miasto(ilosc_domow, ilosc_Obywatelow, x, y);
        for(int i=0;i<ilosc_Obywatelow;i++)
            new Obywatel(i,2000,Miasto, x, y).start();
    }
}
