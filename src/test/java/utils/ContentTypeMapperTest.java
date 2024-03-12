package utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContentTypeMapperTest {

    @Test
    @DisplayName("파일 확장자에 따른 content-type 을 맵핑할 수 있다.")
    void getContentType() {
        String defaultType = ContentTypeMapper.getContentType("");
        assertThat(defaultType).isEqualTo("text/html;charset=utf-8");

        String jpeg = ContentTypeMapper.getContentType("jpg");
        assertThat(jpeg).isEqualTo("image/jpeg");
    }
}