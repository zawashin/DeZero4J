package step.step47;

import dezero4j.Function;
import dezero4j.Softmax;
import dezero4j.SoftmaxCrossEntropy;
import dezero4j.Variable;
import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step47 extends Step {

    public static void main(String[] args) {
        Step step = new Step47();
        step.calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[][]{{-0.61505778, -0.42790161, 0.31733289},
                {-0.76395313, -0.2497645, 0.18591382},
                {-0.52006391, -0.96254612, 0.57818938},
                {-0.94252164, -0.50307479, 0.17576323}});
        //Variable t = new Variable(new double[]{2, 0, 1, 0});
        //Variable t = new Variable(new double[][]{{2, 0, 1, 0}});
        Variable t = new Variable(new double[][]{{2}, {0}, {1}, {0}});

        Function f = new SoftmaxCrossEntropy();
        Variable loss = f.forward(x, t)[0];
        System.out.println(loss);
        loss.backward();
        System.out.println(x.getGrad());
        f = new Softmax();
        Variable y = f.forward(x)[0];
        System.out.println(y);
    }
    /*
    variable(1.4967442524053063)
    variable([[ 0.0526716   0.06351223 -0.11618383]
              [-0.20245021  0.07951662  0.12293359]
              [ 0.05386349 -0.21539595  0.16153246]
              [-0.20544824  0.06913758  0.13631066]])
     */
}
