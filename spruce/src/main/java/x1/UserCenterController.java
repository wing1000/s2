package x1;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Admin;
import fengfei.spruce.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fengfei.fir.utils.BASE64;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import fengfei.ucm.repository.UserRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import fengfei.web.authority.AuthorityHelper;

@Controller
public class UserCenterController extends Admin {

    UserRepository userService = new SqlUserRepository();

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView("login");

        String e = request.getParameter("email");
        String p = request.getParameter("password");
        String rem = request.getParameter("remember");
        rem = "1";
        String cpage = request.getParameter("cpage");
        if (e == null || p == null) {
            mv.addObject("cpage", cpageUrl(request.getQueryString()));
            return mv;
        }

        // validation.required(e);
        // validation.required(p);

        if ("".equals(p) || "".equals(e)) {
            mv.addObject("error", "Email or Password is empty.");
            mv.addObject("email", e);
            mv.addObject("password", e);
            return mv;

        }

        boolean isLogin = verify(session, e, p, rem);

        if (isLogin) {
            if (cpage == null || "".equals(cpage)) {
                return new ModelAndView("redirect:/");
            } else {
                return new ModelAndView("redirect:" + cpage);
            }

        } else {
            mv.addObject("error", "Email And Password mismatch.");
            mv.addObject("email", e);

            // mv.setViewName("redirect:" + request.getRequestURL());
            return mv;
        }

    }

    public String cpageUrl(String url) {
        if (url == null || "".equals(url)) {
            return "";
        } else {
            return "?" + url;
        }
    }

    @RequestMapping("/logout")
    public String logout(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpSession session) {
        session.invalidate();
        Cookie[] cookies = request.getCookies();

        for (int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals(Constants.COOKIE_EMAIL)) {
                // Cookie cookie = new Cookie("user", cookies[i].getValue());
                // cookie.setMaxAge(0);
                // response.addCookie(cookie);
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
                break;
            }
        }

        return "redirect:/";
    }

    @RequestMapping("/logoff")
    @ResponseBody
    public String logoff(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpSession session) {
        logout(request, response, session);
        return "success";
    }

    @RequestMapping("/logon")
    @ResponseBody
    public Map<String, Object> logon(HttpServletRequest request, HttpSession session) {
        boolean isLogin;
        Map<String, Object> rs = new HashMap<>();
        try {

            String e = request.getParameter("email");
            String p = request.getParameter("password");
            String rem = request.getParameter("remember");
            isLogin = verify(session, e, p, rem);
            rs.put("isLogin", isLogin);
            rs.put("username", e);

        } catch (Exception e) {
            e.printStackTrace();
            rs.put("isLogin", false);

        }
        return rs;
    }

    private boolean verify(HttpSession session, String email, String passoword, String remember) {
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
            String md5_pwd = BASE64.encrypt(p);
            boolean isLogin = session.getAttribute(Constants.SESSION_LOGIN_KEY) != null;
            if (!isLogin) {
                UserPwd user = userService.getUserPwd(email, md5_pwd);
                if (user != null) {

                    // if (remember != null && "1".equals(remember)) {
                    // response.setCookie(COOKIE_EMAIL, e, "30d");
                    // response.setCookie(COOKIE_PASSWORD, p, "30d");
                    // }
                    User info = userService.getUser(user.getIdUser());
                    setUserSession(session, user, info);
                    return true;
                }
            } else {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    private void setUserSession(HttpSession session, UserPwd user, User info) {
        session.setAttribute(Constants.SESSION_LOGIN_KEY, true);
        session.setAttribute(Constants.SESSION_USER_NAME_KEY, user.getUserName());
        if (info != null && info.getNiceName() != null && !"".equals(info.getNiceName())) {
            session.setAttribute(Constants.SESSION_USER_NAME_KEY, info.getNiceName());
        }
        if (info == null) {
            info = new User();
        }
        info.setUserName(user.getUserName());
        info.email = user.email;
        info.idUser = user.idUser;
        session.setAttribute(Constants.SESSION_USER_KEY, info);
        session.setAttribute(Constants.SESSION_USER_AUTH_KEY, AuthorityHelper._RAW_ON);
    }



    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView("signup");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String cpage = request.getParameter("cpage");
        if (!password.equals(confirm_password)) {
            mv.addObject("error", "Twice password mismatched.");
            mv.addObject("user", new UserPwd(email, username, null));
            return mv;
        }
        if (password.getBytes().length > 20) {
            mv.addObject("error", "password length<=20 char(or 6 chinese).");
            mv.addObject("user", new UserPwd(email, username, null));
            return mv;
        }

        try {
            String md5_pwd = BASE64.encrypt(password);
            UserPwd up = new UserPwd(email, username, md5_pwd);
            int updated = userService.addUserPwd(up);
            if (updated == -3) {
                mv.addObject("error", "Username and Email already existed.");
            } else if (updated == -2) {
                mv.addObject("error", "Username already existed.");
            } else if (updated == -1) {
                mv.addObject("error", "Email already existed.");
            } else if (updated > 0) {

                setUserSession(session, up, null);
                if (cpage == null || "".equals(cpage)) {
                    return new ModelAndView("redirect:/");
                } else {
                    return new ModelAndView("redirect:" + cpage);
                }
            } else {
                mv.addObject("error", "Signup error.");

            }
            mv.addObject("user", new UserPwd(email, username, null));
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Signup error.");
            mv.addObject("user", new UserPwd(email, username, null));
            return mv;
        }

    }

}
