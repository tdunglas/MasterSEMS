#include<stdio.h>
#include<unistd.h>

int main(){

	int pid;
	
	pid = fork();
	
	if(pid == -1){
		perror("error during fork");
		return -1;
	}

	if(pid == 0){ // son
		execl("/bin/ls","ls","-l", NULL);
		printf("son : pid father %d\n", getppid());
		printf("son : pid son %d\n", getpid());
	}
	else{ // father
		printf("father : pid father %d\n", getpid());
		printf("father : pid son %d\n", pid);

		wait();
	}

	return 0;
}
