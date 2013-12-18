package fengfei.fir.i18n;

/**
 * @Date: 13-12-18
 * @Time: 下午4:08
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * I18n Helper
 * <p/>
 * translation are defined as properties in /conf/messages.<em>locale</em> files
 * with locale being the i18n country code fr, en, fr_FR
 * <p/>
 * <pre>
 * # /conf/messages.fr
 * hello=Bonjour, %s !
 * </pre>
 * <code>
 * Messages.get( "hello", "World"); // => "Bonjour, World !"
 * </code>
 */
public class Messages {

    private static final Object[] NO_ARGS = new Object[]{null};

    static public Properties defaults;

    static public Map<String, Properties> locales = new HashMap<String, Properties>();

    static Pattern recursive = Pattern.compile("&\\{(.*?)\\}");

    public static void defaultLoad() {
        Locale locale = Language.getLocale();
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        String title = messages.getString("title");
    }

    /**
     * Given a message code, translate it using current locale.
     * If there is no message in the current locale for the given key, the key
     * is returned.
     *
     * @param key  the message code
     * @param args optional message format arguments
     * @return translated message
     */
    public static String get(Object key, Object... args) {
        return getMessage(Language.get(), key, args);
    }

    /**
     * Return several messages for a locale
     *
     * @param locale the locale code, e.g. fr, fr_FR
     * @param keys   the keys to get messages from. Wildcards can be used at the end: {'title', 'login.*'}
     * @returnmessages as a {@link java.util.Properties java.util.Properties}
     */
    public static Properties find(String locale, Set<String> keys) {
        Properties result = new Properties();
        Properties all = all(locale);
        // Expand the set for wildcards
        Set<String> wildcards = new HashSet<String>();
        for (String key : keys) {
            if (key.endsWith("*")) wildcards.add(key);
        }
        for (String key : wildcards) {
            keys.remove(key);
            String start = key.substring(0, key.length() - 1);
            for (Object key2 : all.keySet()) {
                if (((String) key2).startsWith(start)) {
                    keys.add((String) key2);
                }
            }
        }
        // Build the result
        for (Object key : all.keySet()) {
            if (keys.contains(key)) {
                result.put(key, all.get(key));
            }
        }
        return result;
    }

    public static String getMessage(Locale locale, Object key, Object... args) {

        String value = null;
        if (key == null) {
            return "";
        }
        if (locales.containsKey(locale.getCountry())) {
            value = locales.get(locale).getProperty(key.toString());
        }
        if (value == null) {
            value = defaults.getProperty(key.toString());
        }
        if (value == null) {
            value = key.toString();
        }

        return formatString(value, args);
    }

    public static String formatString(String value, Object... args) {
//        String message = String.format(value, coolStuff(value, args));
        String message = String.format(value, args);
        Matcher matcher = recursive.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, get(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    static Pattern formatterPattern = Pattern.compile(
            "%((\\d+)\\$)?([-#+ 0,(]+)?(\\d+)?([.]\\d+)?([bBhHsScCdoxXeEfgGaAtT])");

    /**
     * return all messages for a locale
     *
     * @param locale the locale code eg. fr, fr_FR
     * @return messages as a {@link java.util.Properties java.util.Properties}
     */
    public static Properties all(String locale) {
        if (locale == null || "".equals(locale))
            return defaults;
        Properties mergedMessages = new Properties();
        mergedMessages.putAll(defaults);
        mergedMessages.putAll(locales.get(locale));
        return mergedMessages;
    }

}
