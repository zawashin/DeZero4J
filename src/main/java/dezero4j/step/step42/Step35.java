package dezero4j.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step35 {

    public static void main(String[] args) {
        Variable x = new Variable(1);
        System.out.println(x);
        Variable y = x.tanh();
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        Variable gx = x.grad;
        System.out.println(gx);
        x.clearGrad();
        // retaiGrad = trueだと計算が不正確
        gx.backward(false, true);
        Variable gx2 = x.grad;
        System.out.println(gx2);

    }
}
