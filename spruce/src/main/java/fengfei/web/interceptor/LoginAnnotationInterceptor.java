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

import fengfei.web.authority.Authority;
import fengfei.web.authority.ResponseType;
import fengfei.web.authority.Role;

public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object bean = handlerMethod.getBean();
        Authority authority = bean.getClass().getAnnotation(Authority.class);

        Authority methodAuthority = handlerMethod.getMethodAnnotation(Authority.class);
        if (methodAuthority != null) {
            authority = methodAuthority;
        }

//        System.out.println("xxxxxxxxxxxx: " + bean);
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
                // request.getRequestDispatcher("/login").forward(request, response);

                String path = request.getContextPath();
                String cpage = request.getRequestURI().replace(path, "");
                response.sendRedirect(path + "/login?cpage=" + cpage);
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

        return true;

    }
}
