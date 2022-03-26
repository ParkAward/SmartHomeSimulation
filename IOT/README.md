## 🦾IOT
>이 코드는 ESP8266 기준으로 만들어 졌습니다.
```ino
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
```
>센서 데이터를 받아와 mqtt_publish에 넣어주세요
```ino
void loop() {
  
  mqtt_publish("--Message--");
}
```
>메세지를 보내는 방식은 ClientCode + | + Message 입니다.<br />
>보안성과 필터를 위해 사용됩니다.<br/>
>ClientCode가 유효하지 않는다면 서버에서 거부합니다.
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
  delay(600000); //10분 단위
}

```
