-- Get ssid and password from two files

file.open("wifi_home_ssid.lua", "r")
wifi_home_ssid = file.readline()
wifi_home_ssid = string.sub(wifi_home_ssid, 5)..""
print(wifi_home_ssid)
file.close()

file.open("wifi_home_pwd.lua", "r")
wifi_home_pwd = file.readline()
wifi_home_pwd = string.sub(wifi_home_pwd, 5)..""
print(wifi_home_pwd)
file.close()

wifi_home_connection_count = 0
wifi_home_connection_count_max = 3

wifi_home_sid = "ScreamingSilence"
wifi_home_pd = "Knowledge_Is_P0w3r"

-- led setup
indicator_led = 4
gpio.mode(indicator_led, gpio.OUTPUT)
gpio.write(indicator_led, gpio.LOW)

wifi.setmode(wifi.STATION) 
wifi.sta.disconnect()
wifi.sta.config(wifi_home_ssid, wifi_home_pwd)
wifi.sta.connect()
tmr.delay(1000000)
print(wifi.sta.status())
tmr.delay(1000000)
print(wifi.sta.getip())



    


