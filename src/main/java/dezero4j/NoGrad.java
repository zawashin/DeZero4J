package dezero4j;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class NoGrad extends UsingConfig {
    public NoGrad() {
        super("enable_backprop", false);
    }
}