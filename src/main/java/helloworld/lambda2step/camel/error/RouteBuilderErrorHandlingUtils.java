package helloworld.lambda2step.camel.error;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.NoSuchEndpointException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;

import java.net.ConnectException;
import java.net.UnknownHostException;

@Slf4j
@UtilityClass
public class RouteBuilderErrorHandlingUtils {
    private static final String FAILURE_MESSAGE = "Failed due to connection error";

    public static void configureOnExceptions(RouteBuilder r, String routeName) {
        r.errorHandler(
                r.defaultErrorHandler()
                        .maximumRedeliveries(1)
                        .redeliveryDelay(1L)
                        .asyncDelayedRedelivery()
                        .retryAttemptedLogLevel(LoggingLevel.ERROR));

        r.onException(UnknownHostException.class)
                .log("onException(UnknownHostException.class)" + routeName + FAILURE_MESSAGE)
                .handled(false)
                .retryAttemptedLogLevel(LoggingLevel.ERROR)
                .stop();

        r.onException(HttpOperationFailedException.class)
                .onWhen(exchange ->
                        exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class)
                                .getStatusCode() < 500)
                .handled(false)
                .stop();

        r.onException(HttpOperationFailedException.class)
                .onWhen(exchange ->
                        exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class)
                                .getStatusCode() >= 500)
                .maximumRedeliveries(3)
                .redeliveryDelay(1000L)
                .handled(false)
                .stop();

        r.onException(NoSuchEndpointException.class)
                .log("onException(NoSuchEndpointException.class)" + routeName + FAILURE_MESSAGE)
                .handled(false)
                .stop();

        r.onException(ConnectException.class)
                .log("onException(ConnectException.class)" + routeName + FAILURE_MESSAGE)
                .handled(false)
                .stop();

        r.onException(Exception.class)
                .log("onException()" + routeName + FAILURE_MESSAGE)
                .handled(false)
                .stop();
    }
}
