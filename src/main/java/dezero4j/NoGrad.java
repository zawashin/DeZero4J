package dezero4j;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class NoGrad implements Serializable {

    @Serial
    private static final long serialVersionUID = 3503465985065190338L;

    public static UsingConfig no_grad() {
        return new UsingConfig("enableBackprop", false);
    }
}
