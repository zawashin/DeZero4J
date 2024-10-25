package dezero4jv1.step.step12;

public class Step12 {
    public static void main(String[] args) {
        Variable x0 = new Variable(new double[]{2});
        Variable x1 = new Variable(new double[]{3});
        Variable[] xs = new Variable[]{x0, x1};

        Function f = new Plus();
        Variable[] ys = f.forward(xs[0], xs[1]);
        Variable y = ys[0];

        y.backward();

        System.out.println(y.getData()[0]);
    }
}