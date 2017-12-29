package com.gcd.common.messaging;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.gcd.common.bean.InputNumber;

@Component
public class MessageConsumer {

	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;

	private static final Logger logger = Logger.getLogger(MessageConsumer.class);

	/**
	 * Retrieves the numbers from the head of the queue and removes it from the
	 * queue
	 * 
	 * @return InputNumber retrieved from the queue
	 * @return null if queue is empty
	 */
	public InputNumber getMessage(String queueName) {
		logger.debug("Enter method: GcdMessageConsumer");
		InputNumber numBean = null;
		Object obj = jmsTemplate.receiveAndConvert(queueName);
		if (obj instanceof InputNumber)
			numBean = (InputNumber) obj;

		logger.debug("Exit method: GcdMessageConsumer");
		return numBean;

	}

}
