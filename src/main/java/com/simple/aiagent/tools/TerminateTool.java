package com.simple.aiagent.tools;

import org.springframework.ai.tool.annotation.Tool;

/**
 * 终止工具（让自主规划智能体能够合理的中断）
 */
public class TerminateTool {

    @Tool(description = """  
            Terminate the interaction when the request is met OR if the assistant cannot proceed further with the task.  
            "When you have finished all the tasks, call this tool to end the work.  
            """)
    public String doTerminate() {
        return "任务结束";
    }
}
