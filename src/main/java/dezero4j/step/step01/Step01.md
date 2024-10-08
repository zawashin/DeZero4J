### Step01：箱としての変数
- Variableクラス
    - NumPyは使えないので取り敢えずdataフィールドはプリミティブ型
        - double data
```java
public class Variable {
    double data;
    
    public Variable(double data) {
        this.data = data;
    }
    // ... 略
}
```

