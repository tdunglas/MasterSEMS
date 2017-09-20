#include <stdio.h>
#include <pthread.h>

int i;

void addition(){
	i += 10;
	printf("hello, thread fils %d\n",i);
	i += 20;
	printf("hello, thread fils %d\n",i);
}

int main(){
	pthread_t num;
	i = 0;

	if(pthread_create(&num, NULL, (void*(*)())addition, NULL) == -1){
		perror("pb pthread_create\n");
	}

	i += 1000;

	printf("hello, thread principal %d\n",i);

	i += 2000;

	printf("hello, thread principal %d\n",i);

	pthread_join(num,NULL);

	return 0;
}
