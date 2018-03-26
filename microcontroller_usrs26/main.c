#include "stm32f3xx.h"
#include "system_stm32f3xx.h"

// led functions 
void stm32f3_init_leds(void);
void stm32f3_set_leds(char num, char v);
char stm32f3_get_leds(char num);

// button functions
void stm32f3_init_button();
char stm32f3_get_button(); 
char stm32f3_just_pressed();
char stm32f3_just_released();

static unsigned char button_state;

void delay() {
  for (float x=0.0f; x<1.0f; x += 0.00001f);
}

int main()
{

    stm32f3_init_leds();
    stm32f3_init_button();
    

    while(1) {
        if (!(GPIOA->IDR & 0x00000001))
            GPIOE->ODR ^= 0x0000FF00;   /* invert pin 8-15 to 1 */
    delay();
    }
}

void stm32f3_init_leds(void) // allume le peripherique et initialise les pins
{
    /* LEDs initialization */
    RCC->AHBENR |= (1 << 21);     /* enable GPIO E clock */
    GPIOE->MODER |= 0x55550000;   /* configure E8-E15 for output */
}

void stm32f3_set_leds(char num, char v)// num=numero de la LED (0-7), v= 1 (allume) ou 0 (eteint)
{
    if(v){
        GPIOE->ODR |= (v << num + 8); 
    } else {
        GPIOE->ODR &= ~(1 << num + 8); 
    }
}

char stm32f3_get_leds(char num)
{
   return GPIOE->IDR & (1 << num + 8) ? 1 : 0;  
}

void init_button(){ // allume le peripherique et initialise les pins
    /* Button initialization */
    RCC->AHBENR |= (1 << 17);     /* enable GPIO A clock */
    GPIOA->MODER |= 0x00000000;   /* configure A0 for digital input */
    
    button_state = stm32f3_get_button();
}

char stm32f3_get_button(){ // renvoie 1 si bouton appuye, 0 sinon
    return GPIOA->IDR & (1 << 17) ? 1 : 0; 
}

char stm32f3_just_pressed(){  // renvoie TRUE si le bouton a change d’etat (de 0 vers 1) depuis le dernier appel a cette fonction
    
    if(!button_state && stm32f3_get_button()){
        button_state = !button_state;
        return true;
    }
    else {
        return false;
    }
}

char stm32f3_just_released(){
    if(button_state && !stm32f3_get_button()){
        button_state = !button_state;
        return true;
    }
    else {
        return false;
    }
}



