package fengfei.web.app.init;

import fengfei.forest.slice.utils.ResourcesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Date: 13-12-19
 * @Time: 下午2:12
 */
public class BaseInit {
    static Properties applicationConfiguration;

    protected Properties init() {
        try {
            if (applicationConfiguration == null) {
                InputStream inputStream = ResourcesUtils.getResourceAsStream("application.conf");
                applicationConfiguration = new Properties();
                applicationConfiguration.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return applicationConfiguration;
    }
}
