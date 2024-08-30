package tensor4j;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TensorTest {
    public static void main(String[] args) {
        double[][][] array = new double[2][3][4];
        System.out.println("3D");
        Tensor tensor = new Tensor(array);
        System.out.println(array.length);
        System.out.println(array[0].length);
        System.out.println(array[0][0].length);
        System.out.println(tensor.getRows());
        System.out.println(tensor.getCols());
        System.out.println(tensor.getDepth());
        System.out.println(tensor.getFourthDim());
        double[] vector = new double[4];
        tensor = new Tensor(vector);
        System.out.println("1D");
        System.out.println(tensor.getRows());
        System.out.println(tensor.getCols());
        System.out.println(tensor.getDepth());
        System.out.println(tensor.getFourthDim());
        double[][] matrix = new double[3][4];
        System.out.println("2D");
        tensor = new Tensor(matrix);
        System.out.println(tensor.getRows());
        System.out.println(tensor.getCols());
        System.out.println(tensor.getDepth());
        System.out.println(tensor.getFourthDim());
    }

}
