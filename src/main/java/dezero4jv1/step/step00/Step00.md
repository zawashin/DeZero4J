
# DeZero4J
- **De**ep Learning from **Ze**ro **for** **J**ava
- [PINNs(Physics-Informed Neural Networks)](https://en.wikipedia.org/wiki/Physics-informed_neural_networks)を自作してみたいから、「[ゼロから作るDeep Learning ③ ―フレームワーク編](https://github.com/oreilly-japan/deep-learning-from-scratch-3)」のフレームワークDezeroをJavaで(出来るところまで）実装してみる。

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
