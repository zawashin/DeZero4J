### Step06：手作業によるバックプロパゲーション
- Variableクラス
  - 勾配を表すgradフィールドを追加
```java
public class Variable {
  protected double data;
  protected double grad;
    // ... 略
}
```

- Functionクラス
  - 逆伝播を計算のためにinputフィールドを追加
  - 逆伝播を計算するためのbackwardメソッドを定義
```java
public abstract class Function {
  protected Variable input;

  public abstract Variable forward(Variable input);
  protected abstract double forward(double x);
  protected abstract double backward(double gy);
}
```
