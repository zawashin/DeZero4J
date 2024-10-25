### Step04：数値微分

- Functionクラス
  - 順伝播を実際に計算するための抽象メソッドを定義
```java
public abstract class Function {
    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        return new Variable(y);
    }
    protected abstract double forward(double x);
}
```
- 検証のためのStep04クラスにnumricalDiffメソッドを実装

