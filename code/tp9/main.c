#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "leds.h"
#include "button.h"

#define freq 20

void AccelWrite(uint8_t reg, uint8_t* data, uint16_t size);
void AccelRead(uint8_t reg, uint8_t* data, uint16_t size);

unsigned char inc = 0;
I2C_HandleTypeDef i2c = {0};

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
    
    i2c.Instance = I2C1;
    i2c.Mode = HAL_I2C_MODE_MASTER;
    i2c.Init.Timing = 0x00902025;
    i2c.Init.AddressingMode = I2C_ADDRESSINGMODE_7BIT;

    HAL_I2C_Init(&i2c);

    unsigned char reg = 16*2; // 20 hexa

    // 0100 = 50hz for odr
    // 0111 = activate Zen, Yen, Xen
    unsigned char uc = 0b01000111;
    AccelWrite(reg, &uc, 1); 


    while(1) { __WFI(); } 
}

void AccelWrite(uint8_t reg, uint8_t* data, uint16_t size) {    
    /* Transmit data request */
    HAL_I2C_Master_Transmit(&i2c, 0x32, data, size, HAL_MAX_DELAY);
}

void AccelRead(uint8_t reg, uint8_t* data, uint16_t size) {
    
    /* Then we receive the data at this address */
    HAL_I2C_Master_Receive(&i2c, 0x32, data, size, HAL_MAX_DELAY);
}

void SysTick_Handler()
{
    
    if(inc % freq == 0){
        inc = 0; 

        // OUT_X_H_A = 0x29
        unsigned char reg = 0x29;
        uint8_t data[2]; 
        uint8_t request = 0x44;

        AccelRead(reg, data, 2); 
        stmtp_set_led(data[1], !stmtp_get_led(data[1]));

    }

    inc++;
}

// to disable an error from hal which don't have this function
void assert_failed(){}