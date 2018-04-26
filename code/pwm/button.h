#ifndef BUTTON_H__
#define BUTTON_H__

// button functions
void stm32f3_init_button();
char stm32f3_get_button(); 
char stm32f3_just_pressed();
char stm32f3_just_released();

#endif