-- comment
indicator_led = 4
gpio.mode(indicator_led, gpio.OUTPUT)
gpio.write(indicator_led, gpio.LOW)

