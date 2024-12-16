package dezero4j.step.step19;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step18 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{2.0});
        Function square = new Square();
        Variable a = square.forward(x)[0];
        Function add = new Add();
        Variable y = add.forward(a.square(), a.square())[0];
        Config.enableBackprop = false;
        y.backward();

        System.out.println(y.getData());
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }
}
