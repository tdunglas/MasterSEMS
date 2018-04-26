
#include "stm32f3xx.h"
#include "button.h"

static unsigned char button_state; // is pressed or not

void stm32f3_init_button(){ // allume le peripherique et initialise les pins
    /* Button initialization */
    RCC->AHBENR |= RCC_AHBENR_GPIOAEN;     /* enable GPIO A clock */
    GPIOA->MODER |= 0x00000000;   /* configure A0 for digital input */
    
    button_state = stm32f3_get_button();
}

char stm32f3_get_button(){ // renvoie 1 si bouton appuye, 0 sinon
    return (GPIOA->IDR & GPIO_IDR_0 ) ? 1 : 0; 
}

char stm32f3_just_pressed(){  // renvoie TRUE si le bouton a change d’etat (de 0 vers 1) depuis le dernier appel a cette fonction
    
    if(!button_state && stm32f3_get_button()){
        button_state = !button_state;
        return 1;
    }
    else {
        return 0;
    }
}

char stm32f3_just_released(){
    if(button_state && !stm32f3_get_button()){
        button_state = !button_state;
        return 1;
    }
    else {
        return 0;
    }
}