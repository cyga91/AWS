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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static helloworld.constant.Constants.BUCKET_NAME_OUTPUT;
import static helloworld.constant.Constants.BUCKET_OUTPUT_KEY;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_HANDLER;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_ROLE;
import static helloworld.constant.Constants.LAMBDA_FUNCTION_RUNTIME;

public class Lambda {
    S3Bucket s3Bucket;

    public Lambda(S3Bucket s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = LoggerFactory.getLogger(Lambda.class);

    public static void createLambdaFunction(String lambdaFunctionName) {
        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_WEST_1).build();

            CreateFunctionRequest functionRequest = getFunctionRequest(lambdaFunctionName);
            awsLambda.createFunction(functionRequest);
        } catch (ServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
        checkLambdaFunction(lambdaFunctionName);
    }

    private static CreateFunctionRequest getFunctionRequest(String lambdaFunctionName) {
        CreateFunctionRequest functionRequest = new CreateFunctionRequest();
        functionRequest.setFunctionName(lambdaFunctionName);
        functionRequest.setRole(LAMBDA_FUNCTION_ROLE);
        FunctionCode code = new FunctionCode();
        code.setS3Bucket(BUCKET_NAME_OUTPUT);
        code.setS3Key(BUCKET_OUTPUT_KEY);
        functionRequest.setCode(code);
        functionRequest.setRuntime(LAMBDA_FUNCTION_RUNTIME);
        functionRequest.setHandler(LAMBDA_FUNCTION_HANDLER);

        return functionRequest;
    }

    public static void checkLambdaFunction(String lambdaFunctionName) {
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
