package helloworld.util;

import helloworld.lambda2step.camel.processor.ChinaProcessor;
import helloworld.lambda2step.camel.processor.EMEAProcessor;
import helloworld.lambda2step.camel.processor.ExceptionProcessor;
import helloworld.lambda2step.camel.processor.PriceProcessor;
import helloworld.lambda2step.camel.processor.USAProcessor;
import helloworld.lambda2step.camel.router.OrderRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelContextUtil {
    public static CamelContext camelContext;

    public static CamelContext buildContext() throws Exception {
        camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new OrderRouter(new PriceProcessor(), new ExceptionProcessor(), new ChinaProcessor(),
                new USAProcessor(), new EMEAProcessor()));
        camelContext.start();
        return camelContext;
    }

    public static CamelContext getContext() throws Exception {
        if (camelContext == null) {
            return buildContext();
        } else {
            return camelContext;
        }
    }
}
