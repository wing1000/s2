package fengfei.ucm.service.impl;

import fengfei.ucm.service.PingService;
import fengfei.ucm.service.cmd.Command;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class PingServiceImpl implements PingService {

    private Map<String, Command> commands = new HashMap<>();

    @Override
    public String ping() {
        return "pong";
    }

    @Override
    public String ping(String cmd) {
        Command command = commands.get(cmd);
        if (command == null) {
            throw new IllegalArgumentException(cmd + " command is not registered.");
        }
        StringWriter writer = new StringWriter();
        try {
            command.execute(writer);
        } catch (Exception e) {
            throw new RuntimeException("execute error for command: " + cmd, e);
        }
        return writer.toString();
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }
}
