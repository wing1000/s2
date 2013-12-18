package fengfei.fir.rank;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RankUtils {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    static {
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    public static double score(
        double visits,
        double likes,
        double likesIn30Days,
        double favorites,
        double comments) {
        try {
            visits = visits == 0 ? 1 : visits;
            // double score = (visits / 50000) * 100 * 0.1
            // + ((likes - likesIn30Days) / visits) * 100 * 0.2
            // + (likesIn30Days/visits) * 0.4 + (favorites / visits) * 100 * 0.2
            // + (comments / visits) * 100 * 0.1;
            double score = visits * 0.05 + likes * 0.3 + likesIn30Days * 0.5
                    + (favorites / visits) * 0.2 + (comments / visits) * 0.05;

            double s = (score == 0 ? 1 : Math.pow(score, 0.8));

            score = 100 - 100 / (s == 0d ? 1 : s);

            String ss = decimalFormat.format(score < 0 ? 0 : score);
            // System.out.printf("%f %f %f %f %f %s\n", visits, likes,
            // likesIn30Days,
            // favorites, comments, ss);
            return Double.valueOf(ss);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(score(i, i, i, 0, 0));
        }
    }

}
