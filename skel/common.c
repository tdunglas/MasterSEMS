    #include "common.h"

    /* send_msg send a message on socket sock
    sock: the socket
    code: message's protocol code
    size: message's size
    msg: message to be sent
    */
    int send_msg(int sock, unsigned char code, unsigned char size, char *body) 
    {
        msg_t msg;
        
        msg.code = code;
        msg.size = size;

        /* Code nécessaire à envoyer le message correspondant au protocle
            sur la socket
        */
        
        if(send(sock, &msg, HEADSIZE, 0) == -1){
            perror("head err");
            return -1;
        }
        
        
        if((size) != 0){
                return HEADSIZE;
        }
        

        if(send(sock, body, size, 0) == -1){
                perror("send err");
            return -1;
        }

        return HEADSIZE + size;
    }

    /* recv_msg recv a message from the socket sock
    sock: the socket
    code: message's protocol code
    size: message's size
    msg: message to be received
    */
    int recv_msg(int sock, unsigned char *code, unsigned char *size, char **body) 
    {
        msg_t msg;
        
        int i = recv(sock, &msg, HEADSIZE, 0);
        printf("succes1\n");
        
        *code = msg.code;
        
        printf("msg %d\n", msg);
        
        printf("*size %d\n", *size);
        printf("msg.size %d\n", msg.size);
        printf("succes2\n");
        
        *size = msg.size;
        printf(" 654 \n");
        
        if( *size == 0){
            return HEADSIZE;
        }
        
        printf("succes3\n");
        *body = malloc((*size)*sizeof(char));
    
        if(recv(sock, *body, *size, 0) <= 0){
            perror("rev err\n");
            free(*body);
            return -1;
        }
        
        printf("msg : %s\n", *body);
        
        return 0;
    }
