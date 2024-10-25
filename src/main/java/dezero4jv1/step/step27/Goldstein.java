package dezero4jv1.step.step27;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Goldstein extends MultivariateFunction {
    public Variable forward(Variable... xs) {
        /*
        val z = (1 + (xs[0] + xs[1] + 1).pow(2) * (19 - 14*xs[0] + 3*xs[0].pow(2) - 14*xs[1] + 6*xs[0]*xs[1] + 3*xs[1].pow(2))) *
                (30 + (2*xs[0] - 3*xs[1]).pow(2) * (18 - 32*xs[0] + 12*xs[0].pow(2) + 48*xs[1] - 36*xs[0]*xs[1] + 27*xs[1].pow(2)))
                
         */
        Variable z;
        int length = xs[0].getData().length;
        //z = new Variable(length, 1).plus(xs[0].plus(xs[1]).plus(new Variable(length, 1)).pow(2).times(new Variable(length, 19).minus(new Variable(length, 14).times(xs[0])).plus(new Variable(length, 3).times(xs[0].pow(2))).minus(new Variable(length, 14).times(xs[1])).plus(new Variable(length, 6).times(xs[0]).times(xs[1])).plus(new Variable(length, 3).times(xs[1].pow(2))))).times(new Variable(length, 30).plus(new Variable(length, 2).times(xs[0]).minus(new Variable(length, 3).times(xs[1])).pow(2).times(new Variable(length, 18).minus(new Variable(length, 32).times(xs[0])).plus(new Variable(length, 12).times(xs[0].pow(2))).plus(new Variable(length, 48).times(xs[1])).minus(new Variable(length, 36).times(xs[0]).times(xs[1])).plus(new Variable(length, 27).times(xs[1].pow(2))))));
        z = constant(length, 1).plus(xs[0].plus(xs[1]).plus(constant(length, 1)).pow(2).times(constant(length, 19).minus(constant(length, 14).times(xs[0])).plus(constant(length, 3).times(xs[0].pow(2))).minus(constant(length, 14).times(xs[1])).plus(constant(length, 6).times(xs[0]).times(xs[1])).plus(constant(length, 3).times(xs[1].pow(2))))).times(constant(length, 30).plus(constant(length, 2).times(xs[0]).minus(constant(length, 3).times(xs[1])).pow(2).times(constant(length, 18).minus(constant(length, 32).times(xs[0])).plus(constant(length, 12).times(xs[0].pow(2))).plus(constant(length, 48).times(xs[1])).minus(constant(length, 36).times(xs[0]).times(xs[1])).plus(constant(length, 27).times(xs[1].pow(2))))));
        return z;
    }

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{1,1});
        Variable y = new Variable(new double[]{2,1});
        Goldstein goldstein = new Goldstein();
        Variable z = goldstein.forward(x, y);
        z.backward();
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);
        System.out.println(x.getGrad()[1]);
        System.out.println(y.getGrad()[1]);
    }
}
