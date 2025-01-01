package step.step48;

import dezero4j.Function;
import dezero4j.Variable;
import tensor4j.Tensor;
import tensor4j.Utils;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ArgMax extends Function {

    @Serial
    private static final long serialVersionUID = 7054512535089939613L;
    private final int axis;
    int shape0, shape1;

    public ArgMax() {
        this.axis = 1;
    }

    public ArgMax(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        switch (xs[0].getRank()) {
            case 0:
                return new Tensor[]{Utils.create(0)};
            case 1:
                double max = xs[0].getValues()[0];
                int argmax = 0;
                for (int i = 1; i < shape1; i++) {
                    if (xs[0].getValues()[i] > max) {
                        max = xs[0].getValues()[i];
                        argmax = i;
                    }
                }
                return new Tensor[]{Utils.create(argmax)};
            case 2:
                shape0 = xs[0].getShape(0);
                shape1 = xs[0].getShape(1);
                switch (axis) {
                    case 0:
                        double[] maxs = new double[shape1];
                        int[] argmaxs = new int[shape1];
                        for (int i = 0; i < shape1; i++) {
                            maxs[i] = xs[0].getValues()[i];
                            argmaxs[i] = 0;
                            for (int j = 1; j < shape0; j++) {
                                if (xs[0].getValues()[j * shape1 + i] > maxs[i]) {
                                    maxs[i] = xs[0].getValues()[j * shape1 + i];
                                    argmaxs[i] = j;

                                }
                            }
                        }
                        return new Tensor[]{Utils.create(argmaxs)};
                    case 1:
                        double[] maxs1 = new double[shape0];
                        int[] argmaxs1 = new int[shape0];
                        for (int i = 0; i < shape0; i++) {
                            maxs1[i] = xs[0].getValues()[i * shape1];
                            argmaxs1[i] = 0;
                            for (int j = 1; j < shape1; j++) {
                                if (xs[0].getValues()[i * shape1 + j] > maxs1[i]) {
                                    maxs1[i] = xs[0].getValues()[i * shape1 + j];
                                    argmaxs1[i] = j;
                                }
                            }
                        }
                        return new Tensor[]{Utils.create(argmaxs1)};
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[0];
    }
}
