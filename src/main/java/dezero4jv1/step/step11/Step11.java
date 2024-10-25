package dezero4jv1.step.step11;

public class Step11 {
    public static void main(String[] args) {
        Variable x0 = new Variable(new double[]{2});
        Variable x1 = new Variable(new double[]{3});
        Variable[] xs = new Variable[]{x0, x1};

        Function f = new Plus();
        Variable[] ys = f.forward(xs);
        Variable y = ys[0];

        y.backward();

        System.out.println(y.getData()[0]);
    }
}