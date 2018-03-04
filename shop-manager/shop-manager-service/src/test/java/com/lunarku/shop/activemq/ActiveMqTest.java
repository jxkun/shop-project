package com.lunarku.shop.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * 测试activemq的基本操作
 */
public class ActiveMqTest {

	/**
	 * 测试activemq 的 queue, 向queue中发送消息
	 * queue中的消息在服务器中默认持久化，等待消费者消费
	 * @throws Exception
	 */
	@Test
	public void testQueueProducer() throws Exception {
		// 1、 创建一个连接工厂对象ConnectionFactory。需要指定mq服务的ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
		// 2、使用ConnectionFactory创建一个连接Connection
		Connection connection = connectionFactory.createConnection();
		// 3、 开启连接，调用connection的start方法
		connection.start();
		// 4、使用Connection对象创建一个Session对象
		// 参数1： 是否开启事务， 一般不适用事务。保证数据的最终一致，可以使用消息队列
		// 若参数1设置为true，则第二个参数自动忽略； 若设置为false，则 参数2：表示消息的应答模式，一般设置为自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用Session对象创建一个Destination对象，两种形式queue、topic。
		// 参数： 消息队列的名字
		Queue queue = session.createQueue("test-queue");
		// 6、使用Session对象创建一个Producer对象
		MessageProducer producer = session.createProducer(queue);
		// 7、创建一个TextMassage对象
		// 方式一：
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello activemq");*/
		// 方式二
		TextMessage textMessage = session.createTextMessage("hello activemq");
		// 8、发送消息
		producer.send(textMessage);
		// 9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 测试activemq中的queue， 从queue中取消息
	 * @throws Exception
	 */
	@Test
	public void testQueueConsumer() throws Exception {
		// 1、创建一个连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
		// 2、使用工厂创建连接
		Connection connection = connectionFactory.createConnection();
		// 3、 开启连接
		connection.start();
		// 4、使用连接创建创建Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用Session创建一个Destination， Destination应该和消息的发送端一致
		Queue queue = session.createQueue("test-queue");
		// 6、使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(queue);
		// 7、向Consumer对象中设置一个MessageListener对象，用来接受消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage)message;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		// 或者等待键盘输入， 等接收到消息后，按任意键退出等待
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 测试activemq中的topic， 向topic中发送消息
	 * topic 中消息在服务器中默认不持久化，消费者没接收到就没有了
	 * @throws Exception
	 */
	public static void testTopicProducer() throws Exception {
		// 1、创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
		// 2、创建连接
		Connection connection = connectionFactory.createConnection();
		// 3、开启连接
		connection.start();
		// 4、创建Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、创建Destination， 使用topic
		Topic topic = session.createTopic("test-topic");
		// 6、创建一个Producer对象
		MessageProducer producer = session.createProducer(topic); 
		// 7、创建一个TextMessage对象
		TextMessage textMessage = session.createTextMessage("topic: hello activemq");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 测试activemq中的topic， 从topic中获取消息
	 * @throws Exception
	 */
	public static  void testTopicConsumer() throws Exception{
		// 1、创建一个连接工厂
		ConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");;
		// 2、使用连接工厂创建一个连接
		Connection connection = mqConnectionFactory.createConnection();
		// 3、开启连接
		connection.start();
		// 4、使用连接创建Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用Session创建一个Destination， Destination应该和消息的发送发保持一致
		Topic topic = session.createTopic("test-topic");//test-topic itemTopic
		// 6、使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(topic);
		// 7、向Consumer对象中设置一个MessageListener对象，用来接收消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage)message;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 测试广播
	 * 使用test时，test标注的方法不能为静态
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						testTopicConsumer();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
