package step.step43;

import tensor4j.Tensor;

import java.util.Arrays;

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
        Variable[] gxs = new Variable[2];
        if (inputs[0].getRank() == 1) {
            // ドット積ではなく N x Nの行列
            double[][] values = new double[inputs[0].getLength()][gys[0].getLength()];
            // 行列積を計算
            for (int i = 0; i < inputs[0].getLength(); i++) {
                for (int j = 0; j < gys[0].getLength(); j++) {
                    values[i][j] = inputs[0].getValues()[i] * gys[0].getValues()[j];
                }
            }
            gxs[0] = gys[0].matmul(inputs[1].transpose());
            gxs[1] = new Variable(values);
        } else {
            gxs[0] = gys[0].matmul(inputs[1].transpose());
            gxs[1] = inputs[0].transpose().matmul(gys[0]);
        }
        if (!Arrays.equals(gxs[0].getShape(), inputs[0].getShape())) {
            gxs[0] = gxs[0].reshape(inputs[0].getShape());
        }
        if (!Arrays.equals(gxs[1].getShape(), inputs[1].getShape())) {
            gxs[1] = gxs[1].reshape(inputs[1].getShape());
        }
        return gxs;
    }
}
