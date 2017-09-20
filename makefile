TARGETS = hello filesManager
CC = gcc

all : $(TARGETS)

# special bahaviour for make variables
# $< take rule name.c
# $@ take rule name

hello : hello.c
	$(CC) $< -o $@

filesManager : filesManager.c
	$(CC) $< -o $@

clean : 
	rm -f $(TARGETS)
