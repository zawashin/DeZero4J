# DeZero4J

- **De**ep Learning from **Ze**ro **for** **J**ava
- [PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)
  を自作してみたいから、「[ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
  」のフレームワークDezeroをJavaで(出来るところまで）実装してみる。

## 方針
- ステップごとにパッケージを作って進める
  - 各ステップごとにStepNクラスを作って検証する
  - 以前のステップの例題でも動作確認を可能な限り行う
    [](Markdown書きが後になり次ステップで修正したクラスを前ステップで利用する形になった場合がある)
- 外部ライブラリをできる限り使わない
  - Numpy代わりのTensorクラスの実装**も**目指す
    - 車輪の再発明？それがどうした！
- [Tensor](https://github.com/zawashin/Tensor4J)クラスを**先に**実装する
  - 4階まで対応
    - [Deepnetts](https://github.com/deepnetts/deepnetts-communityedition)のTensorクラスに倣う
  - 四則演算、数学関数を除くメソッドは深層学習で必要な2階までしか実装ない
    - 四階までは、**必要に応じて**対応は可能(なはず)

## 現状
- ~~ステップ46の途中まで実装~~
- ~~OutOfMemoryで落ちる~~
- ~~例題で**しか**動作しない~~
- Tensor関連のクラスを再実装して仕切り直し

## 参考資料
- [ゼロから作るDeep Learning](https://github.com/oreilly-japan/deep-learning-from-scratch)
- [ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
- DeZeroのC++版移植[DeZeroCpp](https://github.com/bugph0bia/DeZeroCpp)
- DeZeroのC移植版[Dezero-with-C](https://github.com/Rn86222/Dezero-with-Cp)
  - ここまでやった猛者もいるという自分への励まし

## 各ステップの実装についてのメモ

### Step01：箱としての変数

- Variableクラス
    - NumPyは使えないので取り敢えずdataフィールドはプリミティブ型

```java
public class Variable {
    protected double data;

    // ... 略
}
```


### Step02:変数を生み出す関数

- Functionクラス
    - インターフェースの使い方が良く判らないから抽象クラス
    - 順伝播を計算するforwardメソッド

```java
public abstract class Function {
    public abstract Variable forward(Variable input);
}
```

### Step03：関数の連結

- 複数関数が必要なので、Fuctionクラスの具象化としてExpクラスを実装


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


### Step05：計算グラフで表す


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

  　// ↓追加！
    public abstract Variable forward(Variable input);
    protected abstract double forward(double x);
    protected abstract double backward(double gy);
}
```

### Step07：バックプロパゲーションの自動化

- Variableクラス
    - 逆伝播の計算
        - creatorフィールドを追加
        - backwardメソッドを定義

```java
public class Variable {
    protected double data;
    protected double grad;
    protected Function creator;

    // ... 略
    public void backward() {
        // ... 略
    }
    // ... 略
}
```

- Functionクラス
    - 逆伝播計算自動化するのためにoutputフィールドを追加
    - 逆伝播を計算するためのbackwardメソッドを定義

```java
public abstract class Function {
    protected Variable input;
    protected Variable output;

    public Variable forward(Variable input) {
      // ... 略
    }

    protected abstract double forward(double x);
    protected abstract double backward(double gy);
}
```

### Step08：再帰からループへ


### Step09：関数をより便利に

- Variableクラス
    - dataフィールドとgradフィールドをdoubleからTensorクラスに変更
        - Numpyの代わり

```java
import tensor4j.Tensor;

public class Variable {
    protected Tensor data;
    protected Tensor grad;
    protected Function creator;
    // ... 略
}
```


### Step10：テストを行う


### Step11：可変長の引数（順伝播編）
- Functionクラス
  - 多入力多出力に対応
  - 順伝播を計算するforwardメソッドと逆伝播を計算するbackwardメソッドを修正
  - 逆伝播は未実装

```java
import tensor4j.Tensor;

public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;

    public Variable[] forward(Variable... inputs) {
      // ... 略
    }

    public abstract Tensor[] forward(Tensor... xs);
    public abstract Tensor[] backward(Tensor... gys);
}
```

- 加算演算クラス
  - クラス名をAddからPlusに変更

### Step12：可変長の引数（改善偏）

### Step13：可変長の引数（逆伝播偏）


### Step14：同じ変数を繰り返し使う

- Variableクラス
    - backwardメソッドの修正

```java
public class Variable {
    // ... 略
    public void backward() {
        // ... 略
        ArrayList<Function> funcList = new ArrayList<>();
        funcList.add(creator);
        while (!funcList.isEmpty()) {
            // ... 略
            for (int i = 0; i < gxs.length; i++) {
                if (f.inputs[i].grad == null) {
                    // 複製すること！
                    f.inputs[i].grad = gxs[i].clone();
                } else {
                    f.inputs[i].grad.plusAssign(gxs[i]);
                }
                // ここまで
                // ... 略
            }
        }
    }
}
```


### Step15：複雑な計算グラフ（理論編）


### Step16：複雑な計算グラフ（実装編）

- Variableクラス
    - 順伝搬時の世代を表すgenerationフィールドを追加

```java
public class Variable {
    protected Tensor data;
    protected Tensor grad;
    protected Function creator;
    protected int generation;
    // ... 略
}
```


### Step17：メモリ管理と循環参照

- WeakReferenceの導入
    - 理解出来なかったので保留


### Step18：メモリ使用量を減らすモード
- Configクラスを定義

### Step19：変数を使いやすく
- Tenssorクラスで実装済み
  - toStringメソッド
  - getLengthメソッド


### Step20：演算子のオーバーロード (1)

- Javaなので演算子の多重定義が出来ない
  - Variableクラスに各種演算メソッドを定義
  - **Scala**や**Kotlin**は演算子のオーバーロードが出来る
  - 二項演算クラスの命名はKotlinに**準拠**
    - Mul → Times

### Step21：演算子のオーバーロード (2)
- Javaなので演算子の多重定義が(以下略)

