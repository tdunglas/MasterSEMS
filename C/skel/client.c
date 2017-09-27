    #include "common.h"
    #include <signal.h>

    char *srv_name = "localhost";
    int clt_sock;

    int DFLAG;

    /* Établie une session TCP vers srv_name sur le port srv_port
    * char *srv_name: nom du serveur (peut-être une adresse IP)
    * int srv_port: port sur lequel la connexion doit être effectuée
    *
    * renvoie: descripteur vers le socket
    */ 
    int connect_to_server(char *srv_name, char* srv_port){
        int clt_sock = -1;

        /* Code nécessaires à la création d'une socket en
            écoute : 
            
            - résolution du nom avec gethostbyname
            
            - appel à socket() 
            
            - appel à connect()
            
            avec les bons paramètres et contrôles d'erreurs.

            La fonction retourne l'identifiant de la socket cliente ou -1 en cas d'erreur
        */
        
        
        struct addrinfo hints;
        struct addrinfo* result;
        struct addrinfo* rp;
        
        memset(&hints, 0, sizeof(struct addrinfo));
        hints.ai_family = AF_UNSPEC; // ipv4 & ipv6
        hints.ai_socktype = SOCK_STREAM; // datagram socket
        hints.ai_flags = AI_PASSIVE | AI_ALL; // wild card ip addr
        
        int s, sfd;
        
        s = getaddrinfo(NULL, srv_port, &hints, &result);
        
        if (s != 0) {
            fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
            return -1;
        }

        rp = result;
        
        while(rp != NULL){
            sfd = socket(rp->ai_family, rp->ai_socktype, rp->ai_protocol);
            
            if(sfd == -1){
                rp = rp->ai_next;
                continue;
            }
            
            if (connect(sfd, rp->ai_addr, rp->ai_addrlen) != -1){
                printf("client connect success!\n");
                break;                  /* Success */
            }
            
            close(sfd);
            
            rp = rp->ai_next;
        }
        
        clt_sock = sfd;
        
        if(rp == NULL){
            printf("no connect\n");
            freeaddrinfo(result);
            return -1;
        }
        
        if(clt_sock == -1){
            freeaddrinfo(result);
            return -1;
        }
        
        freeaddrinfo(result);
        
        return clt_sock;
    }

    int authenticate(int clt_sock){

        /* Code nécessaire à l'authentification auprès du serveur :

            - attendre un paquet AUTH_REQ

            - répondre avec un paquet AUTH_RESP
            
            - attendre un paquet ACCESS_OK / ACCESS_DENIED / AUTH_REQ

            - agir en conséquence ...

        */
        
        while(1){
            
            unsigned char size;
            char *b;
            unsigned char code;
        
            recv_msg(clt_sock, &code, &size, &b); 
            
            switch(code){
                case AUTH_REQ: printf("authentication...\n");
                
                    printf("enter a login : ");
                    char* input = (char*)malloc(sizeof(char)*126);
                    scanf("%s", input);
                
                    code = AUTH_RESP;
                    send_msg(clt_sock, code, strlen(input)+1, input); // send AUTH_RESP
            
                break;
                
                case ACCESS_OK: printf("authentication... success\n");
                
                    printf("log recv %s\n", b);
                    
                    return 0;
                break;
                
                default: printf("no auth\n");
                    return -1;
            }
            
        }
            
        return -1;
    }

    int instant_messaging(int clt_sock){
    
        while(1){
            fd_set rset;
            unsigned char code;
            unsigned char size;
            char *data;

            FD_ZERO(&rset);
            FD_SET(clt_sock, &rset);
            FD_SET(STDIN_FILENO, &rset);
            
            
            /* pour les étapes 2 à 4 se contenter de recevoir les messages
            envoyés par le serveur et les afficher à l'utilisateur
            */

             if (select(clt_sock+1, &rset, NULL, NULL, NULL) < 0){
               PERROR("select");
               exit(EXIT_FAILURE);
             }
            
             if (FD_ISSET(STDIN_FILENO, &rset)){
                 
            /* l'utilisateur a tapé un nouveau message */
              DEBUG("STDIN_FILENO isset");
               data = malloc(BUFFSIZE);
               
               if (fgets(data, BUFFSIZE, stdin) == NULL){
                /* gérer feof et ferror */
                
                    //   <COMPLÉTER>
                    if(send_msg(clt_sock , END_OK, 0, NULL) == -1){
                        perror("sending END_OK failed");
                    }
            
             	return 0;
               }
               
               size = strlen(data)+1;
            
               DEBUG("sending MESG %s(%d)", data, size);

               
                if(send_msg(clt_sock, MESG, size, data) == -1){
                    perror("sending MESG failed");
                }
                
               free(data);
            
             }

              if (FD_ISSET(clt_sock, &rset)){
            /* réception d'un message du serveur */
            /* expected: <code><datalen>[<data>] */

            //  <COMPLÉTER>
                char *msg;
                unsigned char size2;
                recv_msg(clt_sock, &code, &size2, &msg);
                printf("msg %s\n", msg);
            }
            
        } /* while (1) */

        return 0;
    }

    int main(int argc, char *argv[]){
        // char srv_name[BUFFSIZE];
        char* srv_port = "4444";
        int clt_sock;
        DFLAG = 1;

        // connect to the server 
        clt_sock = connect_to_server(srv_name, srv_port); 
        
        if(clt_sock < 0){
            printf("sock < 0");
            return -1;
        }
  
        /*testing*/
        unsigned char code = MESG;
        char* body = "hello from client";
        send_msg(clt_sock, code, strlen(body), body);
        
        // authenticate
        authenticate(clt_sock);
        
        printf("654");
        // start instant messaging app
        if (instant_messaging(clt_sock) < 0){
            close(clt_sock);
            eprintf("connexion closed\n");
            exit(EXIT_FAILURE);
        }

        close(clt_sock);
        eprintf("connexion closed\n");
        exit(EXIT_SUCCESS);
    }
