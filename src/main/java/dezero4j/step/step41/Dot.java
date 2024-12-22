package dezero4j.step.step41;

import tensor4j.Tensor;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dot extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[1];
        ys[0] = xs[0].dot(xs[1]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[2];
        System.out.println(Arrays.toString(gys[0].getShape()));
        System.out.println(Arrays.toString(inputs[0].transpose().getShape()));
        System.out.println(Arrays.toString(inputs[1].transpose().getShape()));
        gxs[0] = gys[0].dot(inputs[1].transpose());
        System.out.println(gxs[0]);
        gxs[1] = (inputs[0].transpose()).dot(gys[0]);
        System.out.println(gxs[1]);
        return gxs;
    }
}
