package dezero4j;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Config implements Serializable {

    @Serial
    private static final long serialVersionUID = -3143773052202101785L;
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
