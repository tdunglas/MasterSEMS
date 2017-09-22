    #include "common.h"
    #include "chatroom.h"
    #include <signal.h>
    #include <pthread.h>

    #define MAX_CONN 10            // nombre maximale de requêtes en attentes

    int DFLAG;
    int srv_sock;   

    int create_a_listening_socket(char* srv_port, int maxconn){
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
    
        struct addrinfo hints;
            struct addrinfo* result;
            struct addrinfo* rp;
            
            memset(&hints, 0, sizeof(struct addrinfo));
            hints.ai_family = AF_UNSPEC; // ipv4 & ipv6
            hints.ai_socktype = SOCK_STREAM; // datagram socket
            hints.ai_flags = AI_PASSIVE | AI_ALL; // wild card ip addr
            int s, sfd;
            
            
            
            
            s = getaddrinfo("localhost", srv_port, &hints, &result);
            
            printf("...succes2\n");
            sfd = socket(rp->ai_family, rp->ai_socktype,rp->ai_protocol);
            printf("...succes1\n");
            
            if (bind(sfd, rp->ai_addr, rp->ai_addrlen) == 0)
                printf("succes\n");
    
     printf("...succes3\n");
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
    char* srv_port = "4444"; // find in client code

    /* create a listening socket */
    create_a_listening_socket(srv_port,MAX_CONN);
    
    /* initialize the chat room with no client */
        
    while (1){
        
        /* wait for new incoming connection */
        
        /* register new buddies in the chat room */
        
    } /* while */

    return EXIT_SUCCESS;
    }
