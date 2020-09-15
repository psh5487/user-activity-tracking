# user-activity-tracking
2019 Winter Naver Hackday - 사용자 동선 트래킹 툴 개발

팀원 : (멘티) 박소현, 박영우, 김수아 / (멘토님) 정성용 

## Goal
쇼핑 사이트에서 활동 중인 사용자 로그를 수집하여, 사용자 동선을 표현하는 툴 만들기

## Stack
### Front
- Javascript
- React

### Backend
- Spring Boot
- JPA
- MySQL

## 사용자 로그 수집
### 어떤 로그를 받을까?

- 행동 로그(=액션 로그)에 집중
<img width="700" src="https://user-images.githubusercontent.com/26567880/92554195-b6463e00-f29f-11ea-9303-8941c61a891b.png">

- 사용자 동선 탐색
<img width="1183" src="https://user-images.githubusercontent.com/26567880/92554404-3cfb1b00-f2a0-11ea-997f-930b252b62b2.png">

### 행동 로그 구조 설계
<img width="500" src="https://user-images.githubusercontent.com/26567880/92554949-839d4500-f2a1-11ea-8342-8e60f17f41df.png">

- 카테고리 클릭, 상품 클릭, 스크롤 횟수 로그를 받아, 각 액션에 대해 카테고리 아이디, 상품 아이디, 스크롤 횟수 정보가 담기도록 설계

|Column|Explanation   |
|------|--------------|
|id           |Auto Increment id, PK|
|user_id      |사용자 id|
|action       |category_click, content_click, scroll 中 하나가 들어감|
|action_detail|categoryId, product Id, scroll 횟수 中 하나가 들어감|
|timestamp    |action 발생 시간|
|start_point  |동선의 한 패턴 시작점 여부|

## 행동 로그 수집 구현
### 사용자 동선의 한 패턴(시작과 끝) 어떻게 나눌 것인가
- 패턴 구분 기준은 다양. 많은 고민 필요.
- 사용자의 액션이 10분 이상 발생하지 않으면, 한 패턴이 끝난 것으로 간주
- 사이트를 이탈해도, 10분 안에 액션이 발생하면, 여전히 한 패턴 안에 속한 것으로 간주
- Cookie 사용하기로!

<img width="576" src="https://user-images.githubusercontent.com/26567880/92555748-53ef3c80-f2a3-11ea-9bc1-b2f0051923d1.png">

### Front - React Mock Up 수정
- 사용자 액션이 발생하면, 유효 시간 10분짜리 쿠키("userState" : "Shopping")를 발급
- 유효 시간이 끝나면, 쿠키 사라짐
- 액션 발생 시, 쿠키가 없다면, 새로운 패턴으로 간주. startPoint 에 true 값을 넣어줌
- axios로 사용자 로그 서버로 보내기

### Server - 사용자 로그 DB에 저장
- API

|Method|URI|
|------|---|
|POST  |http://localhost:8080/trackingUser/api|

- CORS 문제 해결

`@CrossOrigin(origins = {"http://localhost:3000" })`

## 사용자 동선 표현
### 사용자 동선 그래프의 사용성
- 사용자 액션 흐름 파악
- 어느 액션이 많이 발생하는지 파악
- UI 개선, 경로 분석, Bottleneck/이탈 구간 파악 등에 대한 인사이트 제공

### 구현하기
1. 서버로부터, 해당 사용자의 액션 로그 데이터를 받아옴

|Method|URI|
|------|---|
|GET   |http://localhost:8080/trackingUser/api/{userId}

- 액션 로그 데이터는 start_point를 기준으로 패턴 별로 분리되어 있음
<img width="507" src="https://user-images.githubusercontent.com/26567880/92559066-cfa0b780-f2aa-11ea-8113-8f0140adffd9.png">

```
[
  {
    "activityLogs":[
      {"id":"1", "user_id":"shopad", "action":"scroll", "action_detail":"0", "timestamp":"2019-11-22T04:23:22.939+0000", "start_point":"true"},
      {"id":"2", "user_id":"shopad", "action":"category_click", "action_detail":"HOT", "timestamp":"2019-11-22T04:23:22.426+0000", "start_point":"false"},
      {"id":"3", "user_id":"shopad", "action":"content_click","action_detail":"기모가디건","timestamp":"2019-11-22T04:23:22.027+0000", "start_point":"false"},
      {"id":"4", "user_id":"shopad", "action":"scroll", "action_detail":"0","timestamp":"2019-11-22T04:23:21.557+0000", "start_point":"false"}
    ]
  },
  {
    "activityLogs":[
      {"id":"5", "user_id":"shopad", "action":"scroll","action_detail":"1", "timestamp":"2019-11-22T04:23:21.134+0000", "start_point":"true",
      {"id":"6", "user_id":"shopad", "action":"content_click", "action_detail":"양털후드", "timestamp":"2019-11-22T04:23:18.400+0000", "start_point":"false",
      {"id":"7", "user_id":"shopad", "action":"category_click","action_detail":"HOT", "timestamp":"2019-11-22T04:23:18.400+0000", "start_point":"false"}
    ]
  },
  {
    "activityLogs":[
      {"id":"8", "user_id":"shopad", "action":"content_click", "action_detail":"라이트미디패딩", "timestamp":"2019-11-22T04:23:21.134+0000", "start_point":"true"},
      {"id":"9", "user_id":"shopad", "action":"category_click", "action_detail":"하의", "timestamp":"2019-11-22T04:23:18.400+0000", "start_point":"false"}
    ]
  }          
]
```

2. 받아온 데이터를 활용하여, Javascript 대쉬보드에 사용자 동선 그래프 그리기 

### Google Chart의 Word Tree 그래프 사용
- 자주 발생한 액션일수록 색깔이 진해지도록 커스터마이징
- 이전 노드 탐색 -> 재귀 함수 사용

<img width="811" src="https://user-images.githubusercontent.com/26567880/92558409-6bc9bf00-f2a9-11ea-8fba-6fb2864739bd.png">

## 핵데이를 통해서
### 서비스 관점
- 처음부터 Total 사용자 정보 수집에 집중하였는데, 한 명 사용자 정보 수집이 우선 되어야함을 알았다.
- 처음부터 Total에 포커스를 두면, 세부 분석, 개인에 대한 파악이 어렵다.
- 한 명 사용자 정보 수집에 집중하여도, 어차피 여러 사용자 정보를 통해 전체 정보도 추론 가능해진다.
- UI 설계에 있어 사용자 입장도 중요하지만, 광고주의 입장도 무시할 수 없다.

### 개발 관점
- 처음에는 DB에 마지막으로 저장된 timestamp를 가져와서 현재 timestamp와 계산을 하고자 했으나, 계산 시간보다 사용자 로그가 더 빠른 속도로 찍혀, 음수 시간이 저장되는 문제가 발생하였다.
- 그래서 빨리 Cookie 사용으로 방향을 틀었는데 잘 작동해서 다행이었다.
- NoSQL DB를 선택했어야한다는 아쉬움이 들었다. MySQL은 좋은 선택이 아니었다. 로그 수집을 MySQL에서 하면 DB가 터진다고 한다.
- React 상태 관리를 효율적으로 해보기 위해 Redux를 공부해보자.




















