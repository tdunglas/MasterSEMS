#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "leds.h"
#include "button.h"

UART_HandleTypeDef huart = {0};

unsigned char msgtx[] = "mississippi\n";
unsigned char msgrx = '0';
unsigned char msgLen = sizeof(msgtx);

unsigned char posFirst = 0;

void updatemsg();

int main()
{
    stmtp_init_leds(); 
    stmtp_init_button();
    
    HAL_Init();
    
    
    __HAL_RCC_GPIOC_CLK_ENABLE();
    
    GPIO_InitTypeDef gpio;
    
    gpio.Pin = GPIO_PIN_4 | GPIO_PIN_5;
    gpio.Mode = GPIO_MODE_AF_PP;
    gpio.Pull = GPIO_NOPULL; // GPIO_PULLUP;
    gpio.Speed = GPIO_SPEED_FREQ_HIGH;
    gpio.Alternate = GPIO_AF7_USART1;
    
    HAL_GPIO_Init(GPIOC, &gpio);
    
    __HAL_RCC_USART1_CLK_ENABLE();

    huart.Instance = USART1;
    huart.Init.BaudRate = 115200;
    huart.Init.WordLength = UART_WORDLENGTH_8B;
    huart.Init.Mode = UART_MODE_TX_RX;
    
    HAL_UART_Init(&huart);
    HAL_NVIC_EnableIRQ(USART1_IRQn);

    HAL_UART_Receive_IT(&huart, &msgrx, 1);

    while(1) { __WFI(); } 
}

void USART1_IRQHandler(){
    HAL_UART_IRQHandler(&huart);
}

void HAL_UART_RxCpltCallback(UART_HandleTypeDef *UartHandle) {
    HAL_UART_Transmit_IT(&huart, msgtx, msgLen);
}

void HAL_UART_TxCpltCallback(UART_HandleTypeDef *UartHandle) {
    updatemsg();
    HAL_UART_Receive_IT(&huart, &msgrx, 1);
}

void updatemsg(){
    char tmp;

    tmp = *(msgtx + posFirst);

    if(posFirst + 1 > msgLen){
        *(msgtx + posFirst) = *(msgtx); 
        *(msgtx) = tmp;
    }
    else {
        *(msgtx + posFirst) = *(msgtx + posFirst + 1); 
        *(msgtx + posFirst + 1) = tmp;
    }

    posFirst = (posFirst + 1) % msgLen;
}

void SysTick_Handler()
{
    HAL_IncTick();
}

// to disable an error from hal which don't have this function
void assert_failed(){}