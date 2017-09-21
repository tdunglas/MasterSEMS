TARGETS = hello filesManager essai pipes threadP mutexP philosophers
CC = gcc

all : $(TARGETS)

# special bahaviour for make variables
# $< take rule name.c
# $@ take rule name

hello : hello.c
	$(CC) $< -o $@

filesManager : filesManager.c
	$(CC) $< -o $@

essai : essai.c

pipes : pipes.c

threadP : threadP.c
	$(CC) $< -o $@ -lpthread

mutexP : mutexP.c
	$(CC) $< -o $@ -lpthread

philosophers : philosophers.c
	$(CC) $< -o $@ -lpthread

clean : 
	rm -f $(TARGETS)
