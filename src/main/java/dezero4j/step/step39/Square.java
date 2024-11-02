package dezero4j.step.step39;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].square()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        /*
        Tensor x = getInputs()[0].getData();
        return new Tensor[]{x.times(gys[0]).times(2)};

         */
        Variable x = getInputs()[0];
        return new Variable[]{x.times(gys[0]).times(2)};
    }
}
