package dezero4j.step.step04;

public class Step04 {
    public static double numericalDiff(Function f, Variable x, double eps) {
        Variable x0 = new Variable(x.getData() - eps);
        Variable x1 = new Variable(x.getData() + eps);
        Variable y0 = f.forward(x0);
        Variable y1 = f.forward(x1);
        return (y1.getData() - y0.getData()) / (2 * eps);
    }

    public static void main(String[] args) {
        Function f = new Square();
        Variable x = new Variable(2.0);
        double dy = numericalDiff(f, x, 1e-4);
        System.out.println(dy);

        Function f2 = new Function() {
            public double forward(double x) {
                Function A = new Square();
                Function B = new Exp();
                Function C = new Square();
                return C.forward(B.forward(A.forward(new Variable(x)))).getData();
            }
        };

        Variable x2 = new Variable(0.5);
        double dy2 = numericalDiff(f2, x2, 1e-4);
        System.out.println(dy2);
    }
}