package dezero4j.step.step10;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SquareTest {
    @Test
    public void testForward() {
        Variable x = new Variable(new double[]{2.0});
        Square square = new Square();
        Variable y = square.forward(x);
        double[] expected = new double[]{4.0};
        assertEquals(y.getData()[0], expected[0], 1.0e-8);
    }

    @Test
    public void testBackward() {
        Variable x = new Variable(new double[]{3.0});
        Square square = new Square();
        Variable y = square.forward(x);
        y.backward();
        double[] expected = new double[]{6.0};
        assertEquals(x.getGrad()[0], expected[0], 1.0e-8);
    }

    @Test
    public void testGradientCheck() {
        Variable x = new Variable(new double[]{Math.random()});
        Square square = new Square();
        Variable y = square.forward(x);
        y.backward();
        double numGrad = numericalDiff(square, x, 1.0e-4);
        boolean flg = Math.abs(x.getGrad()[0] - numGrad) < 1e-4;
        assertTrue(flg);
    }

    public double numericalDiff(AbstractFunction f, Variable x, double eps) {
        Variable x0 = new Variable(new double[]{x.getData()[0] - eps});
        Variable x1 = new Variable(new double[]{x.getData()[0] + eps});
        Variable y0 = f.forward(x0);
        Variable y1 = f.forward(x1);
        return (y1.getData()[0] - y0.getData()[0]) / (2 * eps);
    }
}