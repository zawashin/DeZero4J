package step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class UsingConfig implements AutoCloseable {
    private final String name;
    private boolean oldValue;

    public UsingConfig(String name, boolean value) {
        this.name = name;
        if (name.equals("enableBackprop")) {
            oldValue = Config.enableBackprop;
            Config.enableBackprop = value;
        } else if (name.equals("train")) {
            oldValue = Config.train;
            Config.train = value;
        } else {
            System.err.println("Unknown name: " + name);
        }
    }

    public static UsingConfig noGrad() {
        return new UsingConfig("enableBackprop", false);
    }

    public static UsingConfig testMode() {
        return new UsingConfig("train", false);
    }

    @Override
    public void close() {
        if (name.equals("enableBackprop")) {
            Config.enableBackprop = oldValue;
        } else if (name.equals("train")) {
            Config.train = oldValue;
        } else {
            System.err.println("Unknown name: " + name);
        }
    }
}
