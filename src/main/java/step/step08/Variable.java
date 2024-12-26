package step.step08;

import java.util.ArrayList;
import java.util.List;

public class Variable {
    protected double data;
    protected double grad;
    protected Function creator;

    public Variable(double data) {
        this.data = data;
        this.grad = 0.0;
        this.creator = null;
    }

    public void backward() {
        List<Function> funcs = new ArrayList<>();
        funcs.add(this.creator);

        while (!funcs.isEmpty()) {
            Function f = funcs.remove(funcs.size() - 1);
            Variable x = f.getInput();
            Variable y = f.getOutput();
            x.setGrad(f.backward(y.getGrad()));

            if (x.getCreator() != null) {
                funcs.add(x.getCreator());
            }
        }
    }

    public double getData() {
        return this.data;
    }

    public void setData(double data) {
        this.data = data;
    }

    public double getGrad() {
        return this.grad;
    }

    public void setGrad(double grad) {
        this.grad = grad;
    }

    public Function getCreator() {
        return this.creator;
    }

    public void setCreator(Function func) {
        this.creator = func;
    }
}

