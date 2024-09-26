### Step06：手作業によるバックプロパゲーション
- Variableクラス
  - 勾配を追加
    - double grad
```java
public class Variable {
    double data;
    double grad;
    // 略
}
```

- Functionクラス
  - 逆伝播を計算のためにフィールドinputを追加
    - Variable input
  - 逆伝播を計算するためのbackwardメソッドを定義

```java
public abstract class Function {
  protected Variable input;

  public abstract Variable forward(Variable input);

  protected abstract double forward(double x);

  protected abstract double backward(double gy);
}
```
