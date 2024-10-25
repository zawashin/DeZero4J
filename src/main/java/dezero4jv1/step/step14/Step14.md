### Step14：同じ変数を繰り返し使う

- Variableクラス
  - backwardメソッドの修正

```java
public class Variable {
  // ... 略
    public void backward() {
      // ... 略
        ArrayList<Function> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
          // ... 略
            for (int i = 0; i < gxs.length; i++) {
                // 修正箇所
                if (f.inputs[i].grad == null) {
                    f.inputs[i].grad = gxs[i];
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
                }
                // ここまで
              // ... 略
            }
        }
    }
}
```

