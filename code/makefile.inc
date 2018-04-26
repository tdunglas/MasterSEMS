# Simple makefile template for STM32 project
#
# - include and list objects in OBJS
# - define TARGET

TOOLCHAIN_DIR =

CC = $(TOOLCHAIN_DIR)arm-none-eabi-gcc
OBJCOPY = $(TOOLCHAIN_DIR)arm-none-eabi-objcopy
GDB = $(TOOLCHAIN_DIR)arm-none-eabi-gdb

CMSIS_DIR = ../lib/CMSIS/
HAL_DIR = ../lib/HAL/

INC =   -I . \
        -I .. \
	-I $(CMSIS_DIR) \
	-I $(HAL_DIR) \

LDSCRIPT = $(CMSIS_DIR)/STM32F303XC_FLASH.ld

ARCHFLAGS =  	-mcpu=cortex-m4 \
		-mthumb \
		-mfloat-abi=hard \
		-mfpu=fpv4-sp-d16 \
		-mthumb-interwork \
		-DSTM32F303xC \

CFLAGS= $(INC) $(ARCHFLAGS) \
	-g -O0 \
	-DUSE_HAL_DRIVER \

LDFLAGS= $(ARCHFLAGS) -T $(LDSCRIPT) \
	-Wl,--gc-sections

STARTUP = $(CMSIS_DIR)startup_stm32f303xc
SYSTEM = $(CMSIS_DIR)system_stm32f3xx
HAL = 	stm32f3xx_hal.o \
	stm32f3xx_hal_cortex.o \
	stm32f3xx_hal_gpio.o \
	stm32f3xx_hal_rcc.o \
	stm32f3xx_hal_uart.o \
	stm32f3xx_hal_uart_ex.o \
	stm32f3xx_hal_dma.o \
	stm32f3xx_hal_tim_ex.o \
	stm32f3xx_hal_tim.o \

OBJS += $(STARTUP).o \
	$(SYSTEM).o \
	$(addprefix $(HAL_DIR), $(HAL)) \

all: $(TARGET).bin

%.elf: $(OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(INC) -o $@ $(OBJS)

%.bin: %.elf
	$(OBJCOPY) -O binary $< $@

%.o: %.s
	$(CC) -c -x assembler-with-cpp $(ASFLAGS) $< -o $@

clean:
	rm -f $(OBJS) $(TARGET).elf $(TARGET).bin

flash: $(TARGET).bin
	st-flash erase
	st-flash write $< 0x8000000

debug:
	arm-none-eabi-gdb $(TARGET).elf --eval-command="target remote localhost:4242"

.PRECIOUS: $(OBJS) $(TARGET).elf
