#ifndef STMTP_LEDS_H__
#define STMTP_LEDS_H__

void stmtp_init_leds(void);

void stmtp_set_all(char val);

void stmtp_set_led(char num, char val);

void stmtp_set_pair_leds(char val);

void stmtp_set_odd_leds(char val);

char stmtp_get_led(char num);

void stmtp_clign_1(void);

void stmtp_clign_2(void);

void stmtp_clign_3(void);

#endif