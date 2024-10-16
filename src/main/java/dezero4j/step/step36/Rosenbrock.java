package dezero4j.step.step36;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Rosenbrock extends LostFunction {
    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getData().length;
        // z = 100 * (x0 - x0**2)**2 + (x0 - 1)**2
        lost[0] = param(length, 100).times(xs[1].minus(xs[0].pow(2)).pow(2)).plus((xs[0].minus(param(length, 1)).pow(2)));
        return lost;
    }

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{0});
        Variable y = new Variable(new double[]{2});
        Rosenbrock rosenbrock = new Rosenbrock();
        Variable z = rosenbrock.calc(x, y);
        z.backward(false, true);
        System.out.println(x.getGrad().getData()[0]);
        System.out.println(y.getGrad().getData()[0]);
    }
}
