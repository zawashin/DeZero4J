package dezero4j.step.step33_2;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Div extends Function {
    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].div(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] xs = inputs;
        /*
        Tensor[] xs = new Tensor[]{inputs[0].getData(), inputs[1].getData()};
        for (int i = 0; i < inputs[0].getData().length; i++) {

            gx[0][i] = gys[0] / xs[0];
            gx[1][i] = -gys[0] * (xs[0] / Math.pow(xs[1], 2));
        }
         */
        return new Variable[]{gys[0].div(xs[0]), gys[0].neg().times(xs[0].div(xs[1].square()))};
    }

}
