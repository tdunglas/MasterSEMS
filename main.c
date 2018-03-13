#include "stm32f3xx.h"
#include "system_stm32f3xx.h"

void init_leds(void);
void set_leds(char num, char v);
char get_leds(char num);

void delay() {
  for (float x=0.0f; x<1.0f; x += 0.00001f);
}

int main()
{

    init_leds();

    /* Button initialization */
    RCC->AHBENR |= (1 << 17);     /* enable GPIO A clock */
    GPIOA->MODER |= 0x00000000;   /* configure A0 for digital input */

    while(1) {
        if (!(GPIOA->IDR & 0x00000001))
            GPIOE->ODR ^= 0x0000FF00;   /* invert pin 8-15 to 1 */
    delay();
    }
}

void init_leds(void) 
{
    /* LEDs initialization */
    RCC->AHBENR |= (1 << 21);     /* enable GPIO E clock */
    GPIOE->MODER |= 0x55550000;   /* configure E8-E15 for output */
}

void set_leds(char num, char v)
{
    if(v){
        GPIOE->ODR |= (v << num + 8); 
    } else {
        GPIOE->ODR &= ~(1 << num + 8); 
    }
}

char get_leds(char num)
{
   return GPIOE->IDR & (1 << num + 8) ? 1 : 0;  
}




