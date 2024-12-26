package step.step06;

public class Variable {
    protected double data;
    protected double grad;

    public Variable(double data) {
        this.data = data;
        this.grad = 0.0;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public double getGrad() {
        return grad;
    }

    public void setGrad(double grad) {
        this.grad = grad;
    }
}

