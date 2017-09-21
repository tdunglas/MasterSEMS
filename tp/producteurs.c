#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#include "buff.h"

buff * sb; // buffer
    
int reader(void* arg){
    return 0;
}

int writer(void* arg){
    return 0;
}

int main(){
    
    pthread_t r1;
    pthread_t w1;
    
    buffInit(sb);
    
    if(pthread_create(&r1, NULL, (void*(*)())reader, sb) == -1){
        perror("pb pthread_create\n");
    }
    
    if(pthread_create(&w1, NULL, (void*(*)())writer, &sb) == -1){
        perror("pb pthread_create\n");
    }
    
    
    pthread_join(r1,NULL);
    pthread_join(w1,NULL);
	
	
	return 0;
}
