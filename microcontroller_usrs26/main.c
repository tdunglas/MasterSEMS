#include "stm32f3xx.h"
#include "stm32f3xx_hal.h"
#include "system_stm32f3xx.h"
#include "math.h"

// led functions 
void stm32f3_init_leds(void);
void stm32f3_set_led(char num, char v);
void stm32f3_set_leds_multiple_on(char value);
void stm32f3_set_leds_multiple_off(char value);
char stm32f3_get_leds(char num);

// button functions
void stm32f3_init_button();
char stm32f3_get_button(); 
char stm32f3_just_pressed();
char stm32f3_just_released();

// stick functions
void SysTick_Handler();
unsigned char count = 0;
unsigned char curr_state = 0;

// motif functions
void motif_A();
void motif_B();
void motif_C();
void motif_D();

static unsigned char button_state; // is pressed or not
static unsigned char global_state; // clignotement or frequency

const unsigned char FREQUENCY = 128;

void delay() {
  for (float x=0.0f; x<1.0f; x += 0.00001f);
}

int main()
{
    
    // init timer Systick
    HAL_Init();
    
    stm32f3_init_leds();
    stm32f3_init_button();
    
    int i = 0;
    
    while(1) {
        //delay();
    }
}

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

void stm32f3_set_leds_multiple_off(char value)
{
    GPIOE->ODR &= ~(value << 8); 
}

char stm32f3_get_leds(char num)
{
   return GPIOE->IDR & (1 << num + 8) ? 1 : 0;  
}

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

void SysTick_Handler(){
    
    if((count % FREQUENCY) == 0){
        curr_state = !curr_state;
        
        if(count == FREQUENCY * 5){
            count = 0;
        }
        
        
        //motif_A();
        //motif_B();
        motif_C();
        //motif_D();
    }
    count++;
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
}


