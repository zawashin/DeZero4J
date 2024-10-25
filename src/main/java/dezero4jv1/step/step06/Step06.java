package dezero4jv1.step.step06;

public class Step06 {
    public static void main(String[] args) {
        Square A = new Square();
        Exp B = new Exp();
        Square C = new Square();

        Variable x = new Variable(0.5);
        Variable a = A.forward(x);
        Variable b = B.forward(a);
        Variable y = C.forward(b);

        y.grad = 1.0;
        b.grad = C.backward(y.grad);
        System.out.println(b.grad);
        a.grad = B.backward(b.grad);
        System.out.println(a.grad);
    }

}
