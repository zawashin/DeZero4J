package dezero4jv1.step.step27;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sphere {
    public Variable forward(Variable... xs) {
        return xs[0].square().plus(xs[1].square());
    }

    public static void main(String[] args) {
        Variable x = new Variable(2);
        Variable y = new Variable(2);
        Variable z = x.square().plus(y.square());
        z.backward();
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);

        x = new Variable(4);
        y = new Variable(3);
        Sphere sphere = new Sphere();
        z = sphere.forward(x, y);
        z.backward();
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);
    }
}
