## ๐ฆพIOT
>์ด ์ฝ๋๋ ESP8266 ๊ธฐ์ค์ผ๋ก ๋ง๋ค์ด ์ก์ต๋๋ค.
```ino
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
```
>์ผ์ ๋ฐ์ดํฐ๋ฅผ ๋ฐ์์ mqtt_publish์ ๋ฃ์ด์ฃผ์ธ์
```ino
void loop() {
  
  mqtt_publish("--Message--");
}
```
>๋ฉ์ธ์ง๋ฅผ ๋ณด๋ด๋ ๋ฐฉ์์ ClientCode + | + Message ์๋๋ค.<br />
>๋ณด์์ฑ๊ณผ ํํฐ๋ฅผ ์ํด ์ฌ์ฉ๋ฉ๋๋ค.<br/>
>ClientCode๊ฐ ์ ํจํ์ง ์๋๋ค๋ฉด ์๋ฒ์์ ๊ฑฐ๋ถํฉ๋๋ค.
```ino
void mqtt_publish(String Message){
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;

    packet = client_Code+"|"+Message; 
    packet.toCharArray(msg, 50); 
    Serial.print("Publish message: ");
    Serial.println(msg);
    client.publish("Message", msg);
  }
  delay(600000); //10๋ถ ๋จ์
}

```
