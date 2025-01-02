package step.step53;

import dezero4j.Model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ModelSaver {

    public static void saveModelToFile(Model network, String filePath) throws Exception {
        String filePathWithExtension;

        if (!filePath.endsWith(".model")) {
            filePathWithExtension = filePath + ".model";
        } else {
            filePathWithExtension = filePath;
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePathWithExtension);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(network);
        }
    }
}
