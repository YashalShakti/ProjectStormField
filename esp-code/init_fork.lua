-- App hotspot details
app_SSID = "ScreamingSilence"
app_pwd = "Knowledge_Is_P0w3r"
wifi.setmode(wifi.STATION)
wifi.sta.config(app_SSID, app_pwd)
wifi.sta.connect()
tmr.delay(1000000)
print(wifi.sta.status())
tmr.delay(1000000)
print(wifi.sta.getip())


-- Indicator led parameters
indicator_led = 4
gpio.mode(indicator_led, gpio.OUTPUT)
gpio.write(indicator_led, gpio.LOW)
led_blink_frequency = 500000

if(wifi.sta.getip()~=nil) then


    gpio.write(indicator_led, gpio.HIGH)
    tmr.delay(led_blink_frequency)
    gpio.write(indicator_led, gpio.LOW)
    tmr.delay(led_blink_frequency)
    gpio.write(indicator_led, gpio.HIGH)
    tmr.delay(led_blink_frequency)
    gpio.write(indicator_led, gpio.LOW)
    tmr.delay(led_blink_frequency)
    gpio.write(indicator_led, gpio.HIGH)
    tmr.delay(led_blink_frequency)
    gpio.write(indicator_led, gpio.LOW)
    tmr.delay(led_blink_frequency)
    
end

--gpio.write(indicator_led, gpio.LOW)

-- Filtering and checking

srv = net.createServer(net.TCP)
print("server created")
srv:listen(80, function(conn)
    conn:on("receive", function(client, request)

        print(request)

        buff = ""
        buff = buff.."HTTP/1.1 200 OK\r\nContent-Type: text/html; charset-UTF-8\r\n"
        buff = buff.."Content-Length: "
        buff = buff..10
        buff = buff.."\r\n"
        buff = buff.."Connection : close\r\n\r\n"
        buff = buff.."abcdefghij"
        






        client:send(buff);
        client:close();
        collectgarbage();
    end)
end)
    


    





