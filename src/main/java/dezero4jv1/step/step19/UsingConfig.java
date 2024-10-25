package dezero4jv1.step.step19;

import java.util.function.Supplier;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class UsingConfig implements AutoCloseable {
    private final Supplier<Boolean> getter;
    private final boolean oldValue;

    public UsingConfig(Supplier<Boolean> getter, boolean value) {
        this.getter = getter;
        this.oldValue = getter.get();
        setter(value);
    }

    private void setter(boolean value) {
        // Set the value using reflection or other means
    }

    @Override
    public void close() {
        setter(oldValue);
    }
}
