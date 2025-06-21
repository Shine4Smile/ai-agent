package com.simple.aiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        String cmd = "cmd.exe /c dir";
//        String cmd = "java -version";
        String res = terminalOperationTool.executeTerminalCommand(cmd);
        Assertions.assertNotNull(res);
    }
}