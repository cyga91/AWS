package helloworld.lambda2step;

import com.fasterxml.jackson.databind.ObjectMapper;
import helloworld.lambda2step.camel.pipeline.CamelPipeline;
import helloworld.model.ReceivedOrder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
//    public class Lambda2ndStep implements RequestHandler<SQSEvent, String> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @SneakyThrows
//    @Override
//    public String handleRequest(SQSEvent sqsEvent, Context context) {
//        ReceivedOrder order = objectMapper.readValue(sqsEvent.getRecords().get(0).getBody(), ReceivedOrder.class);
//        //TODO move impl from main() here
//        return "200 Ok";
//    }
public class Lambda2ndStep {
    public static void main(String[] args) throws Exception {
        Lambda2ndStep lambda2ndStep = new Lambda2ndStep();
        ReceivedOrder order = lambda2ndStep.fetchOrder();
        processOrder(order);
    }

    private static void processOrder(ReceivedOrder order) throws Exception {
        CamelPipeline contextBuilder = new CamelPipeline();
        contextBuilder.processOrder(order);
    }

    private ReceivedOrder fetchOrder() throws IOException {
        ObjectMapper fakeObjectMapper = new ObjectMapper();
        ReceivedOrder order = fakeObjectMapper.readValue(readFileAsString(), ReceivedOrder.class);
        log.debug("Fetcher order: " + order);
        return order;
    }

    private String readFileAsString() throws IOException {
        return Files.readString(Paths.get(Objects.requireNonNull(Lambda2ndStep.class.getClassLoader()
                .getResource("validOrder.json")).getFile()));
    }
}
