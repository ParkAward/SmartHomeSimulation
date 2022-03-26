<div align="center" >
<img src="https://user-images.githubusercontent.com/41848169/144705619-fbc24eda-49af-42c7-b92a-f6119af9f8c9.png"/>
</div>


<div>
<img height="450px" align="right" src="https://user-images.githubusercontent.com/41848169/143197305-80f55685-d127-46a0-8f59-11e2f45f09e9.gif" alt="앱 실황 gif"/>
<img align="left" style="padding:20px;" src="https://user-images.githubusercontent.com/41848169/144531870-538354a0-dc84-4221-ae7b-1e328f4a4a6c.png" height="50" alt="react icon"/>
<h2> :iphone: MirrorContol </h2>

<pre>
서버 전용 안드로이드 어플리케이션입니다.

SkyMoon는 Mirror 컨트롤을 제어하는 페이지
SunLite는 Data핸들링을 의미합니다.

Mirror API 키가 존재하며 해당 키와 주소(서버 아이피)가 일치하면 페이지가 넘어가도록 설정했습니다
IOT는 제공되는 Client_Code 가 일치하면 Data Stream과 
보낸 데이터들을 그래프화 하여 보여줄 것입니다.
</pre>
</div>

<h2>설치 주소</h2>

```
https://github.com/Haiol/MirrorContol.git
```
<br/>

<h2>Implements</h2>

```gradle
    implementation 'com.github.mohammadatif:Animatoo:master'
    implementation 'com.android.volley:volley:1.2.1'
    implementation 'com.github.magic-goop:tag-sphere:1.0.0'
    implementation 'com.github.PhilJay:MPAndroidChart:v3.0.2'

```
<br/>

## 디렉토리 구조
<h4>Java </h3>

```bash
mirrorcontrol
    ├─common
    │      ErrorFragment.java
    ├─data
    │      DataActivity.java
    │      DataActViewAdapter.java
    │      DataGraphFragment.java
    │      DataSphereFragment.java
    │      SensorDataFormat.java
    ├─intro
    │      SplashActivity_Moon.java
    ├─login
    │      LoginActivity.java
    ├─main
    │  │  MainActivity.java
    │  │  MainActViewAdapter.java
    │  ├─control
    │  │      ControlFragment.java
    │  │      ControlViewModel.java
    │  │      ControlVolleyRequest.java
    │  └─topics
    │          ListTopicItem.java
    │          TopicsFragment.java
    │          TopicsListAdapter.java
    │          TopicsViewModel.java
    └─sqllite
            DataBases.java
            MQDbOpenHelper.java

```
<h4>XML </h3>

```bash
┌─layout
│    activity_data.xml
│    activity_login.xml
│    activity_main.xml
│    activity_splash_moon.xml
│    app_bar_main.xml
│    content_main.xml
│    control_fragment.xml
│    edit_layout.xml
│    fragment_data_graph.xml
│    fragment_data_sphere.xml
│    fragment_error.xml
│    nav_header_main.xml
│    topics_fragment.xml
└    topic_list_item.xml


```
