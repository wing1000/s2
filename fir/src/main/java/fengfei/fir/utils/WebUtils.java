package fengfei.fir.utils;

import fengfei.fir.i18n.Messages;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;

public class WebUtils {
    public static String i18n(String key, Object... args) {
        return Messages.get(key, args);
    }

    public static String getLastUrlWord(String url) {
        String[] urls = url.trim().split("/");
        System.out.printf("%d %s ", urls.length, Arrays.asList(urls));
        if (urls.length > 0) {
            String lastUrlWord = urls[urls.length - 1];
            // if ("/".equals(url.substring(url.length() - 1))) {
            // lastUrlWord = urls[urls.length - 2];
            // }
            return lastUrlWord;
        }
        return url;
    }

    static ObjectMapper mapper = new ObjectMapper();

    public static String toJSON(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getLastUrlWord("/"));
        System.out.println(getLastUrlWord("3232131/"));
        System.out.println(getLastUrlWord("/3232131/"));
        System.out.println(getLastUrlWord("/43242/"));
        System.out.println(getLastUrlWord("/43242/432423"));
        System.out.println(getLastUrlWord("/43242/432423/"));
    }
}
