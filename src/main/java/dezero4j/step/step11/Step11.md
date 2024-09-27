### Step11：可変長の引数（順伝播編）
- Functionクラス
  - 多入力多出力に対応
    - Variable[] inputs
    - Variable[] outputs
  - 順伝播forwardメソッドと逆伝播backwardメソッドを修正

```java
public abstract class Function {
  protected Variable[] inputs;
  protected Variable[] outputs;

  public Variable[] forward(Variable[] inputs) {
  }

  public abstract double[][] forward(double[][] xs);

  public abstract double[][] backward(double[][] gys);
}
```

- 加算演算クラス
  - クラス名をAddからPlusに変更
