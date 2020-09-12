package helloworld.s3_lambda_sqs;

import com.amazonaws.services.sqs.AmazonSQS;
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
import static helloworld.constant.Constants.SQS_FOUND;
import static helloworld.constant.Constants.WITH_DELAY_SECONDS;

public class Sqs {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Sqs.class);

    private static CreateQueueResult getAmazonSQS(AmazonSQS sqs, String queueName) {
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

    private static void sendSQSMessage(AmazonSQS sqs, String queueName) {
        getAmazonSQS(sqs, queueName);
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

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
