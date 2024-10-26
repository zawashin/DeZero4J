package dezero4j.step.step24;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sphere {

    // $f(x, y) = x^2 + y^2$
    public Variable calc(Variable... xs) {
        return xs[0].square().plus(xs[1].square());
    }

    public static void main(String[] args) {
        Variable x = new Variable(2);
        Variable y = new Variable(2);
        Variable z = x.square().plus(y.square());
        System.out.println(z);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());

        x = new Variable(4);
        y = new Variable(3);
        Sphere sphere = new Sphere();
        z = sphere.calc(x, y);
        System.out.println(z);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }
}