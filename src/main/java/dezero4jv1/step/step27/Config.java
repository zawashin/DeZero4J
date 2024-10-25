package dezero4jv1.step.step27;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {
    public static boolean enableBackprop = true;
    public static boolean oldValue = enableBackprop;

    public void close() {
        enableBackprop = oldValue;
    }
}
