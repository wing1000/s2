package controllers;

import cn.bran.play.JapidController;
import play.Logger;
import play.mvc.Catch;
import play.mvc.Finally;

public class ExceptionCatchController extends JapidController {

	@Catch(Exception.class)
	public static void logException(Throwable throwable) {
		Logger.error("Server error: ", throwable);
        Admin.flashError();
	}

	@Finally
	static void log() {
		Logger.info("Responsed.");
	}
}
