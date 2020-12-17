package helloworld.lambda2step.camel.pipeline;

import com.github.tomakehurst.wiremock.WireMockServer;
import helloworld.model.ReceivedOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
@DisplayName("Should test pipeline")
class CamelPipelineTest {

    private static final String URL = "http://localhost:8080/endpoint";
    private final CamelPipeline camelPipeline = new CamelPipeline();

    @BeforeEach
    void setUp() throws Exception {
    }

    @DisplayName("Should test pipeline")
    @ParameterizedTest(name = "Should retry 3 times for status: {0}")
    @ValueSource(ints = {500, 503, 504})
    void test_processOrderWith5xxException(int status, WireMockServer wiremock) throws Exception {
        // given
        ReceivedOrder receivedOrder = new ReceivedOrder("usa", "adidas", "hoodie", "L");
        stubApiCall5xxResponse(status, wiremock);

        // when
        camelPipeline.processOrder(receivedOrder);

        // then
//        assertThrows(ConnectException.class, camelPipeline.processOrder(receivedOrder));
        wiremock.verify(4, postRequestedFor(urlEqualTo(URL)));
    }

    @Test
    @DisplayName("Should test pipeline")
    void test_processOrderSuccessfully() throws Exception {
        // given
        ReceivedOrder receivedOrder = new ReceivedOrder("usa", "adidas", "hoodie", "L");

        // when
        camelPipeline.processOrder(receivedOrder);

        // then
        assertTrue(true);
//        assertThrows(ConnectException.class, camelPipeline.processOrder());
    }

    private void stubApiCall5xxResponse(int status, WireMockServer wiremock) {
        wiremock.stubFor(post(URL)
                .willReturn(aResponse()
                        .withStatus(status)));
    }
}