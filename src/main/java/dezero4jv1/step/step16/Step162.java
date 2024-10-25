package dezero4jv1.step.step16;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step162 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{2.0});
        Function square = new Square();
        Variable a = square.forward(x)[0];
        Variable a2 = square.forward(x)[0];
        Function plus = new Plus();
        Variable y = plus.forward(a.square()[0], a2.square()[0])[0];
        y.backward();
        System.out.println(y.getData()[0]);
        System.out.println(x.getGrad()[0]);
    }
}
