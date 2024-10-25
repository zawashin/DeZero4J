package dezero4jv1.step.step18;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ConfigTest {
    public void print() {
        System.out.println(Config.enableBackprop);
    }

    public static void main(String[] args) {
        Config.enableBackprop = true;
        ConfigTest config1 = new ConfigTest();
        config1.print();
        Config.enableBackprop = false;
        ConfigTest config2 = new ConfigTest();
        config2.print();
    }
}
