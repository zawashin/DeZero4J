package dezero4j.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sphere extends LostFunction {
    public static void main(String[] args) {
        Variable x = new Variable(1);
        Variable y = new Variable(1.0);
        Sphere sphere = new Sphere();
        Variable z = sphere.calc(x, y);
        z.backward(true, true);
        System.out.println(x.getGrad().getData().values[0]);
        System.out.println(y.getGrad().getData().values[0]);

    }

    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getLength();
        lost[0] = xs[0].pow(2).plus(xs[1].pow(2));
        return lost;
    }
}
