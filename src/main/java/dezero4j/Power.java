package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Power extends Function {

    @Serial
    private static final long serialVersionUID = -2797698397922827503L;
    private double index = 0;

    public Power(double index) {
        this.index = index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].pow(index)};
    }

    @Override
    public Variable[] backward(Variable[] gys) {
        /*
        Tensor x = inputs[0].getData();
        return new Tensor[]{x.pow(index - 1).multiply(gys[0]).multiply(index)};

         */
        Variable x = inputs[0];
        return new Variable[]{x.pow(index - 1).multiply(gys[0]).multiply(index)};
    }

}
