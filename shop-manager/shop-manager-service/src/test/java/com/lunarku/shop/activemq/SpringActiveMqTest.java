package com.lunarku.shop.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringActiveMqTest {
	
	private JmsTemplate jmsTemplate = null;
	private Destination destination = null;
	private ApplicationContext context = null;
	
	private String path = "classpath:spring/applicationContext-activemq.xml";
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext(path);
		jmsTemplate = context.getBean(JmsTemplate.class);
		//destination = (Destination) context.getBean("itemQueue");
		destination = (Destination) context.getBean("itemTopic");
	}
	
	/**
	 * 发送消息
	 */
	@Test
	public void testProducer() {
		
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//TextMessage textMessage = session.createTextMessage("spring activemq send message");
				TextMessage textMessage = session.createTextMessage("100000000000");
				return textMessage;
			}
		});
	}
	
}
