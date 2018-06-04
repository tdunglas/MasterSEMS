#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "leds.h"
#include "button.h"

#define FREQ 128
#define msgLen 8

const unsigned char* msg = "hello";
unsigned char msgrx = '0';
UART_HandleTypeDef huart = {0}; 

void delay() {
    for (float x=0.0f; x<1.0f; x += 0.00001f);
}

int main()
{
    HAL_Init();

    stmtp_init_leds();
    stmtp_init_button();
    
    __HAL_RCC_GPIOC_CLK_ENABLE();
    
    GPIO_InitTypeDef gpio;
    
    gpio.Pin = GPIO_PIN_4 | GPIO_PIN_5;
    gpio.Mode = GPIO_MODE_AF_PP;
    gpio.Pull = GPIO_NOPULL; // GPIO_PULLUP;
    gpio.Speed = GPIO_SPEED_FREQ_HIGH;
    gpio.Alternate = GPIO_AF7_USART1;
    
    HAL_GPIO_Init(GPIOC, &gpio);
    
    __HAL_RCC_USART1_CLK_ENABLE();
    
    UART_InitTypeDef init = {0};
    init.BaudRate = 9600;
    init.WordLength = UART_WORDLENGTH_8B;
    init.StopBits = UART_STOPBITS_1_5;
    init.Parity = UART_PARITY_NONE;
    init.Mode = UART_MODE_TX_RX;
    init.HwFlowCtl = UART_HWCONTROL_RTS_CTS;
    init.OverSampling = UART_OVERSAMPLING_8;
    init.OneBitSampling = UART_ONE_BIT_SAMPLE_ENABLE;
    
    huart.Instance = USART1;
    huart.Init = init; 
    
    HAL_UART_Init(&huart);
         
    HAL_NVIC_EnableIRQ(USART1_IRQn);
    
    HAL_UART_Receive_IT(&huart, &msgrx, 1);
    while(1) { __WFI(); }
}

void USART1_IRQHandler() {    
    stmtp_set_led(0, 1);
    HAL_UART_IRQHandler(&huart);
}

void HAL_UART_RxCpltCallback(UART_HandleTypeDef *UartHandle) {
    stmtp_set_led(1, 1);
    HAL_UART_Transmit_IT(&huart, (unsigned char*)msg, msgLen);
    stmtp_set_led(2, 0);
}

void HAL_UART_TxCpltCallback(UART_HandleTypeDef *UartHandle) {
    stmtp_set_led(2, 1);
    HAL_UART_Receive_IT(&huart, &msgrx, 1);
    stmtp_set_led(1, 0);
}


void SysTick_Handler()
{
    static char cnt;

    if ((++cnt % FREQ) < 20)
    {
    stmtp_set_led(3, 1);
    }
    else
    {
        stmtp_set_led(3, 0);
    }
}
