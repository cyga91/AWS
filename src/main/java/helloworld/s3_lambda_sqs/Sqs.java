package helloworld.s3_lambda_sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static helloworld.constant.Constants.MESSAGE_SENT;
import static helloworld.constant.Constants.SQS_FOUND;

public class Sqs {
    private AmazonSQS sqs;
    private String queueName;

    public Sqs(AmazonSQS sqs, String queueName) {
        this.sqs = sqs;
        this.queueName = queueName;
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Sqs.class);

    public CreateQueueResult getAmazonSQS() {
        // Create queue if doesn't exists
        CreateQueueResult queue = null;
        try {
            queue = sqs.createQueue(queueName);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                logger.error(e.getMessage());
            }
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
        logger.info(SQS_FOUND + queueName);
        return queue;
    }

    public void sendSQSMessage(String message) {
        getAmazonSQS();
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

        // Send multiple messages to the queue
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);
        logger.info(MESSAGE_SENT);
    }
}
