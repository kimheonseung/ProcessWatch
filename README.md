# 프로세스 감시 프로그램
- Java로 명령어 실행 예제 코드 연습 - OpenJDK 8_242
- 특정 시간동안 특정 프로세스명의 실행을 감시하여 실행 중이 아니면, 특정 명령어를 수행하는 프로그램


### 실행 인자
```console
$JAVA_HOME/ProcessWatch -jar ProcessWatch.jar "process명" "process 실행 확인 명령어" "실행할 명령어" "주기(분)" &

# 5분마다 'mysqld' 라는 프로세스명을 'ps -e | grep mys' 명령어로 찾아서 실행 중이 아니라면 'systemctl start mysqld' 명령어를 수행
$JAVA_HOME/ProcessWatch -jar ProcessWatch.jar "mysqld" "ps -e | grep mys" "systemctl start mysqld" 5 &
```
  
  
### 흐름
1. ProcessWatch main 함수 호출
2. 실행 인자로 받은 시간마다 수행할 타이머 설정
3. 프로세스 체크 명령어 수행
4. 프로세스 실행 여부에 따라 특정 명령어를 수행하거나, 사이클 종료