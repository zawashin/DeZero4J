package dezero4jv1.step.step22;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step21 {

    public static void main(String[] args) {
        Variable a = new Variable(new double[]{3.0});
        Variable b = new Variable(new double[]{2.0});
        Variable c = new Variable(new double[]{1.0});

        // y = a * b + c
        Variable y = (a.times(b)).plus(c);
        y.backward();

        System.out.println(y.getData()[0]);
        System.out.println(a.getGrad()[0]);
        System.out.println(b.getGrad()[0]);

        // y = a * b^2 + c
        a.cleaGrad();
        b.cleaGrad();
        c.cleaGrad();
        y = (a.times(b.square())).plus(c);
        y.backward();

        System.out.println(y.getData()[0]);
        System.out.println(a.getGrad()[0]);
        System.out.println(b.getGrad()[0]);
    }
}
