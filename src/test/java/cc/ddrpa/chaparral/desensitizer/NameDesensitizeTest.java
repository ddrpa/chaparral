package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NameDesensitizeTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize() throws JsonProcessingException {
        String original = "张三";
        assertEquals(
                "{\"name\":\"张*三\"}",
                mapper.writeValueAsString(new ChineseName(original)));
    }

    @Test
    void should_handle_blank_and_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"name\":\"\"}",
                mapper.writeValueAsString(new ChineseName("  ")));
        assertEquals(
                "{\"name\":null}",
                mapper.writeValueAsString(new ChineseName(null)));
    }

    static class ChineseName {
        @Sensitive(strategy = NAME)
        public String name;

        public ChineseName(String name) {
            this.name = name;
        }
    }
}