package dezero4jv1.step.step38;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {
    private static final Config instance = new Config();
    private final Map<String, Boolean> param;

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