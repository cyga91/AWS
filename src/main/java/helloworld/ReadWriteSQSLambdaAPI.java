package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static helloworld.constant.Constants.HEADER_NAME;
import static helloworld.constant.Constants.HEADER_VALUE;
import static helloworld.constant.Constants.MESSAGE_SENT_TO_API;
import static helloworld.constant.Constants.SQS_RECEIVED_MESSAGE;

public class ReadWriteSQSLambdaAPI implements RequestHandler<SQSEvent, String> {
    private static final String POSTS_API_URL = "http://www.httpbin.org/post";

    @SneakyThrows
    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        String result = null;
//
//        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
//            result = msg.getBody();
//            logger.log(SQS_RECEIVED_MESSAGE + result);
//        }
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = createSendHttpRequest(result);
//
//        client.send(request, HttpResponse.BodyHandlers.ofString());
//        logger.log(MESSAGE_SENT_TO_API + request.toString());

        return "200 Ok";
    }

    private HttpRequest createSendHttpRequest(String result) {
        return HttpRequest.newBuilder()
                .uri(URI.create(POSTS_API_URL))
                .header(HEADER_NAME, HEADER_VALUE)
                .timeout(Duration.ofSeconds(10))
                .POST(HttpRequest.BodyPublishers.ofString(result))
                .build();
    }
}
