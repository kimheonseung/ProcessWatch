package com.devheon.processwatch;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 * Description :
 *     특정 시간(cycleMin)동안 특정 프로세스명(targetProcess)의 실행 여부를 감시
 *     실행 중이 아니면, 특정 명령어(processCheckCommand)를 수행하는 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-12-19
 * </pre>
 */
public class ProcessWatch {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String PROCESS_NAME = "ProcessWatch";

    private final String LOG_CYCLE_DATE      = String.format("%25s", "[WATCH TIME] ");
    private final String LOG_WATCH_CYCLE     = String.format("%25s", "[WATCHDOG CYCLE(MIN)] ");
    private final String LOG_TARGET_NAME     = String.format("%25s", "[TARGET PROCESS NAME] ");
    private final String LOG_CHECK_COMMAND   = String.format("%25s", "[COMMAND] ");
    private final String LOG_COMMAND_RESULT  = String.format("%25s", "[RESULT] ");
    private final String LOG_ERROR           = String.format("%25s", "[EXECUTE ERROR] ");
    private final String LOG_ALIVE_STATUS    = String.format("%25s", "[PROCESS ALIVE STATUS] ");
    private final String LOG_EXECUTE_COMMAND = String.format("%25s", "[EXECUTE COMMAND] ");

    public static void main(String[] args) {
        /* $JAVA_HOME/ProcessWatch -jar ProcessWatch.jar "ProcessName" "ps -e | grep ProcessName" "실행할명령어" 주기(min) & */
        new ProcessWatch(args[0], args[1], args[2], Integer.valueOf(args[3]));
    }

    public ProcessWatch(final String targetProcessName, final String processCheckCommand, final String executeCommand, int cycleMin) {
        new Timer(this.getClass().getName())
                .scheduleAtFixedRate(
                        new TimerTask() {
                            @Override
                            public void run() {
                                System.out.println(System.lineSeparator());
                                System.out.println(System.lineSeparator());

                                System.out.println(LOG_CYCLE_DATE + sdf.format(new Date()));
                                System.out.println(LOG_WATCH_CYCLE + cycleMin);

                                try {

                                    if(!isProcessAlive(targetProcessName, processCheckCommand))
                                        executeCommand(executeCommand);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Date(),
                        6000L * (long) cycleMin
                );
    }

    /**
     * <pre>
     * Description
     *     특정 프로세스가 실행 중인지 확인하는 메소드
     * ===============================================
     * Parameters
     *     String targetProcessName
     *     String processCheckCommand
     * Returns
     *     boolean 실행 중이면 true 반환
     * Throws
     *     Exception 프로세스명 조회 실패
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-12-19
     * </pre>
     */
    private boolean isProcessAlive(String targetProcessName, String processCheckCommand) throws Exception {
        boolean isAlive = false;

        CommandExecutor commandExecutor = null;

        try {
            System.out.println(LOG_TARGET_NAME + targetProcessName);
            System.out.println(LOG_CHECK_COMMAND + processCheckCommand);

            commandExecutor = new CommandExecutor(processCheckCommand);

            BufferedReader bufferedReaderInput = commandExecutor.getInputStream();
            String input = null;

            while( (input = bufferedReaderInput.readLine()) != null ) {
                System.out.println(LOG_COMMAND_RESULT + input);

                if(input.contains(PROCESS_NAME) || !input.contains(targetProcessName))
                    continue;

                isAlive = true;
                break;
            }

            BufferedReader bufferedReaderError = commandExecutor.getErrorStream();
            String error = null;

            while( (error = bufferedReaderError.readLine()) != null )
                System.out.println(LOG_ERROR + error);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            commandExecutor.closeStream();
            commandExecutor.destroy();
        }

        System.out.println(LOG_ALIVE_STATUS + isAlive);
        return isAlive;
    }

    /**
     * <pre>
     * Description
     *     명령어 수행 객체 호출
     * ===============================================
     * Parameters
     *     String command
     * Returns
     *     
     * Throws
     *     Exception
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-12-19
     * </pre>
     */
    private void executeCommand(String command) throws Exception {
        System.out.println(LOG_EXECUTE_COMMAND + command);
        new CommandExecutor(command);
    }
}
