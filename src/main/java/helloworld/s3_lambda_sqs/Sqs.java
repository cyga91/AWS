package helloworld.s3_lambda_sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static helloworld.constant.Constants.MESSAGE;
import static helloworld.constant.Constants.MESSAGE_SENT;
import static helloworld.constant.Constants.QUEUE_NAME;
import static helloworld.constant.Constants.WITH_DELAY_SECONDS;

public class Sqs {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Sqs.class);

    public static void main(String[] args) {
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        // Create queue if doesn't exists
        try {
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                logger.error(e.getMessage());
            }
        }

        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

        // Send multiple messages to the queue
        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(queueUrl)
                .withEntries(
                        new SendMessageBatchRequestEntry(
                                "msg_1", MESSAGE + 1),
                        new SendMessageBatchRequestEntry(
                                "msg_2", MESSAGE + 2)
                                .withDelaySeconds(WITH_DELAY_SECONDS));
        sqs.sendMessageBatch(send_batch_request);
        logger.info(MESSAGE_SENT);
    }
}
