package dezero4j.step.step33.step33_2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {
    private static Config instance = new Config();
    private Map<String, Boolean> param;

    private Config() {
        param = new HashMap<>();
        param.put("enable_backprop", true);
    }

    public static Config getInstance() {
        return instance;
    }

    public Map<String, Boolean> getParam() {
        return param;
    }
}
