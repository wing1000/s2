package fengfei.sprucy;

public class AppConstants {
    public final static byte DefaultFollowType = 0;
    public static int StartTimeSeconds = 1325347200;// 2012开始seconds
    // Score
    public static double PopularMinScore = 20;
    public static double UpcomingMinScore = 10;
    public static double UpcomingMaxScore = PopularMinScore;
    // Dir Limit
    public static int DirLimit = 20;

    //
    public static String TextSplitRegex = "\n|\t|,| |  |，|#";
    public static String TextTagReSeparator = ",";
    public static String CommaSeparator = ",";

}
