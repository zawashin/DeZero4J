package dezero4j.step.step10;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step10 {
    public static void main(String[] args) {
        SquareTest test =  new SquareTest();
        test.testForward();
        test.testBackward();
        //test.numericalDiff();
        test.testGradientCheck();
    }
}
