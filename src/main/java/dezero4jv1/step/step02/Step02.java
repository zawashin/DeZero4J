package dezero4jv1.step.step02;

public class  Step02 {
    public static void main(String[] args) {
        double data = 10;
        Variable x = new Variable(data);
        Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getData());
    }
}