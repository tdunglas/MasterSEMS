#ifndef STMTP_BUTTON_H__
#define STMTP_BUTTON_H__

void stmtp_init_button(void);

char stmtp_get_button(void);

char stmtp_just_pressed(void);

char stmtp_just_released(void);

void stmtp_poll(void);

#endif