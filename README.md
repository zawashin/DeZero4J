
# DeZero4J
[PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)を自作してみたいから、「[ゼロから作るDeep Learning ❸ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)」のフレームワークDezeroをJavaで(出来るところまで）実装してみた。

## 現状
- ステップ46の途中まで
  - 全結合の多層パーセプトロン
  - 活性化関数はシグモイド関数のみ
  - OptimizerはSGDのみ
- メモリ管理
  - ステップ18でWeakreferenceを使うべきかもしれないけど使い方が判らないので保留
    - 結果、OutOfMemoryで落ちる
- 例題でしか動作しない
  - 実用上問題なのは明らか

## 参考資料
- [ゼロから作るDeep Learning](https://github.com/oreilly-japan/deep-learning-from-scratch) 
- [ゼロから作るDeep Learning ❸ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)
- ↑のC++版[DeZeroCpp](https://github.com/bugph0bia/DeZeroCpp)

## 各ステップの実装についてのメモ
### Step01：箱としての変数
- Variableクラス
  - NumPyは使えないので取り敢えずプリミティブ型
  - 
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
```java:Function.java
public abstract class Function {
    public abstract Variable forward(Variable input);
}
```
### Step03：関数の連結


### Step04：数値微分
- NumricalDiff関数

### Step05：計算グラフで表す

### Step06
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

### Step07

### Step08

### Step09
- Variableクラス
  - dataとgradを配列に変更
```java:
public class Variable {
    double[] data;
    double[] grad;
    Function creator;
    
}
```

### Step10

### Step11
- Functionクラス
  - 多入力多出力に対応
  - 自力でどうにもならずChatGPT導入

### Step12

### Step13

### Step14

### Step15

### Step16

### Step17

### Step18

### Step19

### Step20

### Step21

### Step22

### Step23

### Step24

### Step25

### Step26

### Step27

### Step28

### Step29

### Step30

### Step31

### Step32

### Step33
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

### Step34

### Step35

### Step36

### Step37
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

### Step38

### Step39
### Step40

### Step41

### Step42

### Step43

### Step44
- Layerクラス
  - 抽象クラス
- Affineクラス
  - Layerの派生クラス
  - ゼロつく③の線形層Linearクラス

### Step45
- Modelクラス
  - Layerの派生クラス
- TwoLayerNetクラス
- MultiLayerPerceptronクラス
### Step46
- Optimizerクラス
  - SGD

