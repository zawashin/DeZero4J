package dezero4j.step.step02;

import java.util.Arrays;

public class  Step02 {
    public static void main(String[] args) {
        double[] data = {10};
        Variable x = new Variable(data);
        Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getClass());
        System.out.println(Arrays.toString(y.getData()));
    }
}