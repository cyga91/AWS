package helloworld.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.model.SentOrder;

public class Mapper {
    public static Object writeObjectAsString(SentOrder sentOrder) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(sentOrder);
    }
}
