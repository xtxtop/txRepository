package cn.com.shopec.mgt.mq;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import cn.com.shopec.mgt.deviceUploadPkgParser.DeviceUploadPkgCmd24;
import cn.com.shopec.mgt.deviceUploadPkgParser.DeviceUploadPkgParser;

public class CarGpsMsgConsumer implements MessageListener {

	private static Log log = LogFactory.getLog(CarGpsMsgConsumer.class);
	
	@Resource(name="sendDeviceMsgToClient")
	private SendDeviceMsgToClient sendDeviceMsgToClient;
	
	@Override
	public void onMessage(Message message) {
		log.info("receive message:" + message);
		System.err.println("receive message:" + message);
		String msg = new String(message.getBody());
		log.info("msg=" + msg);
		System.err.println("msg=" + msg);
		DeviceUploadPkgCmd24 cmd24 = (DeviceUploadPkgCmd24)DeviceUploadPkgParser.parseString2DeviceUploadPackage(msg);
		System.err.println(cmd24.toString());
		sendDeviceMsgToClient.sendCmd24MsgToClinet(cmd24);
		
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-rabbitmq.xml");
//
//		RabbitTemplate template = (RabbitTemplate) ctx.getBean("amqpTemplate_car_gps");
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
