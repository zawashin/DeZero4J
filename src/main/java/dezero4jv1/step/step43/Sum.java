package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sum extends Function {

    private int axis = TensorUtils.RANK_MAX;

    public Sum() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Sum(int axis) {
        this();
        this.axis = axis;
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = xs[0].sum(axis);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].broadcastTo(inputs[0].getShape());
        return gxs;
    }

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        Variable x;
        Variable y;
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[0] = x;
        System.out.println("x");
        System.out.println(x);
        Function f = new Sum();
        y = f.forward(xs)[0];
        System.out.println("y");
        System.out.println("axis *:");
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x.clearGrad();
        f = new Sum(0);
        y = f.forward(xs)[0];
        System.out.println("axis 0:");
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
        System.out.println("axis 1:");
        f = new Sum(1);
        y = f.forward(xs)[0];
        System.out.println(y);
        x.clearGrad();
        y.backward(false, true);
        System.out.println(x.grad);
    }

}
