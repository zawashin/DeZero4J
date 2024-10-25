package dezero4jv1.step.step19;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step19 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{2.0});
        Function square = new Square();
        Variable a = square.forward(x)[0];
        Function plus = new Plus();
        Variable y = plus.forward(a.square()[0], a.square()[0])[0];
        Config.enableBackprop = false;
        y.backward();

        System.out.println(y.getData()[0]);
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);
    }
}
