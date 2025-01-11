package dezero4j;

import java.io.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ModelIO {

    public static final String EXTENSION = ".model";

    public static void saveModelToFile(Model network, String filePath) throws Exception {
        String filePathWithExtension;

        if (!filePath.endsWith(EXTENSION)) {
            filePathWithExtension = filePath + EXTENSION;
        } else {
            filePathWithExtension = filePath;
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePathWithExtension);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(network);
        }
    }

    public static Model loadModelFromFile(String filePath) throws Exception {
        String filePathWithExtension;

        if (!filePath.endsWith(EXTENSION)) {
            filePathWithExtension = filePath + EXTENSION;
        } else {
            filePathWithExtension = filePath;
        }

        try (FileInputStream fileIn = new FileInputStream(filePathWithExtension);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Model) in.readObject(); // ネットワーク全体を読み込み
        }
    }

}
