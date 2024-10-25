package dezero4jv1.step.step13;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step13 {

        public static void main(String[] args) {
            Variable x = new Variable(new double[]{2.0});
            Variable y = new Variable(new double[]{3.0});

            Function square = new Square();
            Function square2 = new Square();
            Function plus = new Plus();
            Variable z = plus.forward(square.forward(x)[0], square2.forward(y)[0])[0];

            z.backward();
            System.out.println(z.getData()[0]);
            System.out.println(x.getGrad()[0]);
            System.out.println(y.getGrad()[0]);
    }
}
