package dezero4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Optimizer implements Serializable {

    @Serial
    private static final long serialVersionUID = -5608307613468496909L;
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
