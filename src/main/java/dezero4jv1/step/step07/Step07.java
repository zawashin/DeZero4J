package dezero4jv1.step.step07;

public class Step07 {
    public static void main(String[] args) {
        Square A = new Square();
        Exp B = new Exp();
        Square C = new Square();

        Variable x = new Variable(0.5);
        Variable a = A.forward(x);
        Variable b = B.forward(a);
        Variable y = C.forward(b);

        // backward
        y.setGrad(1.0);
        y.backward();
        System.out.println(x.getGrad());
    }
}