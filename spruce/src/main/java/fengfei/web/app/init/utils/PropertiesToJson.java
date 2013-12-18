package fengfei.web.app.init.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类是将Properties文件或者key/value（实现了Map接口的对象）转换为JSON对象,
 * 并且会将java字符串格式化转化为javascript格式化 “ %s second%s ago” to “{0} second{1} ago”
 * <p/>
 * <pre>
 * 例子：
 * 如下key/value:
 * since.seconds = %s second%s ago
 * since.minutes = %s minute%s ago
 * since.hours   = %s hour%s ago
 * add=Add
 * delete=Delete
 *
 * 调用 fengfei.web.app.init.utils.PropertiesToJson.toJson(Map<Object, Object>, String...)
 * fengfei.web.app.init.utils.PropertiesToJson.toJson(Map<Object, Object>, true, String...)
 * 结果：
 *    {
 * 		"<font color="red">S</font>ince" : {
 * 		"minutes" : "{0} minute{1} ago",
 * 		"seconds" : "{0} second{1} ago",
 * 		"hours" : "{0} hour{1} ago"
 *        },
 * 		"add":"Add",
 * 		"delete":"Delete"
 *    }
 * 调用 fengfei.web.app.init.utils.PropertiesToJson.toJson(Map<Object, Object>, "add")
 * fengfei.web.app.init.utils.PropertiesToJson.toJson(Map<Object, Object>, true, "add","delete")
 * 结果：
 *    {
 * 		"<font color="red">S</font>ince" : {
 * 		"minutes" : "{0} minute{1} ago",
 * 		"seconds" : "{0} second{1} ago",
 * 		"hours" : "{0} hour{1} ago"
 *       <font color="red">
 * 	 //"add":"Add",
 * 	 //"delete":"Delete"
 *       </font>
 *        }
 *    }
 * fengfei.web.app.init.utils.PropertiesToJson.toJson(Map<Object, Object>, false, String...)
 * 结果：
 *    {
 * 		"<font color="red">s</font>ince" : {
 * 		"minutes" : "{0} minute{1} ago",
 * 		"seconds" : "{0} second{1} ago",
 * 		"hours" : "{0} hour{1} ago"
 *        },
 * 		"add":"Add",
 * 		"delete":"Delete"
 *    }
 * </pre>
 *
 * @author Tietang
 */
public class PropertiesToJson {

    public static void main(String[] args) throws Exception {
        File f = new File("dd");
        System.out.println(f.getAbsolutePath());
        InputStream in = PropertiesToJson.class.getClassLoader().getResourceAsStream("messages");
        toFile(in, "public/app/i18n.js", "i18n", "web", "since",
                "validation");
        in = PropertiesToJson.class.getClassLoader().getResourceAsStream("messages.zh_CN");
        toFile(in, "public/app/i18n.zh_CN.js", "i18n", "web", "since",
                "validation");
    }

    /**
     * 默认大写JSON对象第一个字母
     *
     * @see #toFile(String, String, String, boolean, String...)
     */
    public static void toFile(String propFile, String jsonFile,
                              String jsonVariableName, String... excludes) throws IOException {
        toFile(propFile, jsonFile, jsonVariableName, true, new String[]{});
    }

    public static void toFile(InputStream in, String jsonFile,
                              String jsonVariableName, String... excludes) throws IOException {
        toFile(in, jsonFile, jsonVariableName, true, new String[]{});
    }

    /**
     * 将指定Properties文件转换成 json对象并保存到javascript文件
     *
     * @param propFile         Properties key/value文件路径
     * @param jsonFile         js文件路径
     * @param jsonVariableName json对象的变量名称
     * @throws IOException
     */
    public static void toFile(String propFile, String jsonFile,
                              String jsonVariableName, boolean upperCaseFirst, String... excludes)
            throws IOException {
        FileInputStream in = new FileInputStream(propFile);
        toFile(in, jsonFile, jsonVariableName, upperCaseFirst, excludes);
    }

    public static void toFile(InputStream in, String jsonFile,
                              String jsonVariableName, boolean upperCaseFirst, String... excludes)
            throws IOException {
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        String json = toJson(properties, upperCaseFirst, excludes);
        File f = new File(jsonFile);
//        System.out.println(f.getAbsolutePath());
        if (!f.exists()) {
            f.getParentFile().mkdirs();
        }
        FileOutputStream out = new FileOutputStream(f);
        out.write(("var " + jsonVariableName + "=").getBytes());
        out.write(json.getBytes());
        out.write(";\n".getBytes());
        out.close();
    }

    /**
     * 默认大写首字母， 相当于 toJson(Map<Object, Object>, true, String...)
     *
     * @see #toJson(Map, boolean, String...)
     */
    public static String toJson(Map<Object, Object> properties,
                                String... excludes) {
        JavaScriptObject javaScriptObject = new JavaScriptObject(excludes);
        return toJson(properties, javaScriptObject);
    }

    /**
     * 将Key/value转换为JSON对象
     *
     * @param properties     key/value
     * @param upperCaseFirst 对象第一个字母是否大写
     * @param excludes       排除指定字符串开头的key，不转换
     * @return JSON对象
     */
    public static String toJson(Map<Object, Object> properties,
                                boolean upperCaseFirst, String... excludes) {

        JavaScriptObject javaScriptObject = new JavaScriptObject(
                upperCaseFirst, excludes);

        return toJson(properties, javaScriptObject);
    }

    private static String toJson(Map<Object, Object> properties,
                                 JavaScriptObject javaScriptObject) {
        Set<Entry<Object, Object>> entries = properties.entrySet();
        for (Entry<Object, Object> entry : entries) {
            javaScriptObject.put(entry.getKey().toString(), entry.getValue());
        }
        String json = new Gson().toJson(javaScriptObject.getProperties());
        return json;
    }

    private static final String FormatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

    private static Pattern p = Pattern.compile(FormatSpecifier);

    static String convertFormat(String str) {
        // Pattern p = Pattern.compile(FormatSpecifier);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();

        int i = 0;
        while (m.find()) {
            String rstr = "{" + i + "}";
            m.appendReplacement(sb, rstr);
            i++; // 字符串出现次数
        }
        m.appendTail(sb);// 从截取点将后面的字符串接上
        String s = sb.toString();

        return s;
    }

    public static class JavaScriptObject {
        boolean upperCaseFirst = true;
        Map<String, Object> properties = new HashMap<>();
        private String[] excludePrefix = {};

        public Map<String, Object> getProperties() {
            return properties;
        }

        public JavaScriptObject(boolean upperCaseFirst) {
            super();
            this.upperCaseFirst = upperCaseFirst;
        }

        public JavaScriptObject(String... excludePrefix) {
            this.excludePrefix = excludePrefix;
        }

        public JavaScriptObject(boolean upperCaseFirst, String[] excludePrefix) {
            super();
            this.upperCaseFirst = upperCaseFirst;
            this.excludePrefix = excludePrefix;
        }

        public void put(String key, Object value) {
            put(properties, key, value);
        }

        private void put(Map<String, Object> properties, String key,
                         Object value) {
            if (isInclude(key)) {
                return;
            }

            int index = key.indexOf(".");
            if (index >= 0) {
                String fkey = key.substring(0, index);

                String nextKey = key.substring(index + 1);
                if (upperCaseFirst) {
                    fkey = fkey.replaceFirst(fkey.substring(0, 1), fkey
                            .substring(0, 1).toUpperCase());
                }

                Map<String, Object> map = (Map<String, Object>) properties
                        .get(fkey);
                if (map == null) {
                    map = new HashMap<>();
                }
                properties.put(fkey, map);

                put(map, nextKey, value);

            } else {
                value = PropertiesToJson.convertFormat(value.toString());
                properties.put(key, value);
            }

        }

        private boolean isInclude(String key) {
            if (excludePrefix == null) {
                return false;
            }
            for (int i = 0; i < excludePrefix.length; i++) {
                if (key.startsWith(excludePrefix[i])) {
                    return true;
                }
            }
            return false;
        }
    }
}
