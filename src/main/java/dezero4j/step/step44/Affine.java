package dezero4j.step.step44;

import net.goui.util.MTRandom;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Affine extends Layer{

    boolean bias = true;

    public Affine(int numOutputs) {
        super(numOutputs);
        function = new Linear();
    }

    public Affine(int numInputs, int numOutputs) {
        this(numInputs, numOutputs, true);
    }

    public Affine(int numInputs, int numOutputs, boolean bias) {
        super(numInputs, numOutputs);
        if(!bias) {
            numParams = 2;
            params = new Parameter[numParams];
            params[0] = new Parameter(TensorUtils.createRandomTensor(numInputs, numOutputs));
            params[1] = new Parameter(new double[numOutputs]);
        } else {
            numParams = 1;
            params = new Parameter[numParams];
            params[0] = new Parameter(TensorUtils.createRandomTensor(numInputs, numOutputs));
        }
        initialized = true;
        function = new Linear();
    }

    public Variable[] forward(Variable[] xs) {
        if(!initialized) {
            numInputs = xs[0].getShape()[1];
            if(bias) {
                numParams = 2;
                params = new Parameter[numParams];
                params[0] = new Parameter(TensorUtils.createRandomTensor(numInputs, numOutputs));
                params[1] = new Parameter(new double[numOutputs]);
            } else {
                numParams = 1;
                params = new Parameter[numParams];
                params[0] = new Parameter(TensorUtils.createRandomTensor(numInputs, numOutputs));
            }
            initialized = true;
        }
        if(bias) {
            return function.forward(xs[0], params[0], params[1]);
        } else {
            return function.forward(xs[0], params[0]);
        }
    }

    public static void main(String[] args) {
        Random random = new MTRandom(System.currentTimeMillis());
        int n = 100;
        double[][] xArray = new double[n][1];
        double[][] yArray = new double[n][1];
        for (int i = 0; i < xArray.length; i++) {
            /*
            xArray[i][0] = random.nextDouble();
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]) + random.nextDouble();

             */
            xArray[i][0] = (double) i / n;
            yArray[i][0] = Math.sin(2.0 * Math.PI * xArray[i][0]);
        }
        Variable[] xs = new Variable[1];
        Variable x = new Variable(xArray);
        xs[0] = x;
        Variable y0 = new Variable(yArray);

        int numInputs = 1;
        int numHiddens = 10;
        int numOutputs = 1;

        /*
        LinearLayer layer1 = new LinearLayer(numInputs, numHiddens);
        LinearLayer layer2 = new LinearLayer(numHiddens, numOutputs);
         */

        Affine layer1 = new Affine(numHiddens);
        Affine layer2 = new Affine(numOutputs);

        double learningRate = 0.2;
        int iters = 50000;
        //iters = 2;
        Function f2 = new Sigmoid();
        for (int i = 0; i < iters; i++) {
            Variable[] ys1 = layer1.forward(xs);
            Variable[] ys2 = f2.forward(ys1);
            Variable[] ys3 = layer2.forward(ys2);
            Variable y = ys3[0];
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
            }
            layer1.clearGrads();
            layer2.clearGrads();
            loss.backward(false, true);

            layer1.update(learningRate);
            layer2.update(learningRate);
        }
        Variable[] ys1 = layer1.forward(xs);
        System.out.println(ys1.length);
        Variable[] ys2 = f2.forward(ys1);
        Variable[] ys3 = layer2.forward(ys2);
        Variable y = ys3[0];
        for(int i = 0; i < x.getLength(); i++) {
            System.out.println(x.getData().getValues()[i] + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }
        /*
         */

    }

}
