### Step37：テンソルを使う
- Tensorクラス
  - VariableのdataをdoubleからTensorクラスに変更
```java
public class Tensor implements Cloneable, Serializable {
    int rank;
    int length;
    protected double[] values;
    int[] shape;

}
```
  - 取り敢えず2階まで

