package cn.com.shopec.mgt.mq;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class CarWarningMsgConsumer implements MessageListener {

	private static Log log = LogFactory.getLog(CarWarningMsgConsumer.class);
	
	@Resource(name="sendDeviceMsgToClient")
	private SendDeviceMsgToClient sendDeviceMsgToClient;
	
	@Override
	public void onMessage(Message message) {
		log.info("receive message:" + message);
		System.err.println("receive message:" + message);
		String msg = new String(message.getBody());
		log.info("msg=" + msg);
		System.err.println("msg=" + msg);
		sendDeviceMsgToClient.sendWarningMsgToClinet(msg);
		
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-rabbitmq.xml");
//
//		RabbitTemplate template = (RabbitTemplate) ctx.getBean("amqpTemplate_car_useStatus");
//		template.setMandatory(true);
//		if (!template.isConfirmListener()) {
//			template.setConfirmCallback(new ConfirmCallback() {
//				public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//					System.out.println("ack: " + ack + ". correlationData: " + correlationData + "cause : " + cause);
//				}
//			});
//		}
	}

}
