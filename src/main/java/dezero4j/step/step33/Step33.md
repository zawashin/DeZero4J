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

