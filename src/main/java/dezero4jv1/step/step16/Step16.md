### Step16：複雑な計算グラフ（実装編）

- Variableクラス
    - 順伝搬時の世代を表すgenerationフィールドを追加

```java
public class Variable {
    protected double[] data;
    protected double[] grad;
    protected Function creator;
    protected int generation;
    // ... 略
}
```

