#include <stdio.h>
#include <pthread.h>

int bread;
pthread_mutex_t mutex;

void eat(){
	pthread_mutex_lock(&mutex);
	bread -= 1;
	pthread_mutex_unlock(&mutex);
	printf("hello (thread),%d eating some bread, remain %d\n", getpid(),bread);

	pthread_mutex_lock(&mutex);
	bread -= 1;
	pthread_mutex_unlock(&mutex);
	printf("hello (thread),%d eating some bread, remain %d\n", getpid(), bread);
}

int main(int argc, char** argv){

	if(argv[1] == NULL){
		perror("arg must be a number\n");
		return -1;
	}

	int n = (int)argv[1];
	pthread_t* p = (pthread_t*)malloc(n*sizeof(pthread_t));

	pthread_mutex_init(&mutex, NULL);

	bread = 50;
	int i = 0;

	while(i<n){
		
		if(pthread_create(&p[i], NULL, (void*(*)())eat, NULL) == -1){
			perror("pb pthread_create\n");
		}
		
		i++;
	}

	pthread_mutex_lock(&mutex);
	bread -= 5;
	pthread_mutex_unlock(&mutex);

	printf("hello, thread principal remaining bread : %d\n", bread);

	i = 0;
	while(i<n){
		pthread_join(p[i],NULL);
		i++;
	}

	return 0;
}
