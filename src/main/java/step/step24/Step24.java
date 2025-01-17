package step.step24;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step24 extends Step {

    public static void main(String[] args) {
        new Step24().calc();
    }

    @Override
    public void calc() {
        {
            Variable x = new Variable(2);
            Variable y = new Variable(2);
            Variable z = x.square().add(y.square());
            System.out.println(z);
            z.backward();
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());

        }
        {
            Variable x = new Variable(1);
            Variable y = new Variable(1);
            Matyas matyas = new Matyas();
            Variable z = matyas.calc(x, y);
            System.out.println(z);
            z.backward();
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());
        }
        {
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
    }
}
