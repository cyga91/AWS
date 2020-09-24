package helloworld.s3_lambda_sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static helloworld.constant.Constants.MESSAGE_SENT_TO_QUEUE;
import static helloworld.constant.Constants.SQS_FOUND;
import static helloworld.constant.Constants.WITH_DELAY_SECONDS;

public class Sqs {
    @NonNull
    private final AmazonSQS SQS;
    @NonNull
    private final String QUEUE_NAME;

    public Sqs(AmazonSQS SQS, String QUEUE_NAME) {
        this.SQS = SQS;
        this.QUEUE_NAME = QUEUE_NAME;
    }

    private static final Logger logger = LoggerFactory.getLogger(Sqs.class);

    public CreateQueueResult getAmazonSQS() {
        // Create queue if doesn't exists
        CreateQueueResult QUEUE_RESULT = null;
        try {
            QUEUE_RESULT = SQS.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
        logger.info(SQS_FOUND + QUEUE_NAME);
        return QUEUE_RESULT;
    }

    public void sendSQSMessage(String message) {
        getAmazonSQS();
        String queueUrl = SQS.getQueueUrl(QUEUE_NAME).getQueueUrl();

        SendMessageRequest SEND_MSG_REQUEST = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(WITH_DELAY_SECONDS);
        SQS.sendMessage(SEND_MSG_REQUEST);
        logger.info(MESSAGE_SENT_TO_QUEUE);
    }
}
