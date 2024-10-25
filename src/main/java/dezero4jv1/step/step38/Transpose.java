package dezero4jv1.step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Transpose extends Function {

    public Transpose() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        double[] x0 = xs[0].values;
        int length = x0.length;
        double[] values = new double[length];
        System.arraycopy(xs[0].values, 0, values, 0, length);
        int[] shape;
        int n;
        int num1, num2, num3, num4;
        switch (xs[0].rank) {
            case 0, 1:
                shape = xs[0].shape.clone();
                break;
            case 2:
                n = 0;
                shape = new int[]{xs[0].shape[1], xs[0].shape[0]};
                num1 = shape[0];
                num2 = shape[1];
                double[][] array2 = new double[num1][num2];
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        values[n++] = xs[0].getValue(j, i);
                    }
                }
                break;
            case 3:
                n = 0;
                shape = new int[]{xs[0].shape[2], xs[0].shape[1], xs[0].shape[0]};
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        for (int k = 0; k < num3; k++) {
                            values[n++] = xs[0].getValue(k, j, i);
                        }
                    }
                }
                break;
            case 4:
                n = 0;
                shape = new int[]{xs[0].shape[3], xs[0].shape[2], xs[0].shape[1], xs[0].shape[0]};
                num1 = shape[0];
                num2 = shape[1];
                num3 = shape[2];
                num4 = shape[3];
                for (int i = 0; i < num1; i++) {
                    for (int j = 0; j < num2; j++) {
                        for (int k = 0; k < num3; k++) {
                            for (int l = 0; l < num4; l++) {
                                values[n++] = xs[0].getValue(l, k, j, i);
                            }
                        }
                    }
                }
                break;
            default:
                throw new RuntimeException("Not implemented yet");
        }
        ys[0] = new Tensor(values, shape);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gx = new Variable[numInputs];
        gx[0] = gys[0].transpose();
        gx[0].setShape(inputs[0].getShape());
        return gx;
    }
}
