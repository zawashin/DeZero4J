
# DeZero4J
- **De**ep Learning from **Ze**ro **for** **J**ava
- [PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)
  を自作してみたいから、「[ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
  」のフレームワークDezeroをJavaで(出来るところまで）実装してみる。

### 方針
- ステップごとにパッケージを作る
  - 各ステップごとにStepNクラスを作って検証する
    - Markdown書きが後になり次ステップで修正したクラスを前ステップで利用する形になった場合がある
- 外部ライブラリをできる限り使わない
  - numpy代わりのTensorクラスの実装**も**目指す
    - 車輪の再発明

## 現状
- ステップ46の途中まで
  - 全結合の多層パーセプトロン
  - 活性化関数はシグモイド関数のみ
  - OptimizerクラスはSGDクラスのみ
- メモリ管理
  - ステップ18でWeakreferenceを使うべきかもしれないけど使い方が判らないので保留
    - 結果、OutOfMemoryで落ちる
- 例題で**しか**動作しない


## 参考資料
- [ゼロから作るDeep Learning](https://github.com/oreilly-japan/deep-learning-from-scratch) 
- [ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
- ↑のC++版[DeZeroCpp](https://github.com/bugph0bia/DeZeroCpp)

## 各ステップの実装についてのメモ
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
    - dataフィールドとgradフィールドを配列に変更
```java
public class Variable {
  protected double[] data;
  protected double[] grad;
  protected Function creator;
    // ... 略
}
```

### Step10：テストを行う

### Step11：可変長の引数（順伝播編）
- Functionクラス
  - 多入力多出力に対応
  - 順伝播を計算するforwardメソッドと逆伝播を計算するbackwardメソッドを修正

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
                // 修正箇所
                if (f.inputs[i].grad == null) {
                    f.inputs[i].grad = gxs[i];
                } else {
                    for (int j = 0; j < gxs[i].length; j++) {
                        f.inputs[i].grad[j] += gxs[i][j];
                    }
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
    protected double[] data;
    protected double[] grad;
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

### Step20：演算子のオーバーロード (1)

- Javaなので演算子の多重定義が出来ない
  - Variableクラスに各種演算メソッドを定義
  - **Scala**や**Kotlin**は演算子のオーバーロードが出来るけど
  - 二項演算クラスの命名はKotlinに**準拠**
    - Multiply → Times
    - Divide → Div
### Step21：演算子のオーバーロード (2)
- Javaなので演算子の多重定義が(以下略)
### Step22：演算子のオーバーロード (3)

- Javaなので(以下略)
### Step23：パッケージとしてまとめる
### Step24：複雑な関数の微分

- 演算子のオーバーロードがないので複雑になりすぎて可読性が**極めて**低い

### Step25：計算グラフの可視化 (1)
- 省略
### Step26：計算グラフの可視化 (2)

- 省略
### Step27：テイラー展開の微分

### Step28：関数の最適化

### Step29：ニュートン法を用いた最適化（手計算）

### Step30：高階微分（準備編）

### Step31：高階微分（理論編）
### Step32：高階微分（実装偏）

### Step33：ニュートン法を使った最適化（自動計算）

- Variableクラス
  - backwardメソッド実装時の注意点
      - **順伝播の演算を行う**forwardメソッドを用いないと高階微分が計算されない
```java
// Example 
public double[][] forward(double[]... xs) {
    double[] x0 = xs[0];
    length = xs[0].length;
    double[][] ys = new double[numOutputs][length];
    for (int i = 0; i < length; i++) {
        ys[0][i] = Math.cos(x0[i]);
    }
    return ys;
}

@Override
public Variable[] backward(Variable... gys) {
    Variable[] gx = new Variable[numInputs];
    Variable x = inputs[0];
  gx[0] = (x.sin().negative()).times(gys[0]);
    return gx;
}
```

### Step34：sin 関数の高階微分

### Step35：高階微分の計算グラフ

### Step36：高階微分以外の用途

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

### Step38：形状を変える関数

### Step39：和を求める関数
### Step40：ブロードキャストを行う関数

### Step41：行列の積

### Step42：線形回帰

### Step43：ニューラルネットワーク

### Step44：パラメータをまとめるレイヤ
- Layerクラス
  - 抽象クラス
- Affineクラス
  - Layerの派生クラス
  - ゼロつく③の線形層Linearクラス

### Step45：レイヤをまとめるレイヤ
- Modelクラス
  - Layerの派生クラス
- TwoLayerNetクラス
- MultiLayerPerceptronクラス
### Step46：Optimizer によるパラメータ更新
- Optimizerクラス
  - SGD

