#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

pthread_t* philosophers;
pthread_mutex_t* forks;
int* tab;
int* tab2;
int n;
int bread = 100;


int consumeBread(){
        int r = rand()%15;
        return r;
}

int waitRan(){
        int t = rand()%3;
        sleep(t);
        return EXIT_SUCCESS;
}

void* eat(void* arg){
    int* tmp = (int*)arg;
    int id = *tmp;
    
    int idf = (id+1)%n;
    int qty;
    
    while(bread >= 0){
        
        printf("philo %d is thinking\n", id);
        waitRan();
            
        pthread_mutex_lock(&forks[id]);
        printf("philo %d use fork %d\n", id, id);
        waitRan();
        
        pthread_mutex_lock(&forks[idf]);
        printf("philo %d use fork %d\n", id, idf);
        waitRan();
           
        if(bread >= 0){
            qty = consumeBread();
            bread -= qty;
            printf("philo %d eat %d bread... remain %d for dinner\n", id, qty, bread);
            waitRan();
        }
        else{
            printf("philo %d want to eat but no bread remain\n", id);
        }
        
        printf("philo %d release fork %d\n", id, id);
        pthread_mutex_unlock(&forks[id]);
        waitRan();
        
        printf("philo %d release fork %d\n", id, idf);
        pthread_mutex_unlock(&forks[idf]);
        waitRan();
        
    }
    
    printf("philo %d leave the room !\n", id);
}


int main(int argc, char** argv){

	if(argv[1] == NULL){
		perror("arg[1] null\n");
		return -1;
	}

    if(argc != 2){
        printf("too much args, default 5 philos");
        n = 5;
    }
    else{
        n = atoi(argv[1]);
        if(n<=0){
                printf("number <= 0, default 5 philos\n");
                n = 5;
        }
    }
    
	philosophers = (pthread_t*)malloc(n*sizeof(pthread_t));
    
    //pthread_t philosophers[n];
    forks = (pthread_mutex_t*)malloc(n*sizeof(pthread_mutex_t));
    int i = 0;
    tab = (int*)malloc(n*sizeof(int));
    tab2 = (int*)malloc(n*sizeof(int));
    
    printf("there is %d philosophers and %d forks\n", n, n);
    
    i = 0;
    while(i<n){
        tab2[i] = i;
		pthread_mutex_init(&forks[i], NULL);
		i++;
	}
	
	i=0;
    while(i<n){
        tab[i] = i;
        if(pthread_create(&philosophers[i], NULL, (void*(*)())eat, &tab[i]) == -1){
			perror("pb pthread_create\n");
		}
        i++;
    }
    
    i = 0;
    while(i<n){
		pthread_join(philosophers[i],NULL);
		i++;
	}

	return 0;
}
