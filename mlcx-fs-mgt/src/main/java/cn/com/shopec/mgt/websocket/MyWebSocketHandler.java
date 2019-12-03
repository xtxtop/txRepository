package cn.com.shopec.mgt.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MyWebSocketHandler implements WebSocketHandler {

	// 在线用户列表
	private static final Map<String, WebSocketSession> users;
	private String flag;
	static {
		users = new HashMap<>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("成功建立连接");
	}

	// 处理信息
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		flag = (String) message.getPayload();
		String userId = flag+getClientId(session);
		System.err.println(userId);
		if (userId != null) {
			users.put(userId, session);
		}
	}

	// 处理传输时异常
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("连接出错");
		users.remove(getClientId(session));
	}

	// 关闭 连接时
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

		System.out.println("连接已关闭：" + closeStatus);
		users.remove(getClientId(session));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 获取用户标识
	 * 
	 * @param session
	 * @return
	 */
	private String getClientId(WebSocketSession session) {
		try {
			return flag+session.getId();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 发送信息给指定用户
	 * 
	 * @param clientId
	 * @param message
	 * @return
	 */
	public boolean sendMessageToUser(Integer clientId, TextMessage message) {
		if (users.get(clientId) == null)
			return false;
		WebSocketSession session = users.get(clientId);
		System.out.println("sendMessage:" + session);
		if (!session.isOpen())
			return false;
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 广播信息给车辆监控页面
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessageToCarMonitorUsers(TextMessage message) {
		boolean allSendSuccess = false;
		Set<String> clientIds = users.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				if(clientId.startsWith("carMonitor")){
					session = users.get(clientId);
					if (session.isOpen()) {
						session.sendMessage(message);
						allSendSuccess = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				allSendSuccess = false;
			}
		}
		return allSendSuccess;
	}
	/**
	 * 广播信息给非订单用车页面
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessageToNoOrderUsers(TextMessage message) {
		boolean allSendSuccess = false;
		Set<String> clientIds = users.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				if(clientId.startsWith("noOrder")){
					session = users.get(clientId);
					if (session.isOpen()) {
						session.sendMessage(message);
						allSendSuccess = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				allSendSuccess = false;
			}
		}
		return allSendSuccess;
	}
	/**
	 * 广播信息给所有客户端页面
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessageToAllUsers(TextMessage message) {
		boolean allSendSuccess = false;
		Set<String> clientIds = users.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				session = users.get(clientId);
				if (session.isOpen()) {
					session.sendMessage(message);
					allSendSuccess = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				allSendSuccess = false;
			}
		}

		return allSendSuccess;
	}
}