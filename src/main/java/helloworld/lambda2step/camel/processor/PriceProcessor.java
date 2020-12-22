package helloworld.lambda2step.camel.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import helloworld.model.ReceivedOrder;
import helloworld.model.SentOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class PriceProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws JsonProcessingException {
        final var message = exchange.getIn();
        log.error("Received header: " + message.getHeaders());
        exchange.getIn().setBody(createSentOrder(message.getBody(ReceivedOrder.class)));
    }

    private SentOrder createSentOrder(ReceivedOrder receivedOrder) {
        return new SentOrder(receivedOrder.getDestination(), receivedOrder.getBrandName(), receivedOrder.getClothType(),
                receivedOrder.getSize(), 10.0);
    }
}
