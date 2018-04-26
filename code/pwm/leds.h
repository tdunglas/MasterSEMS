#ifndef LEDS_H__
#define LEDS_H__

// led functions 
void stm32f3_init_leds(void);
void stm32f3_set_led(char num, char v);
void stm32f3_set_leds_multiple_on(char value);
void stm32f3_set_all_leds_on();
void stm32f3_set_leds_multiple_off(char value);
void stm32f3_set_all_leds_off();
char stm32f3_get_leds(char num);

// motif functions
void motif_A();
void motif_B();
void motif_C();
void motif_D();

#endif