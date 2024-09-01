
# DeZero4J
- **De**ep Learning from **Ze**ro **for** **J**ava
- [PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)
  を自作してみたいから、「[ゼロから作るDeep Learning ❸ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
  」のフレームワークDezeroをJavaで(出来るところまで）実装してみる。

### 方針
- 最低限ステップごとにコミットする。ビルドは通る状態とする。
  - つもりだったけど、Markdown書きが後になり次ステップで修正したクラスを前ステップで利用する形になった場合がある
- numpy代わりのTensorクラス**も**実装を目指す
  - nd4jの開発が実質凍結っぽい
  - 車輪の再発明？それがどうした！
    - 悲劇の元

## 現状
- ステップ46の途中まで
  - 全結合の多層パーセプトロン
  - 活性化関数はシグモイド関数のみ
  - OptimizerはSGDのみ
- メモリ管理
  - ステップ18でWeakreferenceを使うべきかもしれないけど使い方が判らないので保留
    - 結果、OutOfMemoryで落ちる
- 例題でしか動作しない
  - **実用上問題**なのは明らか

## 参考資料
- [ゼロから作るDeep Learning](https://github.com/oreilly-japan/deep-learning-from-scratch) 
- [ゼロから作るDeep Learning ❸ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
- ↑のC++版[DeZeroCpp](https://github.com/bugph0bia/DeZeroCpp)

## 各ステップの実装についてのメモ
### Step01：箱としての変数
- Variableクラス
  - NumPyは使えないので取り敢えずプリミティブ型
```java
public class Variable {
    double data;
    
    public Variable(double data) {
        this.data = data;
    }
    // 略
}
```

### Step02:変数を生み出す関数
- Functionクラス
  - インターフェースの使い方が良く判らないから抽象クラス
  - 順計算を抽象メソッドとして定義
      - forwardメソッド
- Squareクラス
    - Functionクラスの具象化

```java
public abstract class Function {
    public abstract Variable forward(Variable input);
}
```
### Step03：関数の連結

- 複数関数が必要なので、Fuctionクラスの具象化としてExpクラスを実装

### Step04：数値微分

- 検証のためのStep04クラスにnumricalDiffメソッドを実装

### Step05：計算グラフで表す

### Step06：手作業によるバックプロパゲーション
- Variableクラス
  - 勾配を追加
    - double grad
```java
public class Variable {
    double data;
    double grad;
    // 略
}
```

### Step07：バックプロパゲーションの自動化


### Step08：再帰からループへ

### Step09：関数をより便利に
- Variableクラス
  - dataとgradを配列に変更
```java
public class Variable {
    double[] data;
    double[] grad;
    Function creator;
    // 略
}
```

### Step10：テストを行う

- このステップは省略する。

### Step11：可変長の引数（順伝播編）
- Functionクラス
  - 多入力多出力に対応
  - 自力でどうにもならずChatGPT導入

### Step12：可変長の引数（改善偏）

### Step13：可変長の引数（逆伝播偏）

### Step14：同じ変数を繰り返し使う

### Step15：複雑な計算グラフ（理論編）

### Step16：複雑な計算グラフ（実装編）

### Step17：メモリ管理と循環参照

### Step18：メモリ使用量を減らすモード

### Step19：変数を使いやすく

### Step20：演算子のオーバーロード (1)

### Step21：演算子のオーバーロード (2)

### Step22：演算子のオーバーロード (3)

### Step23：パッケージとしてまとめる

### Step24：複雑な関数の微分

### Step25：計算グラフの可視化 (1)

### Step26：計算グラフの可視化 (2)

### Step27：テイラー展開の微分

### Step28：関数の最適化

### Step29：ニュートン法を用いた最適化（手計算）

### Step30：高階微分（準備編）

### Step31：高階微分（理論編）

- このステップでの実装は無し。

### Step32：高階微分（実装偏）

### Step33：ニュートン法を使った最適化（自動計算）

- Variableクラス
  - backwardメソッド実装時の注意点
    -   逆伝播は逐一書かず、**順伝播の演算メソッド**を用いないと高階微分が計算されない
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
    gx[0] = (x.sin().negative()).multiply(gys[0]);
    return gx;
}
```

### Step34：sin 関数の高階微分

### Step35：高階微分の計算グラフ

### Step36：高階微分以外の用途

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
  - ゼロつく❸の線形層Linearクラス

### Step45：レイヤをまとめるレイヤ
- Modelクラス
  - Layerの派生クラス
- TwoLayerNetクラス
- MultiLayerPerceptronクラス
### Step46：Optimizer によるパラメータ更新
- Optimizerクラス
  - SGD

