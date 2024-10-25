package dezero4jv1.step.step14;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step14 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{3.0});
        Function plus = new Plus();
        Function plus2 = new Plus();
        Variable y = plus.forward(x, x)[0];
        y.backward();
        System.out.println(x.getGrad()[0]);
        x.cleaGrad();
        y = plus2.forward(plus.forward(x, x)[0], x)[0];
        y.backward();
        System.out.println(x.getGrad()[0]);
    }
}
