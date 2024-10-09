### Step01：箱としての変数
- Variableクラス
    - NumPyは使えないので取り敢えずdataフィールドはプリミティブ型
```java
public class Variable {
    protected double data;
    
    public Variable(double data) {
        this.data = data;
    }
    // ... 略
}
```

