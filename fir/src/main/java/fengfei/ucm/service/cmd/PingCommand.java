package fengfei.ucm.service.cmd;

import java.io.Writer;

public class PingCommand implements Command {

    @Override
    public void execute(Writer writer) throws Exception {
        writer.write("pong");

    }

}
