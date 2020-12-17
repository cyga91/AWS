package helloworld.lambda2step.camel.service;

import helloworld.model.SentOrder;

public interface OrderService {
    SentOrder updateSentOrder(SentOrder sentOrder);
}
