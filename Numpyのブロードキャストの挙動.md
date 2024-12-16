---
title: Numpyのブロードキャストの挙動
tags: Python numpy
author: capybara
slide: false
---

# 概要

Numpyにはブロードキャストという仕組みがあり、ベクトル・行列計算を簡易に書けるようになっている。
挙動が気になるので調べてみた。

公式のドキュメントは下記
https://docs.scipy.org/doc/numpy/user/basics.broadcasting.html

# ブロードキャストとは

以下のように要素数が足りない場合には自動で行・列を拡張してくれる機能。

```python
>>> import numpy as np
>>> a = np.array([1, 2, 3])

# ブロードキャストなし
>>> b = np.array([100, 100, 100]) # 同じ要素(100)だが、列数を揃えるためにN回繰り返し書いている。
>>> a + b
array([101, 102, 103])

# ブロードキャストあり
>>> a + 100
array([101, 102, 103])
```

便利ですね。

# ブロードキャストの制約

> When operating on two arrays, NumPy compares their shapes element-wise. It starts with the trailing dimensions, and
> works its way forward. Two dimensions are compatible when
1. they are equal, or
2. one of them is 1

後ろから順に次元を比べ、対応する次元は同じか1でなくてはならない、と書いてありますね。
※(2, 3)対(1, 3)の場合にはまず3と3が比べられて、次に2と1が比べられる。

# 行列で挙動を確認

### (n, m) 対 スカラ値(単一の値)

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6],
                  [8, 10, 12]])
>>> a.shape
(2, 3)

# 足し算
>>> a + 1
array([[ 3,  5,  7],
       [ 9, 11, 13]])

# 引き算
>>> a - 1
array([[ 1,  3,  5],
       [ 7,  9, 11]])

# 掛け算
>>> a * 2
array([[ 4,  8, 12],
       [16, 20, 24]])

# 割り算
>>> a / 2
array([[1., 2., 3.],
       [4., 5., 6.]])
```

わかりやすいですね。

### (n, m) 対 (n, 1)

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6],
                  [8, 10, 12]])
>>> a.shape
(2, 3)
>>> b = np.array([[10],
                  [100]])
>>> b.shape
(2, 1)

# 足し算
>>> a + b
array([[ 12,  14,  16],
       [108, 110, 112]])

# 引き算
>>> a - b
array([[ -8,  -6,  -4],
       [-92, -90, -88]])

# 掛け算
>>> a * b
array([[  20,   40,   60],
       [ 800, 1000, 1200]])

# 割り算
>>> a / b
array([[0.2 , 0.4 , 0.6 ],
       [0.08, 0.1 , 0.12]])
```

行数が同じなので、列方向にコピーされていますね。

### (n, m) 対 (1, m)

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6],
                  [8, 10, 12]])
>>> a.shape
(2, 3)

>>> b = np.array([[10, 100, 1000]])
>>> b.shape
(1, 3)


# 足し算
>>> a + b
array([[  12,  104, 1006],
       [  18,  110, 1012]])

# 引き算
>>> a - b
array([[  -8,  -96, -994],
       [  -2,  -90, -988]])

# 掛け算
>>> a * b
array([[   20,   400,  6000],
       [   80,  1000, 12000]])

# 割り算
>>> a / b
array([[0.2  , 0.04 , 0.006],
       [0.8  , 0.1  , 0.012]])
```

列数が同じなので、行方向にコピーされていますね。

### (n, 1) 対 (1, m)

これはちょっと複雑です。
四則演算で動作は同じだと分かりましたので、以降は簡単のために足し算のみを表示します。

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6]])
>>> a.shape
(1, 3)

>>> b = np.array([[10],
                  [100]])
>>> b.shape
(2, 1)

>>> c = a + b
>>> c.shape
(2, 3)
>>> c
array([[ 12,  14,  16],
       [102, 104, 106]])
```

後ろから次元を比べた時に、まずbの列数の次元が小さいため

```python
b = np.array([[10],
              [100]])
bb = np.array([[10, 10, 10],
              [100, 100, 100]])
```

となり、次にaの行数の次元が小さいため

```python
a = np.array([[2, 4, 6]])
aa = np.array([[2, 4, 6],
               [2, 4, 6]])
```

となりaa + bbが計算されているのだと考えられますね。

### できない例: (n, m) 対 (l, 1), (1, l)

下記のような場合は、対応する次元は同じか1でなければいけない、というルールに反しているためブロードキャストできません。

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6],
                  [8, 10, 12]])
>>> a.shape
(2, 3)

# (l, 1) => (3, 1)の場合
>>> a + np.array([[10],
                  [100],
                  [1000]])
ValueError: operands could not be broadcast together with shapes (2,3) (3,1)

# (1, l) => (1, 2)の場合
>>> a + np.array([[10, 100]])
ValueError: operands could not be broadcast together with shapes (2,3) (1,2)
```

# 応用: テンソルの階数が異なる場合

2階のテンソル(行列)と3階のテンソルを比べた場合にはどうなるのでしょうか。

```python
>>> import numpy as np
>>> a = np.array([[2, 4, 6],
                  [8, 10, 12]])
>>> a.shape
(2, 3)

>>> b = np.array([[[10]],
                  [[100]], 
                  [[1000]],
                  [[10000]]])
>>> b.shape
(4, 1, 1)

>>> c = a + b
>>> c.shape
(4, 2, 3)

>>> c
array([[[   12,    14,    16],
        [   18,    20,    22]],

       [[  102,   104,   106],
        [  108,   110,   112]],

       [[ 1002,  1004,  1006],
        [ 1008,  1010,  1012]],

       [[10002, 10004, 10006],
        [10008, 10010, 10012]]])
```

後ろから比較されるというルールから、a.shape=(2, 3)は(1, 2, 3)として扱われていると考えれば良さそうですね。
a.shape = (1, 2, 3)
b.shape = (4, 1, 1)
それぞれの大きい方の次元を取って
c.shape = (4, 2, 3)になります。

