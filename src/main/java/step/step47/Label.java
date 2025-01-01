package step.step47;

import dezero4j.Variable;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Label {

    public static void main(String[] args) {
        Variable t1 = new Variable(new double[]{2, 0, 1, 0});
        Variable t2 = new Variable(new double[][]{{2, 0, 1, 0}});
        Variable t3 = new Variable(new double[][]{{2}, {0}, {1}, {0}});
        System.out.println(Arrays.toString(t1.getShape()));
        System.out.println(t1);
        System.out.println(Arrays.toString(t2.getShape()));
        System.out.println(t2);
        System.out.println(Arrays.toString(t3.getShape()));
        System.out.println(t3);
    }

}
