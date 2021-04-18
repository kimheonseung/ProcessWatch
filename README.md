### Watching Process with Java Native Library
- Watching a specific process for a set period of time and execute command if process is not running.


### arguments
```console
$JAVA_HOME/ProcessWatch -jar ProcessWatch.jar "process명" "process 실행 확인 명령어" "실행할 명령어" "주기(분)" &

# 5분마다 'mysqld' 라는 프로세스명을 'ps -e | grep mys' 명령어로 찾아서 실행 중이 아니라면 'systemctl start mysqld' 명령어를 수행
$JAVA_HOME/ProcessWatch -jar ProcessWatch.jar "mysqld" "ps -e | grep mys" "systemctl start mysqld" 5 &
```
  
  
### flow
1. Start with main method in ProcessWatch class.
2. Set period cycle with arguments.
3. Check Process.
4. Execute command if process is not running.
