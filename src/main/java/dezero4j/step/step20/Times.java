package dezero4j.step.step20;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Times extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].times(xs[1])};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor[] xs = new Tensor[]{inputs[0].getData(), inputs[1].getData()};
        /*
        for (int i = 0; i < inputs[0].getData().length; i++) {
            gx[0][i] = gys[0][i] * xs[1][i];
            gx[1][i] = gys[0][i] * xs[0][i];
        }

         */
        return new Tensor[]{xs[1].times(gys[0]), xs[0].times(gys[0])};
        //return new Tensor[]{ inputs[1].getData().times(gys[0]), inputs[0].getData().times(gys[0]) };
    }

}
