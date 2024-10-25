package dezero4jv1.step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Or {

    public static void main(String[] args) {
        double[][] xArray = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] yArray = {{0}, {1}, {1}, {1}};
        Variable[] xs = new Variable[1];
        Variable x = new Variable(xArray);
        xs[0] = x;
        Variable y0 = new Variable(yArray);

        int numInputs = 1;
        int numHiddens = 10;
        int numOutputs = 1;
        Model model = new Model();
        model.addLayer(numHiddens);
        model.addLayer(numOutputs);

        double learningRate = 0.2;
        int iters = 10000;
        //iters = 2;
        for (int i = 0; i < iters; i++) {
            Variable y = model.predict(xs);
            Variable loss = y.mse(y0);
            if (i % 100 == 0) {
                System.out.println("Loss[" + i + "] = " + loss);
                System.gc();
            }
            model.clearGrads();
            loss.backward(false, true);

            model.update(learningRate);
        }
        Variable y = model.predict(xs);
        for (int i = 0; i < y.getLength(); i++) {
            System.out.println(x.getData().getValue(i, 0) + ", " + x.getData().getValue(i, 1) + "\t" + y.getData().getValues()[i] + "\t" + y0.getData().getValues()[i]);
        }

    }

}
