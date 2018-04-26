#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "system_stm32f3xx.h"

#include <leds.h>
#include <button.h>

const unsigned char FREQUENCY = 128;
const unsigned char INC = 100;

uint16_t carrier = 0;
uint16_t modulator = 1000;

TIM_HandleTypeDef htim = {0};

void delay(unsigned char limit);
void initgpio();
void imptim2();
void imptim3();

int main()
{
    stm32f3_init_leds();

    HAL_Init();
    __HAL_RCC_TIM2_CLK_ENABLE();
    
    imptim2();
    //imptim3();

    initgpio();
}

void initgpio() {
    __HAL_RCC_GPIOC_CLK_ENABLE();

    GPIO_InitTypeDef gpio_init = { 0 };
    
    gpio_init.Pin = GPIO_PIN_4 | GPIO_PIN_5;
    gpio_init.Mode = GPIO_MODE_AF_PP;
    gpio_init.Pull = GPIO_NOPULL;
    gpio_init.Speed = GPIO_SPEED_FREQ_LOW;
    gpio_init.Alternate = GPIO_AF7_USART1;
    
    HAL_GPIO_Init(GPIOC, &gpio_init);
}

void imptim2(){
    HAL_NVIC_EnableIRQ(TIM2_IRQn);
    
    TIM_Base_InitTypeDef init = {0};
    //init.Prescaler = Min_Data;
    init.Period = 65535;
    //init.CounterMode = TIM_COUNTERMODE_UP;

    htim.Instance = TIM2;
    htim.Init = init;
    
    HAL_TIM_Base_Init(&htim);

    while(1) {
        //carrier = __HAL_TIM_GET_COUNTER(&htim);
        carrier ++;
        //carrier += 1000;
        modulator+=1000;

        if(carrier < modulator){
            stm32f3_set_all_leds_on();
        }
        else {
            stm32f3_set_all_leds_off();
        }

        delay(20);
    }
}

void imptim3(){
    HAL_NVIC_EnableIRQ(TIM3_IRQn);
    
    TIM_Base_InitTypeDef init = {0};
    //init.Prescaler = Min_Data;
    init.Period = 255;
    //init.CounterMode = TIM_COUNTERMODE_UP;

    htim.Instance = TIM3;
    htim.Init = init;
    
    HAL_TIM_Base_Init(&htim);
}
/*
void TIM3_IRQHandler() {
    HAL_TIM_IRQHandler(&htim);
}

void HAL_TIM_PeriodElapsedCallback(TIM_HandleTypeDef *h) {
    stm32f3_set_led(0, !stm32f3_get_leds(0));
}
*/
void delay(unsigned char limit) {
    for(int i=0; i<limit; i++) { }
}

// don't use this function
void assert_failed(){ }

