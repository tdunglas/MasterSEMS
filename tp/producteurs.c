#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>

#include "buff.h"

buff * sb; // buffer
    
int reader(void* arg){
    
    int* tmp = (int*)arg;
    int id = *(tmp);
    char c;
    char* str = (char*)malloc(sizeof(char));
    int i = 0;
    
    while(1){
        
        while((c=bread(sb)) != '\n'){
            
                *(str+i) = c;
                //printf("654 %c\n",*(str+i));
                i++;
                str = (char*)realloc(str, i*sizeof(char)+1);
                
                if(c == '\0'){
                    break;
                }
                
                
        }
        
        printf("reader %d read %s\n", id, str);
        i=0;
        free(str);
        str = (char*)malloc(sizeof(char));
    }
    
    return 0;
}

int writer(void* arg){
    
    int* tmp = (int*)arg;
    int id = *(tmp);
    char c;
    char* str; 
    int i = 0;
    int lim;
    /*
            bwrite(sb,'6');
            bwrite(sb,'5');
            bwrite(sb,'4');
            bwrite(sb,'3');
            bwrite(sb,'\0');
    */
    while(1){
        
       //c = getc(stdin);
        str = (char*)malloc(SIZE*sizeof(char));
        snprintf(str, SIZE, "writer %d\n", id);
        
        lim = (int)strlen(str)+1;
        
        while(i<lim){
            printf("%c", *(str+i));
            bwrite(sb,*(str+i));
            i++;
        }
        
        bwrite(sb,'\0');
        i = 0;
        free(str);
        
        sleep(id+1);
       // bwrite(sb,c);
    }
    
    return 0;
}

int main(){
    
    int nr = 4;
    int nw = 3;
    
    pthread_t r[nr];
    pthread_t w[nw];
    int i = 0;
    int idr[nr];
    int idw[nw];
    
    sb = (buff*)malloc(sizeof(buff));
    buffInit(sb);
    
    for(i = 0; i<nr; i++){
        idr[i] = i;
        
        if(pthread_create(&r[i], NULL, (void*(*)())reader, &idr[i]) == -1){
            perror("pb pthread_create r\n");
        }
    }
      
    for(i = 0; i<nw; i++){
        idw[i] = i;
     
        if(pthread_create(&w[i], NULL, (void*(*)())writer, &idw[i]) == -1){
            perror("pb pthread_create w\n");
        }
    }
        
    for(i=0; i<nr; i++){
        pthread_join(r[i],NULL);
    }
    
    for(i=0; i<nw; i++){
        pthread_join(w[i],NULL);
    }
	
	return 0;
}
