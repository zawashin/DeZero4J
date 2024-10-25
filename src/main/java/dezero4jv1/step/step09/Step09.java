package dezero4jv1.step.step09;

public class Step09 {
    public static void main(String[] args) {
        Variable x = new Variable(new double[]{0.5});
        Variable y = new Square().forward(new Exp().forward(new Square().forward(x)));
        y.backward();
        System.out.println(x.getGrad()[0]);
    }
}