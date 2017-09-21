#ifndef __BUFF_H__
#define __BUFF_H__

#define SIZE 16

typedef struct{
    char* tab;
    int r;
    int w;
}buff;

int buffInit(buff* sb);
int bread(buff* sb);
int bwrite(buff* sb, char c);

#endif
