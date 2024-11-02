package dezero4j.step.step38;

import dezero4j.step.Step;

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
            System.out.println("Sphere");
            Variable x = new Variable(2);
            Variable y = new Variable(2);
            Variable z = x.square().plus(y.square());
            System.out.println(z);
            z.backward();
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());

        }
        {
            System.out.println("Matyas");
            Variable x = new Variable(new double[]{1});
            Variable y = new Variable(new double[]{1});
            Matyas matyas = new Matyas();
            Variable z = matyas.calc(x, y);
            System.out.println(z);
            z.backward();
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());
        }
        {
            System.out.println("Goldstein-Price");
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
