package helloworld.lambda2step.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ExceptionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        throw new Exception("Destination is null");
    }
}
