package dezero4jv1.step.step27;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step27 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{Math.PI/4.0});

        // y = a * b + c
        Variable y = x.sin();
        y.backward();

        System.out.println(y.getData()[0]);
        System.out.println(x.getGrad()[0]);
    }
}
