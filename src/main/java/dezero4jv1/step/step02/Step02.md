### Step02:変数を生み出す関数
- Functionクラス
  - インターフェースの使い方が良く判らないから抽象クラス
  - 順伝播を計算するforwardメソッド
```java
public abstract class Function {
    public abstract Variable forward(Variable input);
}
```
