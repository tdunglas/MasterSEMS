#include "stm32f3xx.h"
#include "button.h"

static char btn_state;

void stmtp_init_button(void)
{
    RCC->AHBENR |= RCC_AHBENR_GPIOAEN;     /* enable GPIO A clock */
}

char stmtp_get_button(void)
{
    return GPIOA->IDR & GPIO_IDR_0 ? 1 : 0; 
}

char stmtp_just_pressed(void)
{
    return !btn_state && stmtp_get_button();
}

char stmtp_just_released(void)
{
    return btn_state && !stmtp_get_button();
}

void stmtp_poll(void)
{
    btn_state = stmtp_get_button();
}