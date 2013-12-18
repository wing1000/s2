package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import fengfei.fir.utils.AppUtils;
import japidviews.Application.error.E500;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Http;

import java.util.*;
import java.util.Map.Entry;

public class Admin extends ExceptionCatchController {
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


    public final static String SESSION_LOGIN_KEY = "islogin";
    public final static String SESSION_USER_ID_KEY = "USER_ID";
    public final static String SESSION_USER_NAME_KEY = "USER_NAME";
    public final static String COOKIE_EMAIL = "spruce_email";
    public final static String COOKIE_PASSWORD = "spruce_pwd";
    //
    public final static int Row = 13;
    public final static int TotalRowShow = 20;

    @Before
    static void log() {
        Http.Header agentHeader = request.headers.get("user-agent");
        String agent = agentHeader.value().toLowerCase();
        String ip = request.remoteAddress;
        logger.info(ip + ": " + agent);
    }

    protected static Map<String, String[]> escapeAll() {
        Map<String, String[]> maps = params.all();
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

    protected static Map<String, String> escapeAllSimple() {
        Map<String, String> map = params.allSimple();
        Set<Entry<String, String>> sets = map.entrySet();
        for (Entry<String, String> entry : sets) {
            map.put(entry.getKey(), escape(entry.getValue()));
        }
        return map;
    }

    protected static String escape(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        return StringEscapeUtils.escapeHtml4(str);
    }

    protected static Integer currentUserId() {
        String sidUser = session.get(SESSION_USER_ID_KEY);
        return sidUser == null || "".equals(sidUser) ? null : Integer.parseInt(sidUser);
    }

    protected static String currentNiceName() {
        String username = session.get(SESSION_USER_NAME_KEY);
        return username;
    }

    protected static void renderDoneJSON(boolean updated) {
        Done done = createDone(updated);
        renderJSON(done);
    }

    protected static void renderHasErrors() {

        if (validation.hasErrors()) {
            Done done = createDone(false);
            done.put("messages", validation.errorsMap());
            renderJSON(done);
        }
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


    protected static void renderErrorJSON() {
        Done done = new Done(i18n("server.inner.error"), Status.Error);
        renderJSON(done);
    }

    protected static void renderSuccessJSON() {
        Done done = new Done(i18n("server.inner.error"), Status.Error);
        renderJSON(done);
    }


    protected static void redirect500() {
        redirect("/500");
    }


    protected static void render500() {
        throw new JapidResult(new E500().render());
    }

    protected static void flashError() {
        flash.put("error", i18n("msg.save.fail"));
    }

    protected static void flashSuccess() {
        flash.put("success", i18n("msg.save.success"));
    }

    protected static void flashFail() {
        flash.put("error", i18n("msg.save.fail"));
    }

    protected static int getIIP() {
        String ip = request.remoteAddress;
        int iip = AppUtils.ip2int(ip);
        return iip;
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
