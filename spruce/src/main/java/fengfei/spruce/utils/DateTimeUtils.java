package fengfei.spruce.utils;

import fengfei.fir.utils.WebUtils;

public class DateTimeUtils {

    final static int OneMinute = 60;
    final static int OneHour = OneMinute * 60;
    final static int OneDay = OneHour * 24;
    final static int OneMonth = OneDay * 30;
    final static int OneYear = OneDay * 365;

    /**
     * m years n months/x minutes/m hours/m days
     *
     * @param before
     * @return
     */
    public static String period(long before) {
        int seconds = (int) ((System.currentTimeMillis() - before) / 1000);
        int minutes = seconds / OneMinute;
        int hours = seconds / OneHour;
        int days = seconds / OneDay;
        int months = seconds / OneMonth;
        int years = seconds / OneYear;
        int ymonths = (seconds % OneYear) / OneMonth;
        String lostTime = null;
        if (seconds >= 1) {
            lostTime = WebUtils.i18n("since.seconds", seconds, "");
        } else {
            lostTime = WebUtils.i18n("since.seconds", 1, "");
        }
        if (minutes >= 1) {
            lostTime = WebUtils.i18n("since.minutes", minutes, "");
        }
        if (hours >= 1) {
            lostTime = WebUtils.i18n("since.hours", hours, "");
        }
        if (days >= 1) {
            lostTime = WebUtils.i18n("since.hours", days, "");
        }
        if (months >= 1) {
            lostTime = WebUtils.i18n("since.months", months, "");
        }
        if (years >= 1) {
            lostTime = years + " years";
            if (ymonths >= 1) {
                lostTime += " " + ymonths + " months";
                lostTime = WebUtils.i18n("since.years.months", years, "", months, "");
            } else {
                lostTime = WebUtils.i18n("since.years", years, "");
            }
        }
        return lostTime;
    }


}
