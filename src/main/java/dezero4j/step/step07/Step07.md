### Step07：バックプロパゲーションの自動化

- Variableクラス
  - 逆伝播の計算
    - フィールドにFunction creatorを追加
    - 勾配の計算
      - backwardメソッドを定義
  - フィールドをprivateに変更

```java
public class Variable {
    private double data;
    private double grad;
    private Function creator;

    // 略
    public void backward() {
        // 略
    }
    // 略
}


```

- Functionクラス
  - 逆伝播を計算のためにフィールドにinputを追加
    - Variable input
  - 逆伝播を計算するためのbackwardメソッドを定義

```java
public abstract class Function {
    protected Variable input;

    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        Variable output = new Variable(y);
        output.setCreator(this);
        this.input = input;
        this.output = output;
        return output;
    }

    protected abstract double forward(double x);

    protected abstract double backward(double gy);
}
```
