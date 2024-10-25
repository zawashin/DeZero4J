package dezero4jv1.step.step44;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sphere extends LossFunction {

    public Variable[] forward(Variable... xs) {
        Variable[] loss = new Variable[1];
        loss[0] = xs[0].pow(2).plus(xs[1].pow(2));
        return loss;
    }

    public static void main(String[] args) {
        Variable x = new Variable(1);
        Variable y = new Variable(1.0);
        Sphere sphere = new Sphere();
        Variable z = sphere.calcLoss(x, y);
        z.backward(true, true);
        System.out.println(x.getGrad().getData().values[0]);
        System.out.println(y.getGrad().getData().values[0]);

    }

}
