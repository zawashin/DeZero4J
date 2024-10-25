package dezero4j.step.step18;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config {

    public void print() {
        System.out.println(Config.enableBackprop);
    }

    public static boolean enableBackprop = true;
    public static boolean oldValue = enableBackprop;

    public void close() {
        enableBackprop = oldValue;
    }

    public static void main(String[] args) {
        Config.enableBackprop = true;
        Config config1 = new Config();
        config1.print();
        Config.enableBackprop = false;
        Config config2 = new Config();
        config2.print();
    }
}
