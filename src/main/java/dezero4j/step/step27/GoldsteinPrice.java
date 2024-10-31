package dezero4j.step.step27;

import tensor4j.Utils;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class GoldsteinPrice {

    private int[] shape;

    public static void main(String[] args) {
        /*
        Variable x = new Variable(new double[]{1, 1});
        Variable y = new Variable(new double[]{2, 1});

         */
        Variable x = new Variable(new double[]{1});
        Variable y = new Variable(new double[]{1});
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
        z = ((xs[0].plus(xs[1]).plus(1)).pow(2)).plus(1).times(constant(19).minus(constant(14).times(xs[0])).plus(constant(3).times(xs[0].pow(2))).minus(constant(14).times(xs[1])).plus(constant(6).times(xs[0]).times(xs[1])).plus(constant(3).times(xs[1].pow(2)))).times(constant(30).plus(constant(2).times(xs[0]).minus(constant(3).times(xs[1])).pow(2).times(constant(18).minus(constant(32).times(xs[0])).plus(constant(12).times(xs[0].pow(2))).plus(constant(48).times(xs[1])).minus(constant(36).times(xs[0]).times(xs[1])).plus(constant(27).times(xs[1].pow(2))))));
        return z;
    }

    private Variable constant(double value) {
        return new Variable(Utils.create(value, shape));
    }
}
