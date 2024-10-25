package dezero4jv1.step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Goldstein extends LostFunction {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{1});
        Variable y = new Variable(new double[]{1});
        Goldstein goldstein = new Goldstein();
        Variable z = goldstein.calc(x, y);
        z.backward(false, true);
        System.out.println(x.getGradAsArray()[0]);
        System.out.println(y.getGradAsArray()[0]);
    }

    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getLength();
        /*
        Variable z;
        val z = (1 + (xs[0] + xs[1] + 1).pow(2) * (19 - 14*xs[0] + 3*xs[0].pow(2) - 14*xs[1] + 6*xs[0]*xs[1] + 3*xs[1].pow(2))) *
                (30 + (2*xs[0] - 3*xs[1]).pow(2) * (18 - 32*xs[0] + 12*xs[0].pow(2) + 48*xs[1] - 36*xs[0]*xs[1] + 27*xs[1].pow(2)))

         */
        //z = new Variable(length, 1).plus(xs[0].plus(xs[1]).plus(new Variable(length, 1)).pow(2).times(new Variable(length, 19).minus(new Variable(length, 14).times(xs[0])).plus(new Variable(length, 3).times(xs[0].pow(2))).minus(new Variable(length, 14).times(xs[1])).plus(new Variable(length, 6).times(xs[0]).times(xs[1])).plus(new Variable(length, 3).times(xs[1].pow(2))))).times(new Variable(length, 30).plus(new Variable(length, 2).times(xs[0]).minus(new Variable(length, 3).times(xs[1])).pow(2).times(new Variable(length, 18).minus(new Variable(length, 32).times(xs[0])).plus(new Variable(length, 12).times(xs[0].pow(2))).plus(new Variable(length, 48).times(xs[1])).minus(new Variable(length, 36).times(xs[0]).times(xs[1])).plus(new Variable(length, 27).times(xs[1].pow(2))))));
        lost[0] = param(length, 1).plus(xs[0].plus(xs[1]).plus(param(length, 1)).pow(2).times(param(length, 19).minus(param(length, 14).times(xs[0])).plus(param(length, 3).times(xs[0].pow(2))).minus(param(length, 14).times(xs[1])).plus(param(length, 6).times(xs[0]).times(xs[1])).plus(param(length, 3).times(xs[1].pow(2))))).times(param(length, 30).plus(param(length, 2).times(xs[0]).minus(param(length, 3).times(xs[1])).pow(2).times(param(length, 18).minus(param(length, 32).times(xs[0])).plus(param(length, 12).times(xs[0].pow(2))).plus(param(length, 48).times(xs[1])).minus(param(length, 36).times(xs[0]).times(xs[1])).plus(param(length, 27).times(xs[1].pow(2))))));
        return lost;
    }
}
