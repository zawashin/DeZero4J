package dezero4j.step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step36 {

    public static void main(String[] args) {
        Variable x = new Variable(2.0);
        Variable y = x.pow(2);
        y.backward(false, true);
        Variable gx = x.grad;
        x.clearGrad();
        Variable z = gx.pow(3).plus(y);
        z.backward(false, true);
        System.out.println(x.data);
        System.out.println(y.data);
        System.out.println(gx.data);
        System.out.println(x.grad);

    }
}
