#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include "buff.h"

int buffInit(buff* sb){
    
    sb->tab = (char*)malloc(SIZE*sizeof(char));
    sb->r = 0;
    sb->w = 0;
    
    if((sem_init(&sb->s1, 0, SIZE)) != 0){
        perror("sem_init empty");
        exit(EXIT_FAILURE);
    }
    
     if((sem_init(&sb->s2, 0, 0)) != 0){
        perror("sem_init empty");
        exit(EXIT_FAILURE);
    }
    
    pthread_mutex_init(&sb->mr, NULL);
    pthread_mutex_init(&sb->mw, NULL);
    
    return 0;
}

char bread(buff* sb){
    /*
    if(sb->r == sb->w){
        printf("read = write : empty\n");
        return 0;
    }
    */
    
    char res;
    int tmp;
    sem_wait(&sb->s2);
    
    pthread_mutex_lock(&sb->mr);
    tmp = sb->r;
    sb->r = (sb->r+1)%SIZE;
    pthread_mutex_unlock(&sb->mr);
    
    res = sb->tab[tmp];
    sem_post(&sb->s1);
    
    return res;
}

int bwrite(buff* sb, char c){
    /*
    if(sb->w == sb->r-1){
        printf("buff full\n");
        return 0;
    }
    */
    
    int tmp;
    sem_wait(&sb->s1);
    
    pthread_mutex_lock(&sb->mw);
    tmp = sb->w;
    sb->w = (sb->w+1)%SIZE;
    pthread_mutex_unlock(&sb->mw);
    
    sb->tab[tmp] = c;
    
    sem_post(&sb->s2);
    
    return 0; 
}  
