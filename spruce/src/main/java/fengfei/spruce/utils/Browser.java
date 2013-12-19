package fengfei.spruce.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Browser {

    final static String[] regs = { "(msie) ([\\w.]+)", "(chrome)[ \\/]([\\w.]+)",
            "(firefox)[ \\/]([\\w.]+)", "version[ \\/]([\\w.]+) (safari)",
            "(opera)(?:.*version|)[ \\/]([\\w.]+)", "(webkit)[ \\/]([\\w.]+)" };

    public static String[] getBrowser(String ua) {
        // Header agentHeader = request.headers.get("user-agent");
        // String agent = agentHeader.value().toLowerCase();
        ua = ua.toLowerCase();

        String[] brower = null;
        for (int i = 0; i < regs.length; i++) {
            String[] rs = getMatcher(regs[i], ua);
            if (rs != null) {
                brower = rs;
                break;
            }

        }
        return brower;
    }

    public static boolean isIE67(HttpServletRequest request) {
        String agentHeader = request.getHeader("user-agent");
        String agent = agentHeader;//.value().toLowerCase();
//        System.out.println(agent);
//        List<String> langs=request.acceptLanguage();
//
//        System.out.println("lng: "+ langs);
//        Lang.change(langs.get(0));
        String[] bs = getBrowser(agent);
        float version = toFloat(bs[1]);
        if ("msie".equals(bs[0]) && version <= 7) {
            return true;
        } else {
            return false;
        }

    }

    private static float toFloat(String s) {
        int index = s.indexOf(".");
        if(index<0){
            return 0;
        }
        try {
            return Float.parseFloat(s.substring(0, index));
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public static void test() {
        String agent = (" Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)\n"
                + "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; QQBrowser/7.3.11251.400)\n"
                + " Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; QQDownload 732; BTRS35926;  Embedded Web Browser from: http://bsalsa.com/; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)\n"
                + "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)\n"
                + " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 BIDUBrowser/2.x Safari/537.31\n"
                + "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36\n"
                + "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2\n"
                + " Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0\n")
            .toLowerCase();
        String[] ua = agent.split("\n");
        System.out.println(ua.length);
        String[] regs = { "(msie) ([\\w.]+)", "(chrome)[ \\/]([\\w.]+)",
                "(firefox)[ \\/]([\\w.]+)", "version[ \\/]([\\w.]+) (safari)",
                "(opera)(?:.*version|)[ \\/]([\\w.]+)", "(webkit)[ \\/]([\\w.]+)" };
        for (int j = 0; j < ua.length; j++) {

            for (int i = 0; i < regs.length; i++) {
                String[] rs = getMatcher(regs[i], ua[j]);
                if (rs != null) {
                    System.out.println(Arrays.asList(rs));
                    break;
                }

            }
        }

    }

    public static String[] getMatcher(String regex, String source) {
        String[] result = new String[2];
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        // System.out.println(regex+" "+source);
        if (matcher.find()) {

            if (source.indexOf("safari") > 0 && source.indexOf("chrome") < 0) {
                result[1] = matcher.group(1);// 只取第一组
                result[0] = matcher.group(2);// 只取第一组
            } else {
                result[0] = matcher.group(1);// 只取第一组
                result[1] = matcher.group(2);// 只取第一组
            }
        } else {
            return null;
        }
        return result;
    }
}
