# Task 1 - index.html 응답
## 학습 목표
- HTTP를 학습하고 학습 지식을 기반으로 웹 서버를 구현한다. 
- Java 멀티스레드 프로그래밍을 경험한다. 
- IDE에서 디버깅 도구를 사용하는 방법을 익힌다. 
- 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.

## 기능요구사항
### 정적인 html 파일 응답
http://localhost:8080/index.html 로 접속했을 때 src/main/resources/static 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

## HTTP Request 내용 출력
서버로 들어오는 HTTP Request의 내용을 읽고 적절하게 파싱해서 로거(log.debug)를 이용해 출력한다.

## 프로젝트 분석
단순히 요구사항 구현이 목표가 아니라 프로젝트의 동작 원리에 대해 파악한다.

## 기존 코드의 구조 변경
- 자바 스레드 모델에 대해 학습한다. 버전 별로 어떤 변경점이 있었는지와 향후 지향점에 대해서도 학습해 본다. 
- 자바 Concurrent 패키지에 대해 학습한다. 
- 기존의 프로젝트는 Java Thread 기반으로 작성되어 있다. 이를 Concurrent 패키지를 사용하도록 변경한다.

## OOP와 클린 코딩
- 주어진 소스 코드를 기반으로 기능요구사항을 만족하는 코드를 작성한다. 
- 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.
- 웹 리소스 파일은 제공된 파일을 수정해서 사용한다. (직접 작성해도 무방함)

## 제한 사항
- build.gradle에 초기에 주어진 패키지 외 외부 패키지 의존성을 최소화한다.
- JDK의 nio는 사용하지 않는다.
- Lombok은 사용하지 않는다. 
- MVC와 관련된 Naming은 사용하지 않는다(Controller, Service, DTO, Repository 등)

## 추가 요구 사항
학습 정리
학습한 내용을 README.md와 GitHub 위키를 이용해서 기록한다.

## 추가학습거리
### HTTP Request Header 예
```json
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```

### 모든 Request Header 출력하기 힌트
- InputStream => InputStreamReader => BufferedReader
- BufferedReader.readLine() 메소드 활용해 라인별로 http header 읽는다.
- http header 전체를 출력한다.

> 힌트를 통해 어떻게 접근해야 할지 모르겠다면 다음 동영상을 통해 모든 Request Header를 출력하는 과정을 볼 수 있다. 동영상을 통해 힌트를 얻은 후 다음 단계 실습을 진행한다.

### Request Line에서 path 분리하기 힌트
- Header의 첫 번째 라인에서 요청 URL(위 예제의 경우 /index.html)을 추출한다.
  - String[] tokens = line.split(" "); 활용해 문자열을 분리할 수 있다.
- 구현은 별도의 유틸 클래스를 만들고 단위 테스트를 만들어 진행할 수 있다.

### path에 해당하는 파일 읽어 응답하기 힌트
- 요청 URL에 해당하는 파일을 static 디렉토리에서 읽어 전달하면 된다.
- 구글에서 “java files readallbytes”로 검색해 파일 데이터를 byte[]로 읽는다.
```java
byte[] body = Files.readAllBytes(new File("./static" + url).toPath()); 
```
 


