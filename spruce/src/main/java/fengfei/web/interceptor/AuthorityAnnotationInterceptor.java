package fengfei.web.interceptor;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fengfei.fir.model.Done;
import fengfei.spruce.Constants;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fengfei.ucm.entity.profile.User;
import fengfei.web.authority.Authority;
import fengfei.web.authority.AuthorityHelper;
import fengfei.web.authority.AuthorityType;
import fengfei.web.authority.ResponseType;
import fengfei.web.authority.Role;

public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Authority authority = handlerMethod.getMethodAnnotation(Authority.class);
        // default public role
        if (null == authority) {
            return true;
        }

        logger.debug("authority", authority.toString());
        Role role = authority.role();
        if (role == Role.Guest) {
            return true;
        } else {

        }
        ResponseType responseType = authority.responseType();
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.SESSION_LOGIN_KEY) == null) {

            if (responseType == ResponseType.HtmlPage) {
                request.getRequestDispatcher("/login.jsp?oprst=false&opmsg=请登录!").forward(
                    request,
                    response);
            } else if (responseType == ResponseType.PlainText) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                Done done = new Done("Please login!", Done.Status.Fail);
                String json = mapper.writeValueAsString(done);
                pw.println(json);
                pw.flush();
                pw.close();
            }
            return false;
        }

        boolean aflag = false;
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        String rights = (String) session.getAttribute(Constants.SESSION_USER_AUTH_KEY);
        for (AuthorityType at : authority.authorityTypes()) {
            if (AuthorityHelper.hasAuthority(at.getIndex(), rights) == true) {
                aflag = true;
                break;
            }
        }

        if (false == aflag) {

            if (responseType == ResponseType.HtmlPage) {
                response.sendRedirect(request.getRequestURL().toString());
            } else if (responseType == ResponseType.PlainText) {
                // 采用ajax方式的进行提示
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                Done done = new Done("Can't be operated. No Authority!", Done.Status.Fail);
                String json = mapper.writeValueAsString(done);
                pw.println(json);
                pw.flush();
                pw.close();
            }

            return false;

        }

        return true;

    }
}
