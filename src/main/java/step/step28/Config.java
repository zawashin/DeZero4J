package step.step28;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {

    public static boolean enableBackprop = true;
    public static boolean oldValue = enableBackprop;

    public void close() {
        enableBackprop = oldValue;
    }

    public void print() {
        System.out.println(step.step29.Config.enableBackprop);
    }
}
