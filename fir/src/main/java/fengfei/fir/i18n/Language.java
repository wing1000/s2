package fengfei.fir.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * @Date: 13-12-18
 * @Time: 下午4:09
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;


/**
 * Language support
 */
public class Language {
    static Logger logger = LoggerFactory.getLogger(Language.class);

    public static ThreadLocal<Locale> current = new ThreadLocal<>();

    /**
     * Retrieve the current language or null
     *
     * @return The current language (fr, ja, it ...) or null
     */
    public static Locale get() {
        Locale locale = current.get();
        if (locale == null) {

            setDefaultLocale();
        }
        // get the picked locale
        locale = current.get();

        return locale;
    }


    /**
     * Force the current language
     *
     * @param locale (fr, ja, it ...)
     * @return false if the language is not supported by the application
     */
    public static boolean set(Locale locale) {
        if (locale != null) {
            current.set(locale);
            return true;
        } else {
            logger.warn("Locale %s is not defined in your application.conf", locale);
            return false;
        }
    }

    /**
     * Clears the current language - This wil trigger resolving language from request
     * if not manually set.
     */
    public static void clear() {
        current.remove();
    }


    /**
     * Change language for next requests
     *
     * @param locale (e.g. "fr", "ja", "it", "en_ca", "fr_be", ...)
     */
    public static void change(Locale locale) {
        if (locale == null) {
            // Give up
            return;
        }

        set(locale);

    }


    public static void setDefaultLocale() {
        set(Locale.ROOT);

    }

    /**
     * @return the default locale if the Locale cannot be found otherwise the locale
     *         associated to the current Lang.
     */
    public static Locale getLocale() {
        Locale locale = get();
        if (locale != null) {
            return locale;
        }
        return Locale.getDefault();
    }

    public static Locale getLocale(String localeStr) {
        Locale langMatch = null;
        for (Locale locale : Locale.getAvailableLocales()) {
            String lang = localeStr;
            int splitPos = lang.indexOf("_");
            if (splitPos > 0) {
                lang = lang.substring(0, splitPos);
            }
            if (locale.toString().equalsIgnoreCase(localeStr)) {
                return locale;
            } else if (locale.getLanguage().equalsIgnoreCase(lang)) {
                langMatch = locale;
            }
        }
        return langMatch;
    }

}
