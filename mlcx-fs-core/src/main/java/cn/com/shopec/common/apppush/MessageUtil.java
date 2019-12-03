package cn.com.shopec.common.apppush;

import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.template.AbstractTemplate;

public class MessageUtil {

	
	private static final int OFFLINE_EXPIRE_TIME = 24 * 3600 * 1000;

	public static SingleMessage getSingleMessage(AbstractTemplate template){
		SingleMessage singleMessage = new SingleMessage();
		singleMessage.setData(template);
		 // 设置消息离线，并设置离线时间
		singleMessage.setOffline(true);
		singleMessage.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);// 离线有效时间，单位为毫秒，可选
		singleMessage.setPushNetWorkType(0); // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		return singleMessage;
	}
	
	public static ListMessage getListMessage(AbstractTemplate template){
        ListMessage listMessage = new ListMessage();
        listMessage.setData(template);
        
        // 设置消息离线，并设置离线时间
        listMessage.setOffline(true);
        listMessage.setOfflineExpireTime(OFFLINE_EXPIRE_TIME); // 离线有效时间，单位为毫秒，可选
        listMessage.setPushNetWorkType(0); // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		return listMessage;
	}
	
	public static AppMessage getAppMessage(AbstractTemplate template){
		AppMessage appMessage = new AppMessage();
        appMessage.setData(template);

        // 设置消息离线，并设置离线时间
        appMessage.setOffline(true);
        appMessage.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);// 离线有效时间，单位为毫秒，可选
        appMessage.setPushNetWorkType(0); // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        appMessage.setSpeed(200);//全量推送个推控制下发速度在100条/秒，只有toApp支持定速推送。
        return appMessage;
	}
	
}
