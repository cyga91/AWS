package helloworld.lambda2step.camel.pipeline;

import helloworld.model.ReceivedOrder;
import helloworld.util.CamelContextUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class CamelPipeline {
    public void processOrder(ReceivedOrder order) throws Exception {
        CamelContext context = CamelContextUtil.getContext();
        sendBodyAndHeader(order, context);
//        receiveBody(context);
//        context.stop();
    }

    public void sendBodyAndHeader(ReceivedOrder order, CamelContext context) {
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBodyAndHeader("direct:start", order, "Destination", order.getDestination());
    }

//    private void receiveBody(CamelContext context) {
//        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
//        final var message = consumerTemplate.receiveBody("seda:end", String.class);
//    }
}
