package dezero4j.step.step04;

public class Step04 {
    public static double numerical_diff(AbstractFunction f, Variable x, double eps) {
        Variable x0 = new Variable(x.getData() - eps);
        Variable x1 = new Variable(x.getData() + eps);
        Variable y0 = f.forward(x0);
        Variable y1 = f.forward(x1);
        return (y1.getData() - y0.getData()) / (2 * eps);
    }

    public static void main(String[] args) {
        Square f = new Square();
        Variable x = new Variable(2.0);
        double dy = numerical_diff(f, x, 1e-4);
        System.out.println(dy);

        AbstractFunction f2 = new AbstractFunction() {
            public double forward(double x) {
                AbstractFunction A = new Square();
                AbstractFunction B = new Exp();
                AbstractFunction C = new Square();
                return C.forward(B.forward(A.forward(new Variable(x)))).getData();
            }
        };

        Variable x2 = new Variable(0.5);
        double dy2 = numerical_diff(f2, x2, 1e-4);
        System.out.println(dy2);
    }
}