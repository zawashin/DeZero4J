package step.step07;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Variable {
    protected double data;
    protected double grad;
    protected Function creator;

    public Variable(double data) {
        this.data = data;
        this.grad = 0.0;
        this.creator = null;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }

    public void backward() {
        Function f = this.creator;
        if (f != null) {
            Variable x = f.getInput();
            x.setGrad(f.backward(this.grad));
            x.backward();
        }
    }

    public double getData() {
        return this.data;
    }

    public double getGrad() {
        return this.grad;
    }

    public void setGrad(double grad) {
        this.grad = grad;
    }
}
