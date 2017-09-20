TARGETS = hello filesManager essai pipes
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

clean : 
	rm -f $(TARGETS)
