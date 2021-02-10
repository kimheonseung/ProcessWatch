package com.devheon.processwatch.constant;

/**
 * <pre>
 * Description :
 *     운영체제에 따른 명령어
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-12-19
 * </pre>
 */
public enum Command {
    WINDOWS_COMMAND_1("cmd"),
    WINDOWS_COMMAND_2("/c"),
    UNIX_COMMAND_1("/bin/bash"),
    UNIX_COMMAND_2("-c");

    private String value;
    private Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
