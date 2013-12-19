package controllers;

import fengfei.fir.i18n.Messages;
import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import fengfei.fir.utils.AppUtils;
import fengfei.spruce.Constants;
import fengfei.ucm.entity.profile.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

public class Admin {
    static Logger logger = LoggerFactory.getLogger(Admin.class);

    public static Map<String, String> pathTitle = new HashMap<>();

    static {
        pathTitle.put("/pop", i18n("popular"));
        pathTitle.put("/last", i18n("last"));
        pathTitle.put("/choice", i18n("choice"));
        pathTitle.put("/upcoming", i18n("upcomming"));
        pathTitle.put("/category", i18n("category"));
        pathTitle.put("/flow", i18n("flow"));
        pathTitle.put("/fav", i18n("fav"));
        pathTitle.put("/favorate", i18n("favorate"));
    }

    //
    public final static int Row = 13;
    public final static int TotalRowShow = 20;


    protected static Map<String, String[]> escapeAll(HttpServletRequest request) {
        Map<String, String[]> maps = request.getParameterMap();// params.all();
        Set<Entry<String, String[]>> sets = maps.entrySet();
        for (Entry<String, String[]> entry : sets) {
            String[] values = entry.getValue();
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = escape(values[i]);
                }
            }
            maps.put(entry.getKey(), values);
        }

        return maps;
    }

    protected static Map<String, String> escapeAllSimple(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();

        Set<Entry<String, String[]>> sets = map.entrySet();
        for (Entry<String, String[]> entry : sets) {
            String[] entries = entry.getValue();
            params.put(entry.getKey(), escape(entries[0]));
        }
        return params;
    }

    protected static Map<String, String> allSimple(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();

        Set<Entry<String, String[]>> sets = map.entrySet();
        for (Entry<String, String[]> entry : sets) {
            String[] entries = entry.getValue();
            params.put(entry.getKey(), entries[0]);
        }
        return params;

    }

    protected static Map<String, String> allSimple( ) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return allSimple(request);

    }

    protected static String escape(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        return StringEscapeUtils.escapeHtml4(str);
    }

    protected static Integer currentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
        return user == null ? null : user.idUser;
    }

    protected static String currentNiceName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(Constants.SESSION_USER_NAME_KEY);
        return username;
    }

    protected static Done renderDoneJSON(boolean updated) {
        return createDone(updated);
    }


    protected static Done createDone(boolean updated) {
        Done done = null;
        if (updated) {
            done = new Done(i18n("success"), Status.Success);
        } else {
            done = new Done(i18n("server.inner.error"), Status.Fail);
        }
        return done;
    }


    protected static Done renderErrorJSON() {
        Done done = new Done(i18n("server.inner.error"), Status.Error);
        return done;
    }

    protected static Done renderSuccessJSON() {
        Done done = new Done(i18n("server.inner.error"), Status.Error);
        return done;
    }


    protected static void flashError(ModelAndView mv) {

        mv.addObject("error", i18n("msg.save.fail"));
    }

    protected static void flashSuccess(ModelAndView mv) {
        mv.addObject("success", i18n("msg.save.success"));
    }

    protected static void flashFail(ModelAndView mv) {
        mv.addObject("error", i18n("msg.save.fail"));
    }

    protected static int getIntRemoteIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        int iip = AppUtils.ip2int(ip);
        return iip;
    }

    protected static String getRemoteAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        return ip;
    }

    public static List<String> pageList(int page, int maxPage) {
        List<String> pages = new ArrayList<>();

        int min = page - 4;
        int max = page + 4 + (min < 0 ? -min : 0);
        max = max > maxPage ? maxPage : max;
        if (max - min < 10) {
            min -= max - min;
        }
        min = min < 1 ? 1 : min;

        for (int i = min; i <= max; i++) {
            pages.add(String.valueOf(i));
        }

        return pages;
    }

    protected static String i18n(String key, Object... args) {
        return Messages.get(key, args);
    }
}
