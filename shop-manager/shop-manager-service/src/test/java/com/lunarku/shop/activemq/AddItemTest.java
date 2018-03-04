package com.lunarku.shop.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class AddItemTest {
	
	@Test
	public void testAddItem() throws Exception {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://192.168.25.131:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("itemTopic");
		
		MessageProducer producer = session.createProducer(topic);
		TextMessage textMessage = session.createTextMessage("1029944528");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();
		
		System.in.read();
	}
}
