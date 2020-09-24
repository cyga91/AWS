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
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static helloworld.constant.Constants.LAMBDA_FUNCTION_RUNTIME;
import static helloworld.exception.ServiceException.CANNOT_CREATE_LAMBDA;

@Data
@Builder
public class Lambda {
    @NonNull
    private String lambdaName;
    @NonNull
    private String role;
    @NonNull
    private String s3BucketName;
    @NonNull
    private String s3BucketKey;
    @NonNull
    private String handler;

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
            throw new ServiceException(CANNOT_CREATE_LAMBDA);
        }
        checkLambdaFunction(lambdaName);
    }

    private CreateFunctionRequest getFunctionRequest() {
        FunctionCode code = new FunctionCode().withS3Bucket(s3BucketName).withS3Bucket(s3BucketKey);

        return new CreateFunctionRequest()
                .withFunctionName(lambdaName)
                .withRole(role)
                .withCode(code)
                .withRuntime(LAMBDA_FUNCTION_RUNTIME)
                .withHandler(handler);
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
            throw new ServiceException(CANNOT_CREATE_LAMBDA);
        }

        logger.info("" + invokeResult.getStatusCode());
    }
}
