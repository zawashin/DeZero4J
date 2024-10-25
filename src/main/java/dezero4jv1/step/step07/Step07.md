### Step07：バックプロパゲーションの自動化

- Variableクラス
  - 逆伝播の計算
    - creatorフィールドを追加
    - backwardメソッドを定義

```java
public class Variable {
    protected double data;
    protected double grad;
    protected Function creator;

    // ... 略
    public void backward() {
        // ... 略
    }
    // ... 略
}
```

- Functionクラス
  - 逆伝播計算自動化するのためにoutputフィールドを追加
  - 逆伝播を計算するためのbackwardメソッドを定義

```java
public abstract class Function {
    protected Variable input;
  protected Variable output;

    public Variable forward(Variable input) {
      // ... 略
    }

    protected abstract double forward(double x);
    protected abstract double backward(double gy);
}
```
