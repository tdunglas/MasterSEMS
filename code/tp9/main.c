#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "leds.h"
#include "button.h"

#define FREQ 32 // 32 millisconds

unsigned char inc = 0;
unsigned char msgtx;
unsigned char msgrx;

int main()
{
    HAL_Init();

    stmtp_init_leds(); 
    stmtp_init_button();
    
    __HAL_RCC_I2C1_CLK_ENABLE();
    __HAL_RCC_GPIOB_CLK_ENABLE();
    
    HAL_NVIC_EnableIRQ(USART3_IRQn);

    GPIO_InitTypeDef gpio_init = {0};
    gpio_init.Pin = GPIO_PIN_7 | GPIO_PIN_6;
    gpio_init.Speed = GPIO_SPEED_FREQ_HIGH;
    gpio_init.Alternate = GPIO_AF4_I2C1;
    gpio_init.Mode = GPIO_MODE_AF_OD;
    
    HAL_GPIO_Init(GPIOB, &gpio_init);
    
    I2C_HandleTypeDef init = {0};
    init.Instance = I2C1; // ?




    init.BaudRate = 9600;
    init.WordLength = UART_WORDLENGTH_8B;
    init.Parity = UART_PARITY_NONE;
    init.Mode = UART_MODE_TX_RX;

    uart_handle.Init = init;
    uart_handle.Instance = USART3;

    HAL_UART_Init(&uart_handle);

    HAL_UART_Receive_IT(&uart_handle, &msgrx, 1);
    //HAL_UART_Transmit_IT(&uart_handle, &msgtx, 1);
    while(1) { __WFI(); }
}

void USART3_IRQHandler() {   
    HAL_UART_IRQHandler(&uart_handle);
}

void HAL_UART_RxCpltCallback(UART_HandleTypeDef *UartHandle) {

    unsigned char msgcp = msgrx;
    unsigned char client, led, state;
    client = led = state = 0;

    // get client value
    client = msgrx >> 4;

    // message is our
    if(!client){
        state = msgcp & 1;
        led = msgcp << 4; // remove client part
        led = led >> 5; // remove state part

        stmtp_set_led(led, !state);
    }
    else { // send message away with -1 for client
        client = msgcp >> 4;
        client--;
        client = client << 4;

        msgcp = msgcp << 4;
        msgcp = msgcp >> 4;
        msgcp |= client;

        msgtx = msgcp;

        HAL_UART_Transmit_IT(&uart_handle, &msgtx, 1);
    }
}

void HAL_UART_TxCpltCallback(UART_HandleTypeDef *UartHandle) {
    //stmtp_set_led(3, !stmtp_get_led(3));
    HAL_UART_Receive_IT(&uart_handle, &msgrx, 1);
}

void SysTick_Handler()
{
    // server state
    if(stmtp_get_button()){
        inc++;
        if(inc % FREQ == 0) {
            // reset
            if(msgtx == 255){ // 1111 client, 111 led 1 state : 1111 111 1
                msgtx = 0;
            }
            else { // increase
                msgtx++;
            }
            
            HAL_UART_Transmit_IT(&uart_handle, &msgtx, 1);
        }
        
    }
    else { // client state
        msgtx = 0;
        HAL_UART_Receive_IT(&uart_handle, &msgrx, 1);
    }
}
