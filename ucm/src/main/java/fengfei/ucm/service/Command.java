package fengfei.ucm.service;

import java.io.Writer;

public interface Command {

	void execute(Writer writer) throws Exception;

}
