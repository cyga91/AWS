//package helloworld.lambda2step.camel.pipeline;
//
//import com.github.tomakehurst.wiremock.WireMockServer;
//import helloworld.model.ReceivedOrder;
//import lombok.val;
//import org.apache.camel.NoSuchEndpointException;
//import org.apache.camel.http.base.HttpOperationFailedException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.net.ConnectException;
//import java.net.UnknownHostException;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.client.WireMock.post;
//import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
//import static java.util.Arrays.asList;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith({MockitoExtension.class})
//@DisplayName("Should test pipeline")
//class CamelPipelineTest {
//
//    private static final String URL = "http://localhost:8080/endpoint";
//    private final CamelPipeline camelPipeline = new CamelPipeline();
//
//    @BeforeEach
//    void setUp() throws Exception {
//    }
//
//    @DisplayName("Should test pipeline")
//    @ParameterizedTest(name = "Should retry 3 times for status: {0}")
//    @ValueSource(ints = {500, 503, 504})
//    void test_processOrderWith5xxException(int status, WireMockServer wiremock) throws Exception {
//        // given
//        ReceivedOrder receivedOrder = new ReceivedOrder("usa", "adidas", "hoodie", "L");
//        stubApiCall5xxResponse(status, wiremock);
//
//        // when
//        camelPipeline.processOrder(receivedOrder);
//
//        // then
////        assertThrows(ConnectException.class, camelPipeline.processOrder(receivedOrder));
//        wiremock.verify(4, postRequestedFor(urlEqualTo(URL)));
//    }
//
//    @Test
//    @DisplayName("Should test pipeline")
//    void test_processOrderSuccessfully() throws Exception {
//        // given
//        ReceivedOrder receivedOrder = new ReceivedOrder("usa", "adidas", "hoodie", "L");
//
//        // when
//        camelPipeline.processOrder(receivedOrder);
//
//        // then
//        assertTrue(true);
////        assertThrows(ConnectException.class, camelPipeline.processOrder());
//    }
//
//    private void stubApiCall5xxResponse(int status, WireMockServer wiremock) {
//        wiremock.stubFor(post(URL)
//                .willReturn(aResponse()
//                        .withStatus(status)));
//    }
//}
//
////
////        for (Class<? extends Exception> exceptionClass : exceptionClasses) {
////            val mockAppender = AppenderHelper.newMockAppender();
////
////            // given: an UnknownHostException is thrown while executing the route
////            doThrow(exceptionClass).when(mockProcessor).process(any());
////
////            // expect: the route shouldn't reach the end
////            mockResult.expectedMessageCount(0);
////
////            // when: the route gets triggered
////            val exception = catchThrowable(() -> template.sendBody(""));
////
////            // then: the expected conditions are satisfied
////            mockResult.assertIsSatisfied();
////
////            // and the exception is propagated (not 'handled') with no retries
////            Assertions.assertThat(exception).isNotNull().hasCauseInstanceOf(exceptionClass);
////            verify(mockProcessor, times(1)).process(any());
////
////            // and there should be a log produced for this route with the orderId that failed
////            verify(mockAppender, times(1)).doAppend(argThat(logEvent ->
////                    logEvent.getMessage().contains(ROUTE_NAME) && logEvent.getMessage().contains("failed for ofoaOrderId")
////            ));
////
////            // reset (only necessary because we're running multiple loops; usually cleaned up by JUnit between each test
////            reset(mockProcessor);
////        }
//    }