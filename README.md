# DeZero4J

- **De**ep Learning from **Ze**ro **for** **J**ava
- [PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)
  を自作してみたいから、「[ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
  」のフレームワークDezeroをJavaで(出来るところまで）実装してみる。

## 方針

- ステップごとにパッケージを作って進める
    - 各ステップごとにStepNクラスを作って検証する
    - 以前のステップの例題でも動作確認を可能な限り行う
- 外部ライブラリをできる限り使わない
    - NumPy代わりの[Tensor](https://github.com/zawashin/Tensor4J)を扱うクラスライブラリの実装**も**目指す
        - 車輪の再発明？それがどうした！
- [Tensor](https://github.com/zawashin/Tensor4J)クラスを**先に**実装する
    - 四則演算、数学関数などのメソッドは深層学習で必要な2階までしか実装していない
  - 途中で細かい修正をした
- 深層強化学習でも使うことを考えて多値分類のStep47までを当面の目標とする

## 開発環境構成

| DeZero     | DeZero4J                                                  |
|------------|-----------------------------------------------------------|
| Python 3   | Eclipse Temurin™ JDK 21-LTS                               |
| NumPy      | [Tensor4J](https://github.com/zawashin/Tensor4J)          |
| matplotlib | **必要に応じて**[JFreeChart](https://www.jfree.org/jfreechart/) |
| CuPy       | CUDAは~~使えない~~使わないので不要                                     |
| Pillow     | 画像認識などは行う予定がないので不要                                        |
| Graphviz   | 計算グラフの可視化は行わないので不要                                        |

## ~~1回目の実装挑戦~~

- ~~ステップ46の途中まで実装~~
- ~~OutOfMemoryで落ちる~~
- ~~学習師で**しか**動作せず、推論が出来ない~~

## 現状

- Tensorデータを扱うためのクラスをあらかじめ実装して仕切り直し
- Step46まで完了
  - 全結合の多階層ニューラルネットでの最低限の学習・推論は出来る

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

- 特になし


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

- 特になし

### Step09：関数をより便利に

- Variableクラス
    - dataフィールドとgradフィールドをdoubleからTensorクラスに変更
        - NumPyの代わり

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

- 特になし

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

- 特になし


### Step13：可変長の引数（逆伝播偏）

- 特になし


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

- 特になし


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
    - ~~理解出来なかったので保留~~
    - ChatGPTで実装できたが、採用保留
- ~~AutoCloseableインターフェースも検討してみる**予定**~~


### Step18：メモリ使用量を減らすモード

- Configクラス、NoGradクラス、UsingConfigクラスを定義
    - 良く解らなかったのでChatGPTでJavaに変換

### Step19：変数を使いやすく

- Tenssorクラスで実装済み
    - toStringメソッド
    - getLengthメソッド


### Step20：演算子のオーバーロード (1)

- Javaなので演算子の多重定義が出来ない
    - Variableクラスに各種演算メソッドを定義
    - **Scala**や**Kotlin**は演算子のオーバーロードが出来る
- Variableクラスに四則演算のメソッド

```java
public class Variable {
// ... 略
    public Variable add(Variable other) {
        Function f = new Add();
        return f.forward(this, other)[0];
    }

// ... 略
}
```

### Step21：演算子のオーバーロード (2)

- Javaなので演算子の多重定義が(以下略)
- Variableクラスの四則演算のメソッドを修正
    - 引数がプリミティブ型(double)の場合を追加

```java
public class Variable {
// ... 略
    public Variable add(Variable other) {
        Function f = new Add();
        return f.forward(this, other)[0];
    }

    public Variable add(double other) {
        Function f = new Add();
        return f.forward(this, new Variable(Utils.create(other, this.getShape())))[0];
    }
// ... 略
}
```

-
### Step22：演算子のオーバーロード (3)

- Javaなので(以下略)
- 四則演算のクラス
    - Subtract
    - Divide
- 負数演算クラス
    - Neg
- 累乗演算クラス
    - Pow
- Variableクラスに各演算のメソッドを追加

### Step23：パッケージとしてまとめる

- 特になし


### Step24：複雑な関数の微分

- 演算子のオーバーロードがないので複雑になりすぎて可読性が**極めて**低い
    - Copilotに丸投げ
    - **参考記事**
        - [Javaに演算子オーバーロードを導入すべきときが来たのか](https://blogs.oracle.com/otnjp/post/is-it-time-for-overloading-in-java-ja)

- Sphere関数
    - $f(x, y) = x^2 + y^2$

```java
public class Sphere {
    public Variable calc(Variable... xs) {
        return xs[0].square().add(xs[1].square());
    }
    // ... 略
}
```

- Matyas関数
    - $f(x, y) = 0.26 \cdot (x^2 + y^2) - 0.48 \cdot x \cdot y$

```java
public class Matyas {
    public Variable calc(Variable... xs) {
        return xs[0].square().add(xs[1].square()).multiply(0.26).subtract(xs[0].multiply(xs[1]).multiply(0.48));
    }
    // ... 略
}
```

- Goldstein-Price関数
    - $f(x, y) = \left[1 + (x + y + 1)^2 \cdot (19 - 14x + 3x^2 - 14y + 6xy + 3y^2)\right] \cdot \left[30 + (2x - 3y)^2 \cdot (18 - 32x + 12x^2 + 48y - 36xy + 27y^2)\right]$

```java
public class Matyas {
    public Variable calc(Variable... xs) {
        return ((xs[0].add(xs[1]).add(1)).pow(2)).add(1).multiply(constant(19).subtract(constant(14).multiply(xs[0])).add(constant(3).multiply(xs[0].pow(2))).subtract(constant(14).multiply(xs[1])).add(constant(6).multiply(xs[0]).multiply(xs[1])).add(constant(3).multiply(xs[1].pow(2)))).multiply(constant(30).add(constant(2).multiply(xs[0]).subtract(constant(3).multiply(xs[1])).pow(2).multiply(constant(18).subtract(constant(32).multiply(xs[0])).add(constant(12).multiply(xs[0].pow(2))).add(constant(48).multiply(xs[1])).subtract(constant(36).multiply(xs[0]).multiply(xs[1])).add(constant(27).multiply(xs[1].pow(2))))));
    }
    // ... 略
}
```

### Step25：計算グラフの可視化 (1)

- 省略

### Step26：計算グラフの可視化 (2)

- 省略

### Step27：テイラー展開の微分

- Sinクラスの実装
    - 逆伝播の確認のためにCosクラスを実装


### Step28：関数の最適化

```java
public class Step28 extends Step {
    @Override
    public void calc() {
        // ... 略
        for (int i = 0; i < maxIteration; i++) {
            // ... 略
            for (Variable x : xs) {
                //x.getData().minusAssign(x.getGrad().multiply(learningRate));
                x.minusAssign(new Variable(x.getGrad().multiply(learningRate)));
            }
            // ... 略
        }
        // ... 略
    }
}
```
### Step29：ニュートン法を用いた最適化（手計算）

- UsingConfigクラスの単純化
- 2階微分を計算するGx2クラスを実装


### Step30：高階微分（準備編）

- 特になし


### Step31：高階微分（理論編）

- 特になし


### Step32：高階微分（実装偏）

- Variableクラス
    - gradフィールドをTensorクラスからVariableクラスに変更
    - backwardメソッドを修正

```java
public class Variable {
    // ... 略
    Variable grad;
  // ... 略
    public void backward(boolean retainGrad, boolean createGraph) {
    // ... 略
        while (!funcs.isEmpty()) {
          // ... 略
            try (UsingConfig config = new UsingConfig("enableBackprop", createGraph)) {
              // ... 略
                    for (int i = 0; i < gxs.length; i++) {
                      // ... 略
                        if (x.grad == null) {
                            x.grad = gx;
                        } else {
                            x.grad = x.grad.add(gx);
                        }
                    }
                }
            } catch (Exception e){
            // ... 略
        }
    }
}
```

- Functionクラス
    - backwardメソッドは順伝播のメソッドを使う

```java
// Example 
public class Cos extends Function {
    public Tensor[][] forward(Tensor... xs) {
        return new Tensor[]{xs[0].cos()};
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable x = inputs[0];
        return new Variable[]{(x.sin().neg()).multiply(gys[0])};
    }
}
```


### Step33：ニュートン法を使った最適化(自動計算)

- Step32までの実装が**正しければ**問題なく動作する

### Step34：sin 関数の高階微分

- 特になし

### Step35：高階微分の計算グラフ

- Tanhクラスを実装

### Step36：高階微分以外の用途

- 特になし


### Step37：テンソルを使う

- Tensorクラスを事前に実装しておいた
    - StepごとにTensorを拡張してったら破綻した

```java
public class Tensor implements Cloneable, Serializable {
    public int rank;
    protected int length;
    protected double[] values;
    protected int[] shape;
// ... 略
}

```

- テンソルでの逆伝播の確認
    - Step39で登場するSumクラスの使用
        - backwardメソッドでBroadcastToクラスが必要
        - BroadcastToクラスのbackwardメソッドでSumToクラスが必要
    - Step39、40まで保留

### Step38：形状を変える関数

- Reshapeクラス
- Transoposeクラス
    - NumPyと同じように0階と1階のテンソルは同値を返すようにした
        - 0階と1階のテンソルの倒置は、**数学的には存在しない**
    - 2階まで対応

### Step39：和を求める関数

- Sumクラス
    - keepDimsについては保留

### Step40：ブロードキャストを行う関数

- BroadcastToクラス
- SumToクラス
    - keepDimsについては保留
- 四則演算クラスのbroadcast対応

### Step41：行列の積

- Matmulクラス
    - 1階テンソルと2階テンソルのbackward計算が落ちる
      - 対策として、1階テンソルを**明示的に**2階テンソルにする必要がある
      - 1階テンソルの場合を考慮した処理を導入
### Step42：線形回帰
- Matmulの修正
  - 0階テンソル(スカラ)の微分値が1x1の2階テンソル(行列)になってしまうバグの修正
  - Dezeroでは計算できないようなのでこちらがバグってる可能性もあり


### Step43：ニューラルネットワーク
- Linearクラスの実装
- Sigmoidクラスの実装
-

### Step44：パラメータをまとめるレイヤ

- Layerクラス
    - 抽象クラス
- Affineクラス
    - Layerの派生クラス
    - Fuctionクラスの具象化Linearクラスと名前が被るのでAffineクラスと名称変更する
      - ゼロつく①の線形層クラス


### Step45：レイヤをまとめるレイヤ
- ActivationFunctionクラス
  - 活性化関数をFucntionクラスの派生クラスとして定義
    - **※Dezeroにはない**
      - Variableクラスの派生クラスParameterがあるからなんとなく
    - Sigmoid, ReLU, Tanh, Softmaxなど
- Modelクラス
    - Layerの派生抽象クラス
- TwoLayerNetクラス
  - Modelクラスの派生クラス
- MultiLayerPerceptronクラス
  - Modelクラスの派生クラス

### Step46 Optimizerによるパラメータ更新
- Optimizerクラス
  - 抽象クラス
  - 具象クラスとしてSDGクラスのみ実装

### Step47：ソフトマックス関数と交差エントロピー誤差

- TBD

### Step48：多値分類

- TBD

### Step49：Datasetクラスと前処理

- TBD

### Step50 ミニバッチを取り出すDataLoader

- TBD

### Step53 モデルの保存と読み込み

- TBD

### Step54：Dropoutとテストモード

- TBD


