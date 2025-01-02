package step.step53;

import dezero4j.Model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

public class ModelLoader {

    public static Model loadModelFromFile(String filePath) throws Exception {
        String filePathWithExtension;

        if (!filePath.endsWith(".model")) {
            filePathWithExtension = filePath + ".model";
        } else {
            filePathWithExtension = filePath;
        }

        try (FileInputStream fileIn = new FileInputStream(filePathWithExtension);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Model) in.readObject(); // ネットワーク全体を読み込み
        }
    }
}
