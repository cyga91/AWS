package helloworld.lambda2step.camel.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class EMEAProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws JsonProcessingException {
        final var message = exchange.getIn();
        log.error("Received header: " + message.getHeaders());
        exchange.getIn().setBody(message.getBody(String.class).toUpperCase());
    }
}
