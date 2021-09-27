#include <stdio.h> 
#include <sys/types.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <unistd.h>

float y(float x) 
{ 

    return 4*x-6*x+5; 
} 

void trapezoidal() 
{ 

    float a = 0, b = 0, n = 0;

    

    a = ((rand()%100));

    while(b<=a)
    {
        b = ((rand()%99)+1);
    }

    n = ((rand()%18)+2);

    //printf(" a: %f\n", a);
    //printf(" b: %f\n", b);
    //printf(" n: %f\n", n);

    float h = (b-a)/n; 
  
    float s = y(a)+y(b); 
  
    for (int i = 1; i < n; i++) 
        s += 2*y(a+i*h); 
    
    sleep(1);
    printf("Proces liczy...\n");
  
    printf("Całka równa: %6.4f\n", (h/2)*s); 
} 

int main() 
{ 

    srand( (int)time( NULL ) ); 

    int proces = 2;
    
    printf("Wprowadź ilość procesów: ");
    scanf("%d", &proces);  

    for(int i = 0;i < proces; i++)
    {
        pid_t pid;
  /* wypisuje identyfikator procesu */
        printf("Nowy proces PID = %d\n", getpid());
  /* tworzy nowy proces */
    switch (pid = fork()) 
        {
        case 0: /* proces potomny */
            printf("Proces potomny PID = %d\n", getpid());
            printf("Działam!\n");
            trapezoidal();
            wait(1);
            /* wykonuje program ps */
        default: /* proces macierzysty */
            printf("Proces Macierzysty PID = %d, nic nie robię\n", getpid());
            printf("Czekam na wykonanie mojego procesu potomnego\n");
            wait(1);
            /* czeka na zakonczenie procesu potomnego */     
        }
  
    } 
    return 0;
}
