package dezero4j.step.step41;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Matmul extends Function {

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[]{xs[0].dot(xs[1])};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        if (inputs[0].getRank() == 1) {
            double[][] values = new double[inputs[0].getLength()][gys[0].getLength()];
            // 行列積を計算
            for (int i = 0; i < inputs[0].getLength(); i++) {
                for (int j = 0; j < gys[0].getLength(); j++) {
                    values[i][j] = inputs[0].getValues()[i] * gys[0].getValues()[j];
                }
            }
            Variable gx1 = new Variable(values);
            return new Variable[]{
                    gys[0].matmul(inputs[1].transpose()), gx1};
        }

        return new Variable[]{
                gys[0].matmul(inputs[1].transpose()),
                inputs[0].transpose().matmul(gys[0])};
    }
}
