package step.step28;


import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Cos extends Function {

    @Override
    public Tensor[] forward(Tensor[] xs) {
        return new Tensor[]{xs[0].cos()};
    }

    @Override
    public Tensor[] backward(Tensor[] gys) {
        Tensor x = inputs[0].getData();
        return new Tensor[]{x.sin().multiply(gys[0]).neg()};
    }

}
