package dezero4jv1.step.step01;

public class Step01 {

    public static void main(String[] args) {
        double data = 1.0;
        Variable x = new Variable(data);
        System.out.println(x.getData());

        x.setData(2.0);
        System.out.println(x.getData());
    }
}