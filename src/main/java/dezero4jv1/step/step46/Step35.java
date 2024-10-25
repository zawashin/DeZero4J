package dezero4jv1.step.step46;

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
        Variable gxs = x.grad;
        System.out.println(gxs);
        x.clearGrad();
        // retaiGrad = trueだと計算が不正確
        gxs.backward(false, true);
        Variable gxs2 = x.grad;
        System.out.println(gxs2);

    }
}
