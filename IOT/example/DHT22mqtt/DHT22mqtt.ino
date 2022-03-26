#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "DHT.h"


#define DHTPIN D4 //DHT11을 D4번핀에 연결한다.

#define DHTTYPE DHT22

const char* ssid = "WiFi";
const char* password = "WiFi_PW";
const char* mqtt_server = "MQTTServerIP";
const String client_Code = "ArduinoNick";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[50];
String packet;
float Humi,Temp;

DHT dht(DHTPIN, DHTTYPE);



void setup() {
  Serial.begin(115200);
  dht.begin();
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  // Switch on the LED if an 1 was received as first character
  if ((char)payload[0] == '1') {
    digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
    // but actually the LED is on; this is because
    // it is acive low on the ESP-01)
  } else {
    digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
  }

}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP8266Client")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("outTopic", "hello world");
      // ... and resubscribe
      client.subscribe("inTopic");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}


float getHumi() { //DHT11 습도를 받아오는 함수
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
  }

  Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print(" %\t");
  return(h);
}

float getTemp() {//DHT11 온도를 받아오는 함수
  
  float t = dht.readTemperature();

  Serial.print("Temperature: ");
  Serial.print(t);
  Serial.println(" *C ");

  return(t);
}

void mqtt_publish(float Humi, float Temp){
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;

    packet = client_Code+"| humi:"+String(Humi)+",temp:"+String(Temp); 
    packet.toCharArray(msg, 50);
    Serial.print("Publish message: ");
    Serial.println(msg);
    client.publish("Sensor/Humi_Temp", msg);
  }
  delay(600000); 
}

void loop() {
  Humi = getHumi();
  Temp = getTemp(); 

  mqtt_publish(Humi, Temp);
}
