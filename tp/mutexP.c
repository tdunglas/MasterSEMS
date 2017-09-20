#include <stdio.h>
#include <pthread.h>

int bread;
pthread_mutex_t mutex;

void eat(){
	pthread_mutex_lock(&mutex);
	bread -= 1;
	pthread_mutex_unlock(&mutex);
	printf("hello (thread), eating some bread, remain %d\n",bread);

	pthread_mutex_lock(&mutex);
	bread -= 1;
	pthread_mutex_unlock(&mutex);
	printf("hello (thread), eating some bread, remain %d\n",bread);
}

int main(){
	pthread_t th;
	bread = 10;

	pthread_mutex_init(&mutex, NULL);
	
	if(pthread_create(&th, NULL, (void*(*)())eat, NULL) == -1){
		perror("pb pthread_create\n");
	}


	pthread_mutex_lock(&mutex);
	bread -= 5;
	pthread_mutex_unlock(&mutex);

	printf("hello, thread principal remaining bread : %d\n", bread);

	pthread_join(th,NULL);

	return 0;
}
