#include "ESP8266WiFi.h"
#include <ESP8266WiFi.h>
#include "ArduinoJson.h"
#include <FS.h>

//Wifi parameters
const char* ssid = "TheAvatar";
const char* password = "ghar*123#";
const char *espAP = "InternetOfThings";
const char *espPass = "password";

//Test pin
int TEST_LED = 12;

//Wifi server
WiFiServer server(80);

void setup() {
    initHW();
    setupWifi();
    server.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  WiFiClient client = server.available();
  if(!client)
      return;

  //Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();

   // Match the request
  int val = -1; // We'll use 'val' to keep track of both the
                // request type (read/set) and value if set.
  if (req.indexOf("/led/0") != -1)
    val = 0; // Will write LED low
  else if (req.indexOf("/led/1") != -1)
    val = 1; // Will write LED high
  else if (req.indexOf("/read") != -1)
    val = -2; // Will print pin reads
  // Otherwise request will be invalid. We'll say as much in HTML

  //Set the test led 

  if(val >= 0 )
    digitalWrite(TEST_LED,val);

  client.flush();

  //Response
   String s = "HTTP/1.1 200 OK\r\n";
  s += "Content-Type: text/html\r\n\r\n";
  s += "<!DOCTYPE HTML>\r\n<html>\r\n";
  // If we're setting the LED, print out a message saying we did
  if (val >= 0)
  {
    s += "LED is now ";
    s += (val)?"on":"off";
    digitalWrite(TEST_LED,(val==1?HIGH:LOW));
  }
  else if (val == -2)
  {; // If we're reading pins, print out those values:
   /* s += "Analog Pin = ";
    s += String(analogRead(ANALOG_PIN));
    s += "<br>"; // Go to the next line.
    s += "Digital Pin 12 = ";
    s += String(digitalRead(DIGITAL_PIN));*/
  }
  else
  {
    s += "Invalid Request.<br> Try /led/1, /led/0, or /read.";
  }
  s += "</html>\n";

  // Send the response to the client
  client.print(s);
  delay(1);
  Serial.println("Client disonnected"); 
// The client will actually be disconnected 
  // when the function returns and 'client' object is detroyed
}

void setupWifi(){
    
    if (!SPIFFS.begin()) {
        Serial.println("Failed to mount file system");
        return;
    }
    if(!loadConfig()){
        Serial.println("WiFi details not found");
        WiFi.mode(WIFI_AP);
        WiFi.softAP(espAP,espPass);
        saveConfig();
    }else {
     // Connect to WiFi
        WiFi.mode(WIFI_STA);
        WiFi.begin(ssid, password);
        while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
   }
}

void initHW(){
    Serial.begin(115200);
    pinMode(TEST_LED,OUTPUT);
    digitalWrite(TEST_LED,LOW);
}
bool configTester(){
   if (!SPIFFS.begin()) {
    Serial.println("Failed to mount file system");
    return false;
  }


  if (!saveConfig()) {
    Serial.println("Failed to save config");
  } else {
    Serial.println("Config saved");
  }

  if (!loadConfig()) {
    Serial.println("Failed to load config");
  } else {
    Serial.println("Config loaded");
  }

}
bool loadConfig(){
    File configFile = SPIFFS.open("/wifiConfig.json","r");
    if(!configFile){
      Serial.println("Config file not found\n");
      return false;
    }
    size_t size = configFile.size();
    if (size > 1024) {
      Serial.println("Config file size is too large");
      return false;
    }
     // Allocate a buffer to store contents of the file.
  std::unique_ptr<char[]> buf(new char[size]);

  // We don't use String here because ArduinoJson library requires the input
  // buffer to be mutable. If you don't use ArduinoJson, you may as well
  // use configFile.readString instead.
  configFile.readBytes(buf.get(), size);

  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& json = jsonBuffer.parseObject(buf.get());

  if (!json.success()) {
    Serial.println("Failed to parse config file");
    return false;
  }

  const char* ssid_json = json["ssid"];
  const char* password_json = json["password"];

  // Real world application would store these values in some variables for
  // later use.

  Serial.print("Loaded ssid ");
  Serial.println(ssid_json);
  Serial.print("Loaded password ");
  Serial.println(password_json);
  return true;
}

bool saveConfig() {
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& json = jsonBuffer.createObject();
  json["ssid"] = "TheAvatar";
  json["password"] = "ghar*123#";

  File configFile = SPIFFS.open("/wifiConfig.json", "w");
  if (!configFile) {
    Serial.println("Failed to open config file for writing");
    return false;
  }

  json.printTo(configFile);
  return true;
}


