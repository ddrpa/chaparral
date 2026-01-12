package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.annotation.Sensitive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static cc.ddrpa.chaparral.enums.DesensitizeStrategy.BANK_ACCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 本测试用例中使用的银行卡号均为虚拟卡号，使用在线工具随机生成
 */
class BankAccountDesensitizeTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_desensitize_debit_card() throws JsonProcessingException {
        String original = "6223071354746919940";
        assertEquals(
                "{\"card\":\"622307******9940\"}",
                mapper.writeValueAsString(new CardNumber(original)));
    }

    @Test
    void should_desensitize_credit_card() throws JsonProcessingException {
        String original = "6223071354746919";
        assertEquals(
                "{\"card\":\"622307******6919\"}",
                mapper.writeValueAsString(new CardNumber(original)));
    }

    @Test
    void should_handle_blank_and_null_value() throws JsonProcessingException {
        assertEquals(
                "{\"card\":\"\"}",
                mapper.writeValueAsString(new CardNumber("  ")));
        assertEquals(
                "{\"card\":null}",
                mapper.writeValueAsString(new CardNumber(null)));
    }

    static class CardNumber {
        @Sensitive(strategy = BANK_ACCOUNT)
        public String card;

        public CardNumber(String card) {
            this.card = card;
        }
    }
}