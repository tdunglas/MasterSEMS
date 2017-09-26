#ifndef __BUFF_H__
#define __BUFF_H__
#include <semaphore.h>

#define SIZE 16

typedef struct{
    char* tab; 
    int r; // indice reader
    int w; // indice writer
    sem_t s1; // free space 
    sem_t s2; // used space 
    pthread_mutex_t mr; // lock for r
    pthread_mutex_t mw; // lock for w
}buff;

int buffInit(buff* sb);
char bread(buff* sb);
int bwrite(buff* sb, char c);

#endif // __BUFF_H__
