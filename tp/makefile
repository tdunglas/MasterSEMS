TARGETS = hello filesManager essai pipes threadP mutexP philosophers producteurs
CC = gcc
CFLAGS = -lpthread
all : $(TARGETS)

# special bahaviour for make variables
# $< take rule name.c
# $@ take rule name

hello : hello.c
	$(CC) $(CFLAGS) $< -o $@

filesManager : filesManager.c
	$(CC) $(CFLAGS) $< -o $@

essai : essai.c

pipes : pipes.c

threadP : threadP.c
	$(CC) $(CFLAGS) $< -o $@ 

mutexP : mutexP.c
	$(CC) $(CFLAGS) $< -o $@

philosophers : philosophers.c
	$(CC) $(CFLAGS) $< -o $@ 

producteurs : producteurs.c buff.c buff.h
	$(CC) $(CFLAGS) $^ -o $@

clean : 
	rm -f $(TARGETS)
