package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SIDMakerTest {

    @Test
    @DisplayName("SID 메이커는 10만개의 중복되지 않은 SID를 생성할 수 있다.")
    void makeSID() {
        Set<String> SIDs = new HashSet<>();
        int size = 1000000;
        for (int i = 0; i < size; i++) {
            SIDs.add(SIDMaker.makeSID());
        }
        assertThat(SIDs.size()).isEqualTo(size);
    }
}