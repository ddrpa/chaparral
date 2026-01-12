package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.BIRTHDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthdayDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize() throws JsonProcessingException {
        assertEquals(
                "{\"birthday\":\"****-10-01\"}",
                mapper.writeValueAsString(new Birthday(LocalDate.of(1949, 10, 1))));
    }

    @Test
    void should_handle_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"birthday\":null}",
                mapper.writeValueAsString(new Birthday(null)));
    }

    static class Birthday {
        @Sensitive(strategy = BIRTHDAY)
        public LocalDate birthday;

        public Birthday(LocalDate birthday) {
            this.birthday = birthday;
        }
    }
}