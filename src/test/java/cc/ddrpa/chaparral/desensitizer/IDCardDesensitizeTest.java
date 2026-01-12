package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.ID_CARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 本测试用例中使用的身份证号均为虚拟号码，使用在线工具随机生成
 */
class IDCardDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize() throws JsonProcessingException {
        String original = "330106190103070133";
        assertEquals(
                "{\"id\":\"3****************3\"}",
                mapper.writeValueAsString(new IDCard(original)));
    }

    @Test
    void should_handle_blank_and_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"id\":\"\"}",
                mapper.writeValueAsString(new IDCard("  ")));
        assertEquals(
                "{\"id\":null}",
                mapper.writeValueAsString(new IDCard(null)));
    }

    static class IDCard {
        @Sensitive(strategy = ID_CARD)
        public String id;

        public IDCard(String id) {
            this.id = id;
        }
    }
}