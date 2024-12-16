package dezero4j.step.step24;

import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class GoldsteinPrice {

    private int[] shape;

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{1, 1});
        Variable y = new Variable(new double[]{2, 1});

        /*
        Variable x = new Variable(new double[]{1});
        Variable y = new Variable(new double[]{1});
         */
        GoldsteinPrice goldstein = new GoldsteinPrice();
        Variable z = goldstein.calc(x, y);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }

    public Variable calc(Variable... xs) {
        shape = xs[0].getShape();
        /*
        val z = (1 + (xs[0] + xs[1] + 1).pow(2) * (19 - 14*xs[0] + 3*xs[0].pow(2) - 14*xs[1] + 6*xs[0]*xs[1] + 3*xs[1].pow(2))) *
                (30 + (2*xs[0] - 3*xs[1]).pow(2) * (18 - 32*xs[0] + 12*xs[0].pow(2) + 48*xs[1] - 36*xs[0]*xs[1] + 27*xs[1].pow(2)))
         */
        Variable z = null;
        z = ((xs[0].add(xs[1]).add(1)).pow(2)).add(1).multiply(constant(19).subtract(constant(14).multiply(xs[0])).add(constant(3).multiply(xs[0].pow(2))).subtract(constant(14).multiply(xs[1])).add(constant(6).multiply(xs[0]).multiply(xs[1])).add(constant(3).multiply(xs[1].pow(2)))).multiply(constant(30).add(constant(2).multiply(xs[0]).subtract(constant(3).multiply(xs[1])).pow(2).multiply(constant(18).subtract(constant(32).multiply(xs[0])).add(constant(12).multiply(xs[0].pow(2))).add(constant(48).multiply(xs[1])).subtract(constant(36).multiply(xs[0]).multiply(xs[1])).add(constant(27).multiply(xs[1].pow(2))))));
        return z;
    }

    private Variable constant(double value) {
        return new Variable(Utils.fill(value, shape));
    }
}
