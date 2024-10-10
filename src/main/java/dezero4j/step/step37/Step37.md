### Step37：テンソルを使う
- Tensorクラス
  - VariableのdataをdoubleからTensorクラスに変更
  - 取り敢えず2階まで
```java
public class Tensor implements Cloneable, Serializable {
  public int rank;
  protected int length;
  protected double[] values;
  protected int[] shape;

}
```

