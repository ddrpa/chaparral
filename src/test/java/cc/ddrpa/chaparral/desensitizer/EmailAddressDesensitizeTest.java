package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailAddressDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize() throws JsonProcessingException {
        String original = "no_reply@ddrpa.cc";
        assertEquals(
                "{\"email\":\"n***y@ddrpa.cc\"}",
                mapper.writeValueAsString(new EmailAddress(original)));
    }

    @Test
    void should_handle_invalid_blank_and_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"email\":\"\"}",
                mapper.writeValueAsString(new EmailAddress("invalid#ddrpa.cc")));
        assertEquals(
                "{\"email\":\"\"}",
                mapper.writeValueAsString(new EmailAddress("  ")));
        assertEquals(
                "{\"email\":null}",
                mapper.writeValueAsString(new EmailAddress(null)));
    }

    static class EmailAddress {
        @Sensitive(strategy = EMAIL)
        public String email;

        public EmailAddress(String email) {
            this.email = email;
        }
    }
}