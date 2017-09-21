#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "buff.h"

int buffInit(buff* sb){
    
    sb->tab = (char*)malloc(SIZE*sizeof(char));
    sb->r = 0;
    sb->w = 0;
    
    return 0;
}

int bread(buff* sb){
    
    if(sb->r == sb->w){
        printf("read = write : empty\n");
        return 0;
    }
    
    printf("%c",sb->tab[sb->r]);
    sb->r = (sb->r + 1)%SIZE;
    
    return 0;
}

int bwrite(buff* sb, char c){
    
    if(sb->w == sb->r-1){
        printf("buff full\n");
        return 0;
    }
    
    sb->tab[sb->w] = c;
    sb->w = (sb->w+1)%SIZE;
    
    return 0; 
}  
