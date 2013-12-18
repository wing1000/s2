package fengfei.ucm.service.command;

import java.io.Writer;

import fengfei.ucm.service.Command;

public class PingCommand implements Command {

	@Override
	public void execute(Writer writer) throws Exception {
		writer.write("pong");

	}

}
