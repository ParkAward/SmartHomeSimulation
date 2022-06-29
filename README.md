
<div align="center">
 <h4>비디오🎞</h4>
 

[![MQTTMirrorServer](http://img.youtube.com/vi/t7HeZgF0mY8/0.jpg)](https://youtu.be/t7HeZgF0mY8?t=0s)
</div>
 
 --------------

◼ MQTT broker 서버를 통해 센서데이터를 수집하여 API를 제공하고 MagicMirror(RPI)와 안드로이드를 통해 시각화 합니다. <br/>
◼수집된 데이터를 시각화하고 단말기(안드로이드 어플)을 통해 MagicMirror를 제어합니다.



<h2>🗺구상도</h2>
<div align="center">
<img    width="50%" src="https://user-images.githubusercontent.com/41848169/144437047-46997bfb-7e39-452c-8f07-6e0c760c06bc.jpg" alt="전체적인 구상도"/>
</div>
<pre>
 이 프로젝트는 한 WiFi네트워크 안에서 진행됩니다.
 이후에 WiFi관련 IOT와 같은 제품등을 테스트를 진행하기 쉽게
 환경을 조성해주는 홈 IOT네드워크시스템 입니다.
 <br/>
 Arduino <-MQTT->  RPI <-HTTP-> Android 
 서버 구성이 잘 되었는지 판단하기 위해서  DHT22 온습도 센서와 Wemos D1(와이파이 모듈)을 사용했습니다.
 <br />
 RPI에는 <a href="https://magicmirror.builders/">MagicMirror Module</a>, <a href="https://mosquitto.org/">Ecilpes Mosquitto<a/>를 사용했습니다
</pre>

<h3>이미지 사용 출저</h3>
Mobile 일러스트 : https://notefolio.net/sukwontoto/233542
