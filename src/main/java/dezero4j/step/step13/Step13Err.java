package dezero4j.step.step13;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step13Err {

        public static void main(String[] args) {
            Variable x = new Variable(new double[]{2.0});
            Variable y = new Variable(new double[]{3.0});

            AbstractFunction square = new Square();
            AbstractFunction plus = new Plus();
            // 関数の使いまわしはエラーの原因
            Variable z = plus.forward(square.forward(new Variable[]{x})[0], square.forward(new Variable[]{y})[0])[0];

            z.backward();
            System.out.println(z.getData()[0]);
            System.out.println(x.getGrad()[0]);
            System.out.println(y.getGrad()[0]);
    }
}
