package helloworld.s3_lambda_sqs;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.CreateFunctionRequest;
import com.amazonaws.services.lambda.model.FunctionCode;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static helloworld.constant.Constants.LAMBDA_FUNCTION_RUNTIME;

@Data
@Builder
public class Lambda {
    private String lambdaName;
    private String role;
    private String s3BucketName;
    private String s3BucketKey;
    private String handler;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Lambda.class);

    public void createLambdaFunction() {
        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_WEST_1).build();

            CreateFunctionRequest functionRequest = getFunctionRequest();
            awsLambda.createFunction(functionRequest);
        } catch (ServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
        checkLambdaFunction(lambdaName);
    }

    private CreateFunctionRequest getFunctionRequest() {
        CreateFunctionRequest functionRequest = new CreateFunctionRequest();
        functionRequest.setFunctionName(lambdaName);
        functionRequest.setRole(role);
        FunctionCode code = new FunctionCode();
        code.setS3Bucket(s3BucketName);
        code.setS3Key(s3BucketKey);
        functionRequest.setCode(code);
        functionRequest.setRuntime(LAMBDA_FUNCTION_RUNTIME);
        functionRequest.setHandler(handler);

        return functionRequest;
    }

    public void checkLambdaFunction(String lambdaFunctionName) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambdaFunctionName);

        InvokeResult invokeResult = null;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_WEST_1).build();

            invokeResult = awsLambda.invoke(invokeRequest);
            String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

            logger.info("ANS is: " + ans);
        } catch (ServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }

        logger.info("" + invokeResult.getStatusCode());
    }
}
