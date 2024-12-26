package step.step33;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {

    public static boolean enableBackprop = true;
    public static boolean oldValue = enableBackprop;
    public static boolean train = false;
    /*
    public void close() {
        /enableBackprop = oldValue;
    }

     */

    public void print() {
        System.out.println(Config.enableBackprop);
    }
}
