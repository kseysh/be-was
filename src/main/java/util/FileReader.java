package util;

import exception.BadRequestException;
import exception.HttpException;
import exception.InternalServerErrorException;
import exception.NotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static final String RESOURCES_PATH = "./src/main/resources/static";

    private FileReader() {
    }

    public static byte[] readFile(String path) {
        File file = new File(RESOURCES_PATH + path);
        validateFile(file, path);
        return FileReader.readAllBytes(file);
    }

    private static byte[] readAllBytes(File file) {
        int fileLength = (int) file.length();
        byte[] bytes = new byte[fileLength];

        try (FileInputStream fis = new FileInputStream(file)) {
            int offset = 0;
            int numRead;

            while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return bytes;
    }

    private static void validateFile(File file, String path) throws HttpException {
        if (!file.exists()) {
            throw new NotFoundException(path + " file not found");
        }

        if (!file.isFile()) {
            throw new BadRequestException(path + " file is not a file");
        }
    }
}
