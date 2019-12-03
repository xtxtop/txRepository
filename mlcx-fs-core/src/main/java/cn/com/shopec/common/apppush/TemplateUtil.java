package cn.com.shopec.common.apppush;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class TemplateUtil {
	
	/**
	 * 得到默认的LinkTemplate
	 * 
	 * @param title
	 * @param content
	 * @return
	 */
	public static LinkTemplate getLinkTemplate(String title,String content,String linkUrl) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(IGtPushUtil.getAppId());
        template.setAppkey(IGtPushUtil.getAppKey());
        template.setStyle(getDefaultStyle(title, content));
        // 设置打开的网址地址
        template.setUrl(linkUrl);
		return template;
	}
	
	/**
	 * 得到默认的NotificationTemplate
	 * @param title
	 * @param content
	 * @param transmissionContent
	 * @return
	 */
	public static NotificationTemplate getNotificationTemplate(String title,String content,String transmissionContent) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(IGtPushUtil.getAppId());
        template.setAppkey(IGtPushUtil.getAppKey());
        template.setStyle(getDefaultStyle(title, content));
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionContent);
        return template;
    }

	/**
	 * 得到默认的style
	 * @param title
	 * @param content
	 * @return
	 */
	private static Style0 getDefaultStyle(String title, String content) {
		Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        return style;
	}
}
