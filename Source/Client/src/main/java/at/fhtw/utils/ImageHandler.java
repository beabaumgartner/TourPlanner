package at.fhtw.utils;

import at.fhtw.businessLogic.BusinessLogic;
import at.fhtw.exceptions.FailedToParseImageFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageHandler {
    private static final Logger logger = LogManager.getLogger(ImageHandler.class);
    public static void saveBase64EncodedImageToFile(String encodedImage, String filename) {
        try {
            Path imagePath = Paths.get("data/images/");
            if (!Files.exists(imagePath)) {
                Files.createDirectories(imagePath);
                logger.info("ImageHandler.saveBase64EncodedImageToFile() - directory created: " + imagePath);
            }

            if(!filename.startsWith("data/images/")) {
                filename = "data/images/" + filename;
            }
            if(!filename.endsWith(".jpg")) {
                filename += ".jpg";
            }

            byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
            Path path = Paths.get(filename);

            FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
            fileOutputStream.write(decodedBytes);
            logger.info("ImageHandler.saveBase64EncodedImageToFile() - image saved successfully: " + filename);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new FailedToParseImageFileException("ImageHandler.saveBase64EncodedImageToFile - failed to save image: " + filename);
        }
    }
}
