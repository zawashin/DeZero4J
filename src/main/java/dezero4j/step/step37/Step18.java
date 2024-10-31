package dezero4j.step.step37;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step18 extends Step {

    public static void main(String[] args) {
        new Step18().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{2.0});
        Variable a = x.square();
        Variable y = a.square().plus(a.square());
        //Config.enableBackprop = false;
        y.backward();

        System.out.println(y);
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }
}
