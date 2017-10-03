package com.jgg.sdp.logger;

import javax.jms.*;

public class LoggerListener implements MessageListener {
	
    private String queueName;
    public LoggerListener(String queueName) {
        this.queueName = queueName;
    }

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(queueName + " received "
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
