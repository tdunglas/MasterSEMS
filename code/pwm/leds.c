#include "stm32f3xx.h"
#include "leds.h"

unsigned char curr_state = 0;

void stm32f3_init_leds(void) // allume le peripherique et initialise les pins
{
    /* LEDs initialization */
    RCC->AHBENR |= (1 << 21);     /* enable GPIO E clock */
    GPIOE->MODER |= 0x55550000;   /* configure E8-E15 for output */
}

void stm32f3_set_led(char num, char v)// num=numero de la LED (0-7), v= 1 (allume) ou 0 (eteint)
{
    if(v){
        GPIOE->ODR |= (v << num + 8); 
    } else {
        GPIOE->ODR &= ~(1 << num + 8); 
    }
}

void stm32f3_set_leds_multiple_on(char value)
{
    GPIOE->ODR |= (value << 8); 
}

void stm32f3_set_all_leds_on()
{
    stm32f3_set_leds_multiple_on(255);
}

void stm32f3_set_leds_multiple_off(char value)
{
    GPIOE->ODR &= ~(value << 8); 
}

void stm32f3_set_all_leds_off()
{
    stm32f3_set_leds_multiple_off(255);
}

char stm32f3_get_leds(char num)
{
   return GPIOE->IDR & (1 << num + 8) ? 1 : 0;  
}

// set all led on then off
void motif_A(){
    
    unsigned char value = 255; // 11111111b
    
    if(curr_state){
        stm32f3_set_leds_multiple_on(value);
    }
    else{
        stm32f3_set_leds_multiple_off(value);
    }

    curr_state = !curr_state;
}

// set all pair led on then off then odd
void motif_B(){
    
    unsigned char value_pair = 85; // 01010101b
    unsigned char value_odd = 170; // 10101010b
    
    if(curr_state){
        stm32f3_set_leds_multiple_on(value_pair);
        stm32f3_set_leds_multiple_off(value_odd);
    }
    else{
        stm32f3_set_leds_multiple_on(value_odd);
        stm32f3_set_leds_multiple_off(value_pair);
    }

    curr_state = !curr_state;
}

// led switch on 0-4 then 1-5...
void motif_C(){
    
    static unsigned char value = 17;    
    unsigned char val_all = 255;
    
    stm32f3_set_leds_multiple_off(val_all);
    stm32f3_set_leds_multiple_on(value);
    
    if(value == 136){
        value = 17;
    }
    else{
        value = value * 2;
    }

    curr_state = !curr_state;
}

// switch led on 0 ... 7
void motif_D(){
    /*
    for(int i=0; i<8; i++){
        stm32f3_set_leds(i,1);
        delay();
        
        stm32f3_set_leds(i,0);
        delay();
    }*/

    curr_state = !curr_state;
}
