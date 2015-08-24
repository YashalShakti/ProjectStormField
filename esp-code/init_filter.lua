wifi.setmode(wifi.STATION)
wifi.sta.config("ScreamingSilence", "Knowledge_Is_P0w3r")
tmr.delay(1000000)
print(wifi.sta.getip())
led = 4
gpio.mode(led, gpio.OUTPUT)
gpio.write(led, gpio.LOW)
srv = net.createServer(net.TCP)
print("server created")
srv:listen(80, function(conn)
    conn:on("receive", function(client, request)
        print(request)
        local buf = "";
        local _, _, method, path, vars = string.find(request, "([A-Z]+) (.+)?(.+) HTTP");
        if(method == nil) then
            _, _, method, path = string.find(request, "([A-Z]+) (.+) HTTP");
        end
        local _GET = {}
        if(vars ~=nil) then
            print("--------")
            print(vars)
            for k, v in string.gmatch(vars, "(%w+)=(%w+)&*") do
                _GET[k] = v
            end
        end

        buf = buf.."<h1> ESP8266 Web Server</h1>";
        buf = buf.."<p>GPIO2 <a href=\"?pin=ON1\"><button>ON</button></a>&nbsp;<a href=\"?pin=OFF1\"><button>OFF</button></a></p>";
        local _on, _off = "",""
        if(_GET.pin == "ON1")then
            gpio.write(led, gpio.HIGH)
        elseif(_GET.pin == "OFF1")then
            gpio.write(led, gpio.LOW)
        end
        client:send(buf);
        client:close();
        collectgarbage();
    end)
end)
    
