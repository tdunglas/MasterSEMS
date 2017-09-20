#include<stdio.h>
#include<stdlib.h>

int copy(char* src, char* dest){

	FILE* f = fopen(src,"r"); 
	FILE* copy = fopen(dest,"w+");
	char c;
	int res;

	if(f == NULL){
//		perror("error src file !\n");
		return -1;
	}		
	
/*	if(copy == NULL){
//		perror("error dest file !\n");
		return -1;
	}
*/
	while((c = fgetc(f)) != EOF){	
		
		if(c == -1){
			perror("error reading !\n");
			return -1;
		}

		res = fputc(c,copy);

		if(res == -1){
			perror("error writing !\n");
			return -1;
		}
	}

	printf("file copied !\n");

	return 0;
}

int main(int argc, char** argv){

//	printf("indice : %d", optind);

	if(argv[1] == NULL 
	|| argv[2] == NULL){
		printf("missing args\n");
		return 1;
	}
	
	if(copy(argv[1],argv[2])){
		perror("error while copying!");
	}
	
	return 0;
}
