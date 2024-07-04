package dezero4j.step.step01;

import java.util.Arrays;

public class Step01 {

    public static void main(String[] args) {
        double[] data = {1.0};
        Variable x = new Variable(data);
        System.out.println(Arrays.toString(x.getData()));

        x.setData(new double[]{2.0});
        System.out.println(Arrays.toString(x.getData()));
    }
}