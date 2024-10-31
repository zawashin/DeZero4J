package dezero4j.step.step28;


import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sin extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].sin()};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor x = inputs[0].getData();
        return new Tensor[]{x.cos().times(gys[0])};
    }

}
