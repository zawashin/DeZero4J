### Step14：同じ変数を繰り返し使う

- Variableクラスの修正
    - backwardメソッド

```java
public class Variable {

    public void backward() {
        if (grad == null) {
            grad = new double[data.length];
            Arrays.fill(grad, 1.0);
        }
        ArrayList<Function> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
            Function f = funcList.remove(funcList.size() - 1);
            double[][] gys = new double[f.outputs.length][];
            for (int i = 0; i < f.outputs.length; i++) {
                gys[i] = f.outputs[i].grad;
            }
            double[][] gxs = f.backward(gys);
            if (gxs.length != f.inputs.length) {
                throw new IllegalStateException("Length of gradients and inputs do not match");
            }
            for (int i = 0; i < gxs.length; i++) {
                // 修正箇所
                if (f.inputs[i].grad == null) {
                    f.inputs[i].grad = gxs[i];
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
                }

                if (f.inputs[i].creator != null) {
                    funcList.add(f.inputs[i].creator);
                }
            }
        }
    }
}
```

