#include "common.h"
#include "chatroom.h"
#include <signal.h>
#include <pthread.h>

#define MAX_CONN 10            // nombre maximale de requêtes en attentes

int DFLAG;
int srv_sock;

int create_a_listening_socket(int srv_port, int maxconn){
  int srv_sock = -1;

  /* Code nécessaires à la création d'une socket en
     écoute : 

     - appel à socket() 

     - appel à bind()

     - appel à listen()

     avec les bons paramètres et contrôles d'erreurs.

     La fonction retourne l'identifiant de la socket serveur ou -1 en
     cas d'erreur.
  */

  return srv_sock;
}

int accept_clt_conn(int srv_sock, struct sockaddr_in *clt_sockaddr){
  int clt_sock =-1;

  /* Code nécessaire à l'acception d'une connexion sur
     la socket en écoute (passée en argument via le paramètre srv_sock :
     
     - appel à accept()
     
     avec les bons paramètres et contrôles d'erreurs.
     
     La fonction retourne l'identifiant de la socket cliente ou -1 en
     cas d'erreur.
     
  */

  DEBUG("connexion accepted");

  return clt_sock;
}

int main(void) 
{
  
  DFLAG = 1;

  /* create a listening socket */
  
  /* initialize the chat room with no client */
    
  while (1){
    
    /* wait for new incoming connection */
    
    /* register new buddies in the chat room */
    
  } /* while */

  return EXIT_SUCCESS;
}
