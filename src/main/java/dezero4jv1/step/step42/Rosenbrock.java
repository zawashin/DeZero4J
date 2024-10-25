package dezero4jv1.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Rosenbrock extends LossFunction {

    public Variable[] forward(Variable... xs) {
        Variable[] loss = new Variable[1];
        int length = xs[0].getData().length;
        // z = 100 * (x0 - x0**2)**2 + (x0 - 1)**2
        loss[0] = parameter(100, length).times(xs[1].minus(xs[0].pow(2)).pow(2)).plus((xs[0].minus(parameter(1, length)).pow(2)));
        return loss;
    }

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{0});
        Variable y = new Variable(new double[]{2});
        Rosenbrock rosenbrock = new Rosenbrock();
        Variable z = rosenbrock.calcLoss(x, y);
        z.backward(false, true);
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }

}
