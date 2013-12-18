package controllers;

import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import play.Logger;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Finally;

public class SecureForJson extends Secure {

	@Before(unless = { "UserCenter.login", "UserCenter.logout",
			"UserCenter.logon", "logoff", "UserCenter.signup",
			"UserCenter.register" })
	static void checkAuthentification() {
		//

		// Map<String, Cookie> cookies = request.cookies;
		// for (Entry<String, Cookie> ck : cookies.entrySet()) {
		// System.out.printf("cookie key=%s, value=%s , age=%s \n",
		// ck.getKey(), ck.getValue().value, ck.getValue().maxAge);
		//
		// }
		//
		// System.out.println("session: " + session.all());
		// System.out.println("session: " + session.getId());
		// System.out.println();
		if (session.get(SESSION_LOGIN_KEY) == null) {
			// Http.Cookie cookie = cookies.get(COOKIE_EMAIL);
			// Http.Cookie cookie = cookies.get(COOKIE_USER);
			// loginIndex();
			// System.out.println("-----------------login1-----------------");
			// throw new JapidResult(new Login().render());
			Done done = new Done(Admin.i18n("login.json.message.before")
					+ "<a href=\"/login\">"+Admin.i18n("login.json.message.login")+"</a>!",
					Status.Fail);
			renderJSON(done);

		}
	}
    @Catch(Exception.class)
    public static void logException(Throwable throwable) {
        Logger.error("Server error: ", throwable);
        Admin.renderErrorJSON();
    }

    @Finally
    static void log() {
        Logger.info("Ajax Responsed.");
    }

}
