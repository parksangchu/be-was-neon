package utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileManagerTest {

    @Test
    @DisplayName("파일 경로에서 확장자를 추출할 수 있다.")
    void getFileExtension() {
        String filePath = "./src/main/resources/static/index.html";
        String fileExtension = FileManager.getFileExtension(filePath);
        assertThat(fileExtension).isEqualTo("html");
    }
}