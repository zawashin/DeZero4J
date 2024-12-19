#!/bin/bash

#cat step*/Step*.md > ../../../../../README.md
# nの値を引数で指定
n=40

# 出力ファイル名（必要に応じて変更）
output_file="../../../../../README.md"

# 出力ファイルがすでに存在していたら削除
if [ -f "$output_file" ]; then
    rm "$output_file"
fi

# 1からnまでのstepN.mdをcatで連結
for ((i=0; i<=n; i++)); do
      # iが10未満なら0を付ける
      if [ $i -lt 10 ]; then
          step_dir="step0${i}"
          step_file="step0${i}/step0${i}.md"
      else
          step_dir="step${i}"
          step_file="step${i}/step${i}.md"
      fi
#    step_file="step${i}/step${i}.md"
    if [ -f "$step_file" ]; then
        cat "$step_file" >> "$output_file"
        echo "" >> "$output_file"  # 改行を追加
    else
        echo "ファイルが見つかりません: $step_file"
    fi
done

echo "連結完了: $output_file"


