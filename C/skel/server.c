    #include "common.h"
    #include "chatroom.h"
    #include <signal.h>
    #include <pthread.h>

    #define MAX_CONN 10            // nombre maximale de requêtes en attentes

    int DFLAG;
    int srv_sock;   

    int create_a_listening_socket(char* srv_port, int maxconn){

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
        
        if(s == -1){
            printf("no getaddrinfo\n");
            return -1;
        }
        
        rp = result;
        
        while(rp != NULL){
            sfd = socket(rp->ai_family, rp->ai_socktype,rp->ai_protocol);
            
            if(sfd == -1){
                continue;
            }
            
            if (bind(sfd, rp->ai_addr, rp->ai_addrlen) == 0){
                break;
            }
            rp = rp->ai_next;
        }
        
        if(rp == NULL){
            printf("not bind\n");
            freeaddrinfo(result);
            return -1;
        }
        
        freeaddrinfo(result);
        
        listen(sfd, 10);
        
        return sfd;
    }

    int accept_clt_conn(int srv_sock, struct sockaddr_in *clt_sockaddr){
        int clt_sock =-1;
        socklen_t addrlen;
        
        /* Code nécessaire à l'acception d'une connexion sur
            la socket en écoute (passée en argument via le paramètre srv_sock :
            
            - appel à accept()
            
            avec les bons paramètres et contrôles d'erreurs.
            
            La fonction retourne l'identifiant de la socket cliente ou -1 en
            cas d'erreur.
            
        */
        
        //clt_sock = accept(srv_sock, NULL, NULL);
        
        clt_sock = accept(srv_sock, 
		    (struct sockaddr *)clt_sockaddr,
		    (socklen_t *)&addrlen);
        
        if(clt_sock == -1){
            printf("no accept\n");
            return -1;
        }
        
        DEBUG("connexion accepted");

        return clt_sock;
    }

    int main(void) {
        
        DFLAG = 1;
        char* srv_port = "4444"; // find in client code
        
        /* create a listening socket */
        int listeningsocket = create_a_listening_socket(srv_port,MAX_CONN);
        
        /* initialize the chat room with no client */
        initialize_chat_room();
            
        int sock = -1;
        unsigned char code = MESG;
        while (1){
            
            /* wait for new incoming connection */
            sock = accept_clt_conn(listeningsocket,NULL);
            
            printf("sock %d\n",sock);
            
            if(sock != -1){
                unsigned char size;
                char *body;
                recv_msg(sock, &code, &size, &body);
                
            }
            
            /* register new buddies in the chat room */
            clt_authentication(sock);
            
            struct sockaddr_in sin;
            socklen_t len = sizeof(sin);
            if (getsockname(sock, (struct sockaddr *)&sin, &len) == -1)
                perror("getsockname");
            else{
                printf("port number %d\n", ntohs(sin.sin_port));
                printf("port addr %d\n", ntohs(sin.sin_addr.s_addr));
            
            }
            
            //login_chatroom(sock, char *ip, srv_port);
            
        } /* while */

        return EXIT_SUCCESS;
    }
