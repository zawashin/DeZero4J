package dezero4jv1.step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Softmax extends ActivationFunction {

    int axis = 1;

    public Softmax() {
        numInputs = 1;
        numOutputs  = 1;
    }

    public Softmax(int axis) {
        this();
        setAxis(axis);
    }


    public void setAxis(int axis) {
        this.axis = axis;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numInputs];
        double[] values;
        if(axis==1) {
            values = new double[xs[0].getShape()[1]];
            double sum = 0.0;
            for(int i = 0; i < xs[0].length; i++) {
                sum += Math.exp(xs[0].getValues()[i]);
            }
            for(int i = 0; i < xs[0].length; i++) {
                values[i] = Math.exp(xs[0].getValues()[i])/sum;
            }
            ys[0] = new Tensor(values, xs[0].getShape());
        } else if (axis==0) {
            values = new double[xs[0].getShape()[0]];
            double sum = 0.0;
            //double max =
            for(int i = 0; i < xs[0].length; i++) {
                sum += Math.exp(xs[0].getValues()[i]);
            }
            for(int i = 0; i < xs[0].length; i++) {
                values[i] = Math.exp(xs[0].getValues()[i])/sum;
            }
            ys[0] = new Tensor(values, xs[0].getShape());
            /*
        } else {

             */
        }
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        /*
        y = self.outputs[0]()
        gx = y * gy
        sumdx = gx.sum(axis=self.axis, keepdims=True)
        gx -= y * sumdx
        return gx

         */
        Variable[] gxs = new Variable[numInputs];

        if(axis==1) {

        } else if (axis==0) {

        } else {

        }
        //gxs[0] = new Variable();
        return gxs;
    }
}
