#!/bin/bash

# Nが1から10までループ
for N in {1..50}
do
  # ディレクトリが存在するか確認
  if [ -d "step0$N" ]
  then
    # ディレクトリが存在する場合、その中に.mdファイルを作成
    touch "step0$N/Step0$N.md"
  fi
done
# Nが1から9までループ
for N in {10..50}
do
  # ディレクトリが存在するか確認
  if [ -d "step$N" ]
  then
    # ディレクトリが存在する場合、その中に.mdファイルを作成
    touch "step$N/Step$N.md"
  fi
done
