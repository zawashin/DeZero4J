package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step38 {

    public static void main(String[] args) {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("x");
        System.out.println(x);
        Variable yr = x.reshape(new int[]{6});
        System.out.println("Reshape x");
        System.out.println(yr);
        yr.backward(false, true);
        System.out.println("Derivative of Reshape x");
        System.out.println(x.grad);
        x.clearGrad();
        //x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}});
        Variable yt = x.transpose();
        System.out.println("Trans x");
        System.out.println(yt);
        yt.backward(false, true);
        System.out.println("Derivative of Trans x");
        System.out.println(x.grad);
    }
}
