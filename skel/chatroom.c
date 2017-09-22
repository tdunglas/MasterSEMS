#include "chatroom.h"
#include "common.h"
#include <errno.h>
#include <signal.h>
#include <pthread.h>

#define MAX_AUTH_ATTEMPS 3

typedef struct 
{
} buddy_t;
  
pthread_t chatroom_id;
buddy_t chat_room[MAX_CLIENTS];
int curr_nb_clients = 0;

void *chatroom(void *arg);

void initialize_chat_room()
{

  /* Create the chat room */

}

int broadcast_shutdown();

/* stop_chat_room() should be a safe function */
void stop_chat_room()
{

}

int register_new_client(int sock, char *login, char *ip, int port) 
{
  DEBUG("registering client %s(%s:%d)", login, ip, port);

  /* find the first empty cell in chat_room */
  

  /* check for already too many clients */
  /* signal the new client in the chatroom */
  
  return curr_nb_clients;
}

int deregister_client(int sock) 
{
  /* find the first empty cell in chat_room */

  return 0;
}

int get_client_socket(int i) 
{
  return 0;
}

char *get_client_login(int i) 
{
  return NULL;
}

char *get_client_ip(int i) 
{
  return NULL;
}

int get_client_port(int i) 
{
  return 0;
}

/* broadcast_shutdown send a END_OK message to all chat room clients
   This function is a safe function (could be called in a handler)
 */
int broadcast_shutdown() 
{
  return 0;  
}

/* broadcast_msg: send a message to all chat room client
 */
int broadcast_msg(int code, int size, char *data) 
{
  //int clt_sock;

  DEBUG("broadcasting message \"%s\"", data);

  // envoie le message à tous les clients connectée la socket d'un
  // client peut être récupérée avec la fonction get_client_socket(i),
  // i allant de 0 à MAX_CLIENTS-1
  
  return 0;  
}

int broadcast_text(char *login, char *data) 
{
  // envoie le message data à tous les clients connectée
  // en le préfixant du login l'émetteur
  // utilise broadcast_msg

  return 0;
  
}

/* clt_authentication authenticate a new buddy and return her login
   clt_sock: client socket
   return a pointer to newly allocated string containing the login
 */
char* clt_authentication(int clt_sock){
  int attemp;
  for(attemp=0; attemp < MAX_AUTH_ATTEMPS; attemp++){
    
    /* 
       authentification du client :

       - envoi du AUTH_REQ

       - lecture du AUTH_RESP
       
       - envoi du ACCESS_OK / ACCESS_DENIED ou nouveau tour de boucle

       - en cas de succès retourne un pointeur vers la chaîne de
         caractère contenant le login, sinon retourne NULL

     */
    
  } /* for */
  
  return NULL;
}

int login_chatroom(int clt_sock, char *ip, int port)
{
  char *login;
  
  /* authenticate the connected client */

  /* register the clients in the chat room */
    
  return 0;
}

void *chatroom(void *arg)
{
  int i;
  
  while ( 1 ) 
    {
      fd_set rset;
      int clt_sock;
      int nfds;
      
      FD_ZERO(&rset);

      /* adding clients' sockets to rset */
      
      for (i = 0; i < MAX_CLIENTS; i++) 
	{

	}
      
      // wait for incomming messages (use select)
      	 
      /* find which client sent data */
      
      for (i = 0; i < MAX_CLIENTS; i++) 
	{
	  clt_sock = get_client_socket(i);
	  if ( clt_sock == 0 ) continue;
	  
	  if (!FD_ISSET(clt_sock, &rset)){
	    continue;
	  }
      
	  /* read client i message */
	  /* 
	     - lecture du message émit par le client i

	     - si code MESG: envoie du message à tous les clients avec la fonction broadcast_text()
	     la fonction get_client_login(i) permet de récupérer le login du client i.

	     - sinon traité le message de façon approprié: un client
               qui se déconnecte (END_OK) ou qui renvoi une erreur ou
               provoque un erreur doit être retiré du salon de
               discussion. La fonction deregister_client(i) permet de retirer le client i.

	  */
	}
      
    } /* while (1) */

  return NULL;
}
