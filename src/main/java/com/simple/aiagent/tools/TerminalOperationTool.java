package com.simple.aiagent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 终端操作工具
 */
public class TerminalOperationTool {

    @Tool(description = "Execute a command in the terminal")
    public String executeTerminalCommand(@ToolParam(description = "Command to execute in the terminal") String command) {
        StringBuilder output = new StringBuilder();
        Process process = null;
        try {
            // 判断操作系统，组装shell命令
            String os = System.getProperty("os.name").toLowerCase();
//            String[] fullCommand;
            ProcessBuilder processBuilder;
            if (os.contains("win")) {
                // Windows下通过cmd.exe执行，支持内置命令如dir
//                fullCommand = new String[]{"cmd.exe", "/c", command};
                processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            } else {
                // Linux/Mac下通过sh执行
//                fullCommand = new String[]{"/bin/sh", "-c", command};
                processBuilder = new ProcessBuilder("/bin/sh", "/c", command);
            }
//            process = Runtime.getRuntime().exec(fullCommand);
            process = processBuilder.start();

            // 读取标准输出流
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            // 读取标准错误流（如java -version等命令输出）
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("Command execution failed with exit code: ").append(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            output.append("Error executing command: ").append(e.getMessage());
        }
        return output.toString();
    }
}
