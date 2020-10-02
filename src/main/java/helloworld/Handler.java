package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class Handler implements RequestHandler<SQSEvent, String> {
    public Handler() {
    }
    @Override
    public String handleRequest(SQSEvent event, Context context) {
        return "OKAY";
    }
}
