package step.step46;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Optimizer {

    protected Model target;
    protected List<Function> hookFunction = new ArrayList<>();

    public Optimizer(Model target) {
        this.target = target;
    }

    public abstract void update();

    public void addHook(Function f) {
        hookFunction.add(f);
    }
}
