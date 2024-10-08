### Step07：バックプロパゲーションの自動化

- Variableクラス
  - 逆伝播の計算
    - creatorフィールドを追加
      - Function creator
    - backwardメソッドを定義
  - フィールドをprivateに変更

```java
public class Variable {
    private double data;
    private double grad;
    private Function creator;

    // ... 略
    public void backward() {
        // ... 略
    }
    // ... 略
}


```

- Functionクラス
  - 逆伝播を計算のためにinputフィールドとoutputフィールドを追加
    - Variable input
    - Variable output
  - 逆伝播を計算するためのbackwardメソッドを定義

```java
public abstract class Function {
    protected Variable input;

    public Variable forward(Variable input) {
      // ... 略
    }

    protected abstract double forward(double x);
    protected abstract double backward(double gy);
}
```
