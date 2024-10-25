package dezero4jv1.step.step21;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step21 {

    public static void main(String[] args) {
        Variable a = new Variable(new double[]{3.0});
        Variable b = new Variable(new double[]{2.0});
        Variable c = new Variable(new double[]{1.0});

        // y = a * b + c
        Variable y = (a.times(b))[0].plus(c)[0];
        y.backward();

        System.out.println(y.getData()[0]);
        System.out.println(a.getGrad()[0]);
        System.out.println(b.getGrad()[0]);
    }
}
