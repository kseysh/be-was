package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    private FileReader() {}

    public static byte[] readAllBytes(File file) throws IOException {
        int fileLength = (int) file.length();
        byte[] bytes = new byte[fileLength];

        try (FileInputStream fis = new FileInputStream(file)) {
            int offset = 0;
            int numRead;

            while (
                    offset < bytes.length &&
                            (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0
            ) {
                offset += numRead;
            }
        }
        return bytes;
    }
}
