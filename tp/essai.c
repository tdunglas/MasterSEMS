#include<stdio.h>

int main(){

	int pid;
	
	pid = fork();
	
	if(pid == -1){
		perror("error during fork");
		return -1;
	}

	if(pid == 0){
		for(;;){
			printf("SON\n");
		}
	}
	else{
		for(;;){
			printf("FATHER\n");
		}
	}

	return 0;
}
