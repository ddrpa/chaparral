package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.SOCIAL_MEDIA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SocialMediaUsernameDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_mask_short_username() throws JsonProcessingException {
        assertEquals(
                "{\"username\":\"**\"}",
                mapper.writeValueAsString(new Username("小A")));
        assertEquals(
                "{\"username\":\"**\"}",
                mapper.writeValueAsString(new Username("\uD83D\uDE00")));
    }

    @Test
    void should_mask_3_char_username() throws JsonProcessingException {
        assertEquals(
                "{\"username\":\"一*人\"}",
                mapper.writeValueAsString(new Username("一个人")));
        assertEquals(
                "{\"username\":\"\uD83D\uDE00*人\"}",
                mapper.writeValueAsString(new Username("\uD83D\uDE00脸人")));
    }

    @Test
    void should_mask_long_username() throws JsonProcessingException {
        assertEquals(
                "{\"username\":\"云淡***轻\"}",
                mapper.writeValueAsString(new Username("云淡风轻")));
        assertEquals(
                "{\"username\":\"AA***哥\"}",
                mapper.writeValueAsString(new Username("AAAJava批发王哥")));
        assertEquals(
                "{\"username\":\"\uD83D\uDE00\uD83E\uDD21***\uD83D\uDC7E\"}",
                mapper.writeValueAsString(new Username("\uD83D\uDE00\uD83E\uDD21\uD83D\uDC7Dis\uD83D\uDC7E")));
    }

    @Test
    void why_not_use_subString() {
        String str = "\uD83D\uDE00Smileys";
        assertNotEquals("Smileys", str.substring(1));
        assertEquals("Smileys", str.substring(2));
    }

    static class Username {
        @Sensitive(strategy = SOCIAL_MEDIA)
        public String username;

        public Username(String username) {
            this.username = username;
        }
    }
}
