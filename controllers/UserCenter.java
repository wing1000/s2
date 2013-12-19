package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.utils.UUID;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import fengfei.ucm.repository.UserRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import japidviews.Application.ForgotPassword;
import japidviews.Application.Login;
import japidviews.Application.Signup;
import japidviews.Application.SignupDone;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.HtmlEmail;
import play.Logger;
import play.libs.Mail;
import play.modules.router.Any;

import java.util.HashMap;
import java.util.Map;

public class UserCenter extends Admin {

    static UserRepository userService = new SqlUserRepository();
    static Base64 base64 = new Base64();

    public static void login() {
        String e = params.get("email");
        String p = params.get("password");
        String rem = params.get("remember");
        rem = "1";
        String cpage = params.get("cpage");
        if (e == null || p == null) {
            flash.put("cpage", cpageUrl(request.querystring));
            System.out.println(flash.get("cpage"));
            throw new JapidResult(new Login().render());
        }
        validation.required(e);
        validation.required(p);

        if (validation.hasErrors()) {
            flash.put("email", e);
            flash.put("password", e);
            redirect(request.url);

        }

        boolean isLogin = verify(e, p, rem);

        if (isLogin) {
            if (cpage == null || "".equals(cpage)) {
                // Usher.index();
                redirect("/");
            } else {
                redirect(cpage);
            }

        } else {
            flash.put("error", i18n("login.error"));
            flash.put("email", e);
            redirect(request.url);
            // throw new JapidResult(new Login().render());
        }

    }

    public static String cpageUrl(String url) {
        if (url == null || "".equals(url)) {
            return "";
        } else {
            return "?" + url;
        }
    }

    public static void logout() {
        session.clear();
        response.removeCookie(COOKIE_EMAIL);
        response.removeCookie(COOKIE_PASSWORD);
        throw new JapidResult(new Login().render());
    }

    public static void logon() {
        boolean isLogin;
        Map<String, Object> rs = new HashMap<>();
        try {

            String e = params.get("email");
            String p = params.get("password");
            String rem = params.get("remember");
            isLogin = verify(e, p, rem);
            rs.put("isLogin", isLogin);
            rs.put("username", e);

        } catch (Exception e) {
            Logger.error(e, "logon error.");
            rs.put("isLogin", false);

        }
        renderJSON(rs);
    }

    public static void logoff() {
        try {
            session.clear();
            response.removeCookie(COOKIE_EMAIL);
            response.removeCookie(COOKIE_PASSWORD);
            renderText(true);
        } catch (Exception e) {
            Logger.error(e, "logoff error.");
            renderText(false);

        }
    }

    private static boolean verify(String email, String passoword,
                                  String remember) {
        try {
            String e = email.trim();
            String p = passoword.trim();

            // if (e == null) {
            // Http.Cookie cookie = request.cookies.get(COOKIE_EMAIL);
            // e = cookie == null ? e : cookie.value;
            // }
            // if (p == null) {
            // Http.Cookie cookie = request.cookies.get(COOKIE_PASSWORD);
            // p = cookie == null ? p : cookie.value;
            // }
            // String md5_pwd = BASE64.encrypt(p);
            byte[] pwd_bs = base64.encodeBase64(p.getBytes(), false);
            String md5_pwd = new String(pwd_bs);
            boolean isLogin = session.get(SESSION_LOGIN_KEY) != null;
            Logger.debug("session login info: %s", true);
            if (!isLogin) {
                UserPwd user = userService.getUserPwd(email, md5_pwd);
                Logger.debug("database user info: %s",user);
                if (user != null) {
                    session.put(SESSION_LOGIN_KEY, true);
                    session.put(SESSION_USER_ID_KEY, user.getIdUser());
                    session.put(SESSION_USER_NAME_KEY, user.getUserName());
                    // if (remember != null && "1".equals(remember)) {
                    // response.setCookie(COOKIE_EMAIL, e, "30d");
                    // response.setCookie(COOKIE_PASSWORD, p, "30d");
                    // }
                    User info = userService.getUser(user.getIdUser());
                    if (info != null && info.getNiceName() != null
                            && !"".equals(info.getNiceName())) {
                        session.put(SESSION_USER_NAME_KEY, info.getNiceName());
                    }

                    return true;
                }
            } else {
                return true;
            }
            return false;

        } catch (Exception e) {
            Logger.error(e, "verify user  error.");
            return false;
        }

    }

    public static void signup() {
        throw new JapidResult(new Signup().render(new UserPwd()));
    }

    @RequestMapping("/verify/email")
    public static void verifyEmail(Integer id, String code) {

    }

    @RequestMapping("/recover")
    public static void forgotPassword() {
        throw new JapidResult(new ForgotPassword().render());
    }

    public static void register() {
        System.out.println(params.allSimple());
        String username = params.get("username");
        String email = params.get("email");
        String password = params.get("password");
        String confirm_password = params.get("confirm_password");
        String cpage = params.get("cpage");
        if (!password.equals(confirm_password)) {
            flash.put("error", i18n("signup.password.mismatch"));
            throw new JapidResult(new Signup().render(new UserPwd(email,
                    username, null)));
        }
        if (password.getBytes().length > 20) {
            flash.put("error", i18n("signup.password.max"));
            throw new JapidResult(new Signup().render(new UserPwd(email,
                    username, null)));
        }

        try {
            byte[] pwd_bs = base64.encodeBase64(password.getBytes(), false);
            String md5_pwd = new String(pwd_bs);// BASE64.encrypt(password);
            UserPwd up = new UserPwd(email, username, md5_pwd);
            //
            String vc = UUID.randomB36UUID();
            Base64 base64 = new Base64();
            byte[] bs = base64.encodeBase64(vc.getBytes(), false);
            String verifyCode = new String(bs);
            up.verify = verifyCode;
            //
            int updated = userService.addUserPwd(up);
            if (updated == -3) {
                flash.put("error", i18n("signup.name.and.email.exist"));
            } else if (updated == -2) {
                flash.put("error", i18n("signup.user.exist"));
            } else if (updated == -1) {
                flash.put("error", i18n("signup.email.exist"));
            } else if (updated > 0) {
                session.put(SESSION_LOGIN_KEY, true);
                session.put(SESSION_USER_ID_KEY, updated);
                session.put(SESSION_USER_NAME_KEY, up.getUserName());
                if (cpage == null || "".equals(cpage)) {
                    redirect("/");
                } else {
                    redirect(cpage);
                }

                HtmlEmail semail = new HtmlEmail();
                semail.setFrom("spruce_system@163.com");
                semail.addTo(up.email);
                semail.setSubject("Confirm your email");
                semail.setHtmlMsg(String.format(mail, up.userName, up.idUser,
                        verifyCode));
                // semail.setTextMsg("Your email client does not support HTML, too bad :(");
                Mail.send(semail);

            } else {
                flash.put("error", i18n("server.inner.error"));

            }

            if (flash.get("error") == null) {
                throw new JapidResult(new SignupDone().render(new UserPwd(
                        email, username, null)));
            } else {
                throw new JapidResult(new Signup().render(new UserPwd(email,
                        username, null)));

            }

        } catch (Exception e) {
            Logger.error(e, "register user error.");
            flash.put("error", i18n("server.inner.error"));
            throw new JapidResult(new Signup().render(new UserPwd(email,
                    username, null)));
        }

    }

    final static String mail = "Hey %s ,\n welcome to Spruce! Before you get started, please verify your email address below:\n  <a href=\"/verify?id=%s&code=%s\">click to verify</a>.\n ";

}
