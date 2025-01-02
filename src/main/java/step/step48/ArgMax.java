package step.step48;

import dezero4j.Variable;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ArgMax implements Serializable {

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

    public int[] calc(Variable... xs) {
        switch (xs[0].getRank()) {
            case 0:
                return new int[]{0};
            case 1:
                double max = xs[0].getValues()[0];
                int argmax = 0;
                for (int i = 1; i < xs[0].getLength(); i++) {
                    if (xs[0].getValues()[i] > max) {
                        max = xs[0].getValues()[i];
                        argmax = i;
                    }
                }
                return new int[]{argmax};
            case 2:
                shape0 = xs[0].getShape()[0];
                shape1 = xs[0].getShape()[1];
                double[] maxs;
                int[] argmaxs;
                switch (axis) {
                    case 0:
                        maxs = new double[shape1];
                        argmaxs = new int[shape1];
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
                        return argmaxs;
                    case 1:
                        maxs = new double[shape0];
                        argmaxs = new int[shape0];
                        for (int i = 0; i < shape0; i++) {
                            maxs[i] = xs[0].getValues()[i * shape1];
                            argmaxs[i] = 0;
                            for (int j = 1; j < shape1; j++) {
                                if (xs[0].getValues()[i * shape1 + j] > maxs[i]) {
                                    maxs[i] = xs[0].getValues()[i * shape1 + j];
                                    argmaxs[i] = j;
                                }
                            }
                        }
                        return argmaxs;
                    default:
                        break;
                }
                break;
            default:
                System.err.println("Rank " + xs[0].getRank() + " is not supported.");
                throw new IllegalArgumentException("Rank " + xs[0].getRank() + " is not supported.");
        }
        throw new IllegalArgumentException("Rank " + xs[0].getRank() + " is not supported.");
    }
}
