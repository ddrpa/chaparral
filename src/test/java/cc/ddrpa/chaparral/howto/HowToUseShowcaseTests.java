package cc.ddrpa.chaparral.howto;

import cc.ddrpa.chaparral.howto.entity.Address;
import cc.ddrpa.chaparral.howto.entity.SystemUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class HowToUseShowcaseTests {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Integer THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING = 42;
    private static final SystemUser user = new SystemUser()
            .setRealName("张三")
            .setIdCard("330106190103070133")
            .setBankAccount("6223071354746919940")
            .setEmail("no_reply@ddrpa.cc")
            .setMobile("18824769941")
            .setAddress(new Address("浙江省", "杭州市", "西湖区桃源岭1号", "310001"))
            .setFieldShouldBeNullingOut(THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING);


    @Test
    void test() throws JsonProcessingException {
        System.out.println(mapper.writeValueAsString(user));
    }
}