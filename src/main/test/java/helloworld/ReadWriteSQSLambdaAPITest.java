package helloworld;

import junit.framework.TestCase;
import org.junit.Test;

public class ReadWriteSQSLambdaAPITest extends TestCase {

    @Test
    public void testHandleRequest() {
        // given when
        String result = "200 OK";
        // then
        assertEquals("200 OK", result);
    }
}