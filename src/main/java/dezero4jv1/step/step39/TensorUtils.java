package dezero4jv1.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TensorUtils {
    protected static final String[] ERROR_MESSAGE = new String[]{
            "Tensor Order is not 0th.",
            "Tensor Order is not 1st.",
            "Tensor Order is not 2nd.",
            "Tensor Order is not 3th.",
            "Tensor Order is not 4th.",
            "Over 5th Order Tensor is not implemented.",
            "Data Length is not correct"
    };
    protected static final String ERROR_RANK = ERROR_MESSAGE[5];
    protected static final String ERROR_LENGTH = ERROR_MESSAGE[6];

    public static String toString(Tensor tensor) {
        StringBuilder buffer = new StringBuilder();

        int n = 0;
        switch (tensor.rank) {
            case 0:
                buffer.append("[").append(tensor.values[0]).append("]");
                break;
            case 1:
                buffer.append("[");
                for (int i = 0; i < tensor.num1; i++) {
                    buffer.append(tensor.values[n++]);
                    if (i == tensor.num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",");
                    }
                }
                break;
            case 2:
                buffer.append("[");
                for (int i = 0; i < tensor.num1; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < tensor.num2; j++) {
                        buffer.append(tensor.values[n++]);
                        if (j == tensor.num2 - 1) {
                            buffer.append("]");
                        } else {
                            buffer.append(",");
                        }
                    }
                    if (i == tensor.num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                break;
            case 3:
                for (int i = 0; i < tensor.num1; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < tensor.num2; j++) {
                        for (int k = 0; k < tensor.num3; k++) {
                            buffer.append(tensor.values[n++]).append("\n");
                        }
                    }
                    if (i == tensor.num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                break;
            case 4:
                for (int i = 0; i < tensor.num1; i++) {
                    if (i == 0) {
                        buffer.append("[");
                    } else {
                        buffer.append(" [");
                    }
                    for (int j = 0; j < tensor.num2; j++) {
                        for (int k = 0; k < tensor.num3; k++) {
                            for (int l = 0; l < tensor.num4; l++) {
                                buffer.append(tensor.values[n++]).append("\n");
                            }
                        }
                    }
                    if (i == tensor.num1 - 1) {
                        buffer.append("]");
                    } else {
                        buffer.append(",\n");
                    }
                }
                break;
            default:
                throw new RuntimeException(ERROR_RANK);
        }
        return buffer.toString();
    }

    public static int index(Tensor tensor, int... indexes) {
        int index;
        switch (indexes.length) {
            case 1:
                if (tensor.rank != 1) {
                    throw new RuntimeException(ERROR_MESSAGE[1]);
                }
                index = indexes[0];
                break;
            case 2:
                if (tensor.rank != 2) {
                    throw new RuntimeException(ERROR_MESSAGE[2]);
                }
                index = indexes[0] * tensor.num2 + indexes[1];
                break;
            case 3:
                if (tensor.rank != 3) {
                    throw new RuntimeException(ERROR_MESSAGE[3]);
                }
                return indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[1] * tensor.num4;
            case 4:
                if (tensor.rank != 4) {
                    throw new RuntimeException(ERROR_MESSAGE[4]);
                }
                return indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[2] * tensor.num4 + indexes[3];
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public static int index(Tensor tensor, int i, int j) {
        if (tensor.rank != 2) {
            throw new RuntimeException(ERROR_MESSAGE[2]);
        }
        return i * tensor.num2 + j;
    }

    public static int index(Tensor tensor, int i, int j, int k) {
        if (tensor.rank != 3) {
            throw new RuntimeException(ERROR_MESSAGE[3]);
        }
        return i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4;
    }

    public static int index(Tensor tensor, int i, int j, int k, int l) {
        assert tensor.rank == 4 : "Tensor is not 4th order";
        return i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4 + l;
    }

    public static double getValue(Tensor tensor, int... indexes) {
        int index;
        switch (indexes.length) {
            case 1:
                index = indexes[0];
                break;
            case 2:
                assert tensor.rank == 2 : "Tensor is not Matrix";
                index = indexes[0] * tensor.num2 + indexes[1];
                break;
            case 3:
                assert tensor.rank == 3 : "Tensor is not 3rd order";
                return indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[1] * tensor.num4;
            case 4:
                assert tensor.rank == 4 : "Tensor is not 4th order";
                return indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[2] * tensor.num4 + indexes[3];
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        return index;
    }

    public static double getValue(Tensor tensor, int i, int j) {
        if (tensor.rank != 2) {
            throw new RuntimeException("Tensor is not Matrix");
        }
        return tensor.values[i * tensor.num2 + j];
    }

    public static double getValue(Tensor tensor, int i, int j, int k) {
        if (tensor.rank != 3) {
            throw new RuntimeException("Tensor is not 3rd order");
        }
        return tensor.values[i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4];
    }

    public static double getValue(Tensor tensor, int i, int j, int k, int l) {
        assert tensor.rank == 4 : "Tensor is not 4th order";
        return tensor.values[i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4 + l];
    }

    public static void setValue(Tensor tensor, double value, int... indexes) {
        int index;
        switch (indexes.length) {
            case 1:
                index = indexes[0];
                break;
            case 2:
                assert tensor.rank == 2 : "Tensor is not Matrix";
                index = indexes[0] * tensor.num2 + indexes[1];
                break;
            case 3:
                assert tensor.rank == 3 : "Tensor is not 3rd order";
                index = indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[1] * tensor.num4;
                break;
            case 4:
                assert tensor.rank == 4 : "Tensor is not 4th order";
                index = indexes[0] * tensor.num2x3x4 + indexes[1] * tensor.num3x4 + indexes[2] * tensor.num4 + indexes[3];
                break;
            default:
                throw new RuntimeException("No tensor above the forth order has been implemented.");
        }
        tensor.values[index] = value;
    }

    public static void setValue(Tensor tensor, double value) {
        if (tensor.rank != 0) {
            throw new RuntimeException(ERROR_MESSAGE[0]);
        }
        tensor.values[0] = value;
    }

    public void setValue(Tensor tensor, double value, int i) {
        tensor.values[i] = value;
    }

    public static void setValue(Tensor tensor, double value, int i, int j) {
        if (tensor.rank != 2) {
            throw new RuntimeException(ERROR_MESSAGE[2]);
        }
        tensor.values[i * tensor.num2 + j] = value;
    }

    public static void setValue(Tensor tensor, double value, int i, int j, int k) {
        if (tensor.rank != 3) {
            throw new RuntimeException(ERROR_MESSAGE[3]);
        }
        tensor.values[i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4] = value;
    }

    public static void setValue(Tensor tensor, double value, int i, int j, int k, int l) {
        if (tensor.rank != 4) {
            throw new RuntimeException(ERROR_MESSAGE[4]);
        }
        tensor.values[i * tensor.num2x3x4 + j * tensor.num3x4 + k * tensor.num4 + l] = value;
    }

    public static int[] indexes(Tensor tensor, int index) {
        int[] indexes;
        if (tensor.rank == 0) {
            indexes = new int[1];
            indexes[0] = 1;
        } else if (tensor.rank == 1) {
            indexes = new int[tensor.rank];
            indexes[0] = index;
        } else if (tensor.rank == 2) {
            indexes = new int[tensor.rank];
            indexes[0] = tensor.length / tensor.num2x3x4;
            indexes[1] = (tensor.length % tensor.num2x3x4) / tensor.num3x4;
        } else if (tensor.rank == 3) {
            indexes = new int[tensor.rank];
            indexes[0] = tensor.length / tensor.num2x3x4;
            indexes[1] = (tensor.length % tensor.num2x3x4) / tensor.num3x4;
            indexes[2] = ((tensor.length % tensor.num2x3x4) % tensor.num3x4) / tensor.num4;
        } else if (tensor.rank == 4) {
            indexes = new int[tensor.rank];
            indexes[0] = tensor.length / tensor.num2x3x4;
            indexes[1] = (tensor.length % tensor.num2x3x4) / tensor.num3x4;
            indexes[2] = ((tensor.length % tensor.num2x3x4) % tensor.num3x4) / tensor.num4;
            indexes[3] = ((tensor.length % tensor.num2x3x4) % (tensor.num3x4)) % tensor.num4;
        } else {
            throw new RuntimeException();
        }
        return indexes;
    }

}
