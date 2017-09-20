#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>

# define LIMITE 256

int main(){

	int pA[2], pB[2]; // 0 read, 1 write
	int res;
	char* msg = malloc(sizeof(char)*LIMITE);

	if(pipe(pA)){
		perror("pipe");
		exit(1);
	}

	if(pipe(pB)){
		perror("pipe");
		return -1;
	}

	res = fork();

	if(res == -1){
		perror("fork");
		return -1;
	}

	if(res == 0){ // son

		printf("son : %d\n", getpid());

		close(pA[0]); 
		close(pB[1]);		
	
		write(pA[1], "hello, I am process A", LIMITE);
		read(pB[0], msg, LIMITE);
		printf("%d %s\n", getppid(), msg);
	}
	else{ // father

		printf("father : %d\n", getpid());

		close(pA[1]); 
		close(pB[0]);	
	
		read(pA[0], msg, LIMITE);
		printf("%d %s\n",res, msg);
		write(pB[1], "hello, I am process B", LIMITE);

		wait();	
	}

	return 0;
}
