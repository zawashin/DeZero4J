package dezero4j.step.step18;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Test {
    public void print() {
        System.out.println(Config.enableBackprop);
    }

    public static void main(String[] args) {
        Config.enableBackprop = true;
        Test test1 = new Test();
        test1.print();
        Config.enableBackprop = false;
        Test test2 = new Test();
        test2.print();
    }
}
