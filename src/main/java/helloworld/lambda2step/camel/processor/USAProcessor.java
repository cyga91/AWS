package helloworld.lambda2step.camel.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import helloworld.lambda2step.camel.service.OrderService;
import helloworld.model.SentOrder;
import helloworld.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class USAProcessor implements Processor, OrderService {
    @Override
    public void process(Exchange exchange) throws JsonProcessingException {
        final var message = exchange.getIn();
        log.error("Received header: " + message.getHeaders());
        exchange.getIn().setBody(Mapper.writeObjectAsString(updateSentOrder(message.getBody(SentOrder.class))));
    }

    @Override
    public SentOrder updateSentOrder(SentOrder sentOrder) {
        sentOrder.setPrice(sentOrder.getPrice() + 20);
        return sentOrder;
    }
}
