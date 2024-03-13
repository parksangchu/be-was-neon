package utils;

public class FileManager {
    public static final String FILE_EXTENSION_MARKER = ".";
    public static final String FILE_SEPARATOR = "/";
    public static final String NO_FILE_EXTENSION = "";

    public static String getFileExtension(String filePath) {
        int lastIndexOfDot = filePath.lastIndexOf(FILE_EXTENSION_MARKER);
        int lastIndexOfSlash = filePath.lastIndexOf(FILE_SEPARATOR);

        if (lastIndexOfDot == -1 || lastIndexOfDot < lastIndexOfSlash) {
            return NO_FILE_EXTENSION; // 확장자가 없음을 나타냅니다.
        }

        return filePath.substring(lastIndexOfDot + 1);
    }
}
