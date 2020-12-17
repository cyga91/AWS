package helloworld.lambda2step.camel.router;

import helloworld.lambda2step.camel.error.CustomRouteBuilderErrorHandlingUtils;
import helloworld.lambda2step.camel.error.RouteBuilderErrorHandlingUtils;
import helloworld.lambda2step.camel.processor.ChinaProcessor;
import helloworld.lambda2step.camel.processor.EMEAProcessor;
import helloworld.lambda2step.camel.processor.ExceptionProcessor;
import helloworld.lambda2step.camel.processor.PriceProcessor;
import helloworld.lambda2step.camel.processor.USAProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;

import static helloworld.lambda2step.camel.error.RouteBuilderErrorHandlingUtils.configureOnExceptions;

@Component
@RequiredArgsConstructor
public class OrderRouter extends RouteBuilder {

    private static final String FAILURE_MESSAGE = "Failed due to connection error";

    private final PriceProcessor priceProcessor;
    private final ExceptionProcessor exceptionProcessor;
    private final ChinaProcessor chinaProcessor;
    private final USAProcessor usaProcessor;
    private final EMEAProcessor emeaProcessor;

    @Override
    public void configure(){
        CustomRouteBuilderErrorHandlingUtils.configureOnExceptions(this, "OrderRoutes");

        from("direct:start")
                .streamCaching()
                .routeId("orderEndpoint")
                .routeDescription("The processor below will validate received order and change to sent order form")
                .log(LoggingLevel.ERROR, "Received order::%{body}")
                .process(priceProcessor)
                .choice()
                    .when(header("Destination").isEqualTo(""))
                        .log(LoggingLevel.ERROR, "Invalid destination")
                        .to("direct:ExceptionCreateProcessor")
                    .otherwise()
                        .log(LoggingLevel.ERROR, "Valid destination")
                        .to("direct:DestinationProcessor");
//                .end()
//                .endChoice()
//                .log(LoggingLevel.INFO, "Final step")
//                .process(postProcessor)
//                .end();

        from("direct:DestinationProcessor")
                .routeId("destinationSplitter")
                .routeDescription("Split orders into multiple destinations")
                .choice()
                    .when(header("Destination").isEqualTo("china"))
                        .process(chinaProcessor)
                        .to("file:destination/?fileName=MyFile1.txt&charset=utf-8")
                    .when(header("Destination").isEqualTo("usa"))
                        .process(usaProcessor)
                        .to("file:destination/?fileName=MyFile2.txt&charset=utf-8")
                    .otherwise()
                        .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                        .to("http://localhost:8080/endpoint")
                        .process(emeaProcessor)
                        .to("file:destination/?fileName=MyFile3.txt&charset=utf-8")
                .end();

        from("direct:ExceptionCreateProcessor")
                .routeId("ExceptionCreateProcessor")
                .routeDescription("Throw exception if destination is null")
                .process(exceptionProcessor)
                .end();
    }
}
