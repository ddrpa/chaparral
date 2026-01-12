package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.CELL;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 本测试用例中使用的手机号均为虚拟号码，使用在线工具随机生成
 */
class CellPhoneNumberDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize() throws JsonProcessingException {
        String original = "18824769941";
        assertEquals(
                "{\"cell\":\"188****9941\"}",
                mapper.writeValueAsString(new CellNumber(original)));
    }

    @Test
    void should_handle_blank_and_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"cell\":\"\"}",
                mapper.writeValueAsString(new CellNumber("  ")));
        assertEquals(
                "{\"cell\":null}",
                mapper.writeValueAsString(new CellNumber(null)));
    }

    static class CellNumber {
        @Sensitive(strategy = CELL)
        public String cell;

        public CellNumber(String cell) {
            this.cell = cell;
        }
    }
}