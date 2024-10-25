package dezero4jv1.step.step14;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step142 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{3.0});
        Variable y = x.plus(x)[0];
        y.backward();
        System.out.println(x.getGrad()[0]);
        x.cleaGrad();
        y = (x.plus(x)[0]).plus(x)[0];
        y.backward();
        System.out.println(x.getGrad()[0]);
    }
}
