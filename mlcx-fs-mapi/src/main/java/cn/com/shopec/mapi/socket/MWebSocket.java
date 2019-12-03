package cn.com.shopec.mapi.socket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;

import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.ml.service.CLoveCarService;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.service.CParkOrderService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import net.sf.json.JSONObject;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class MWebSocket {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<MWebSocket> webSocketSet = new CopyOnWriteArraySet<MWebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	private CParkingService cParkingService;

	private CParkOrderService cParkOrderService;

	private CLoveCarService cLoveCarService;

	private MemberService memberService;

	{
		ApplicationContext act = ApplicationContextRegister.getApplicationContext();
		cParkingService = act.getBean(CParkingService.class);
		cParkOrderService = act.getBean(CParkOrderService.class);
		cLoveCarService = act.getBean(CLoveCarService.class);
		memberService = act.getBean(MemberService.class);
	}

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 * @throws Exception
	 */
	@OnMessage
	public void onMessage(String message) throws Exception {
		System.out.println("来自客户端的消息:" + message);
		// 群发消息
		for (MWebSocket item : webSocketSet) {
			try {
				StringBuilder sendMsg = new StringBuilder();
				JSONObject jsStr = JSONObject.fromObject(message);
				String service = jsStr.getString("service");// 接口名称
				if (null != service && !service.equals("")) {
					switch (service) {
					case "checkKey":// 客户端认证
						String parkid = jsStr.getString("parkid");// 车场ID
						CParking cp = cParkingService.getCParking(parkid);
						if (cp != null) {
							sendMsg.append("{\"result_code\":\"0\",\"service\":\"" + service + "\",\"message\":\""
									+ "认证成功" + "\"}");
						} else {
							sendMsg.append("{\"result_code\":\"1\",\"service\":\"" + service + "\",\"message\":\""
									+ "认证失败，无此车场" + "\"}");
							item.sendMessage(sendMsg.toString());
							webSocketSet.remove(this.session);
						}
						break;
					case "heartbeat":// 客户端认证
						String pid = jsStr.getString("parkid");// 车场ID
						CParking cping = cParkingService.getCParking(pid);
						if (cping != null) {
							sendMsg.append("{\"result_code\":\"0\",\"service\":\"" + service + "\",\"message\":\""
									+ "在线" + "\"}");
						} else {
							sendMsg.append("{\"result_code\":\"1\",\"service\":\"" + service + "\",\"message\":\""
									+ "离线" + "\"}");
							item.sendMessage(sendMsg.toString());
							webSocketSet.remove(this.session);
						}
						break;
					case "uploadcarinfo":// 上传车辆信息
						DealCore dc = new DealCore();
						String sm = dc.uploadcarinfo(memberService, cLoveCarService, cParkOrderService, cParkingService,
								jsStr, sendMsg);
						break;
					case "leavecarinfo":// 上传车辆信息
						DealCore dc1 = new DealCore();
						String sm1 = dc1.leavecarinfo(memberService, cLoveCarService, cParkOrderService,
								cParkingService, jsStr, sendMsg);
						break;
					default:
						sendMsg.append("{\"result_code\":\"1\",\"service\":\"" + service + "\",\"message\":\""
								+ "service参数错误" + "\"}");
						webSocketSet.remove(this.session);
						break;
					}
				}
				System.out.println("给客户端发送的消息：" + sendMsg.toString());
				item.sendMessage(sendMsg.toString());
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		MWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		MWebSocket.onlineCount--;
	}
}