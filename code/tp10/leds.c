#include "stm32f3xx.h"
#include "leds.h"

#define NB_LEDS 8

void stmtp_init_leds(void)
{
    RCC->AHBENR |= RCC_AHBENR_GPIOEEN;     /* enable GPIO E clock */
    GPIOE->MODER |= 
        GPIO_MODER_MODER8_0 
        | GPIO_MODER_MODER9_0 
        | GPIO_MODER_MODER10_0 
        | GPIO_MODER_MODER11_0
        | GPIO_MODER_MODER12_0
        | GPIO_MODER_MODER13_0
        | GPIO_MODER_MODER14_0
        | GPIO_MODER_MODER15_0; /* configure E8-E15 for output */
}

void stmtp_set_led(char num, char val)
{
    if(val)
        GPIOE->ODR |= (val << num + 8); 
    else
        GPIOE->ODR &= ~(1 << num + 8); 
}

void stmtp_set_all(char val)
{
    for (int i = 0; i < NB_LEDS; i++)
        stmtp_set_led(i, val);
}

void stmtp_set_pair_leds(char val)
{
    for (int i = 0; i < NB_LEDS; i+= 2)
        stmtp_set_led(i, val);
}

void stmtp_set_odd_leds(char val)
{
    for (int i = 1; i < NB_LEDS; i+= 2)
        stmtp_set_led(i, val);
}

char stmtp_get_led(char num)
{
    return GPIOE->ODR & (1 << num + 8) ? 1 : 0;
}

void stmtp_clign_1(void)
{
    stmtp_set_all(!stmtp_get_led(1));
}

void stmtp_clign_2(void)
{
    static char var;

    stmtp_set_pair_leds(!var);
    stmtp_set_odd_leds(var);

    var ^= 0x1;
}

void stmtp_clign_3(void)
{
    static char pos;
 
    char next_pos = (pos + 4) % NB_LEDS;

    stmtp_set_led(pos - 1, 1);
    stmtp_set_led(next_pos, 1);

    stmtp_set_led(pos, 1);
    stmtp_set_led(next_pos, 1);

    //stmtp_set_led( (pos - 1) % NB_LEDS, 0);
    //stmtp_set_led(next_pos, 0);

    pos++;
}
