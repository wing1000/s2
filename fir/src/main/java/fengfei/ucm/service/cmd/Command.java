package fengfei.ucm.service.cmd;

import java.io.Writer;

public interface Command {

    void execute(Writer writer) throws Exception;

}
