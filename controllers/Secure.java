package controllers;

import cn.bran.play.JapidController;
import play.mvc.Before;

public class Secure extends JapidController {

    public final static String SESSION_LOGIN_KEY = "islogin";
    public final static String SESSION_USER_ID_KEY = "USER_ID";
    public final static String COOKIE_EMAIL = "spruce_email";
    public final static String COOKIE_USER = "spruce_USER";
    public final static String COOKIE_PASSWORD = "spruce_pwd";

    /**
     * <pre>
     * IE内核：
     *  IE10： Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
     *  QQ浏览器： Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; QQBrowser/7.3.11251.400)
     *  IETest IE8: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; QQDownload 732; BTRS35926;  Embedded Web Browser from: http://bsalsa.com/; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)
     *  2345浏览器：Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)
     *  Webkit内核：
     *  百度浏览器： Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 BIDUBrowser/2.x Safari/537.31
     *  Chrome: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36
     *  Safari for Windows：Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2
     *  
     *  FireFox: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0
     * 
     * </pre>
     */

  
    @Before(unless = { "UserCenter.login", "UserCenter.logout", "UserCenter.logon", "logoff",
            "UserCenter.signup", "UserCenter.register", "login", "UserCenter.forgotPassword" })
    static void checkAuthentification() {
        //
        String cpage = request.url;
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
            String url = (cpage == null || "".equals(cpage)) ? "" : "?cpage=" + cpage;

            redirect("/login" + url);

        }
    }

}
