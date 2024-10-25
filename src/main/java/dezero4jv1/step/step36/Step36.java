package dezero4jv1.step.step36;

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
        System.out.println(x.grad);

    }
}
