#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>


int pip[2];

void handlerA(int s){
	
	write(STDERR_FILENO,"handler \n",64);		

}

main(){
	int nb_ecrit;
	int pid;

	signal(SIGPIPE,handlerA);

	/*  ouverture d'un pipe */

	if(pipe(pip))
	{ 
		perror("error pipe\n");
		exit(1);
	}
	
	pid = fork();
	
	if (pid == 0)
	{
		close(pip[0]);
		close(pip[1]);
		printf("Je suis le fils\n");
		exit(1);
	}
	else
	{
		close(pip[0]);
		for(;;){
			if ((nb_ecrit = write(pip[1], "ABC", 3)) == -1)
			{

				perror ("pb write");
				exit(1);
			}
			else
				printf ("retour du write : %d\n", nb_ecrit);
			}
		}
}
