package cn.com.shopec.common.apppush;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class AppPushUtil {

	/**
	 * 对单个用户推送消息
	 * 
	 * @param cid
	 * @param title
	 * @param contant
	 * @return
	 */
	public static String pushSingleMessage(String cid, String title, String contant) {
		IGtPush push = IGtPushUtil.getPush();
		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title, contant, contant);
		SingleMessage message = MessageUtil.getSingleMessage(template);
		Target target = new Target();
		target.setAppId(IGtPushUtil.getAppId());
		target.setClientId(cid);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		return ret.getResponse().toString();
	}

	/**
	 * 对指定应用群推消息(应用通知模板)
	 * 
	 * @param appIdList
	 * @param title
	 * @param contant
	 * @param taskName
	 */
	public static String pushAppMessage(String title, String contant) {

		IGtPush push = IGtPushUtil.getPush();

		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title, contant, contant);
		AppMessage appMessage = MessageUtil.getAppMessage(template);

		// 推送给App的目标用户需要满足的条件
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(IGtPushUtil.getAppId());
		appMessage.setAppIdList(appIdList);

		/*
		 * //设置特点条件 AppConditions cdt = new AppConditions(); List<String>
		 * phoneTypeList = new ArrayList<String>(); //手机类型
		 * cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		 * List<String> provinceList = new ArrayList<String>(); //省份
		 * cdt.addCondition(AppConditions.REGION, provinceList); List<String>
		 * tagList = new ArrayList<String>(); //自定义tag
		 * cdt.addCondition(AppConditions.TAG,tagList);
		 * appMessage.setConditions(cdt);
		 */

		IPushResult ret = push.pushMessageToApp(appMessage);
		return ret.getResponse().toString();
	}

	/**
	 * 批量单推
	 * 
	 * @param cid
	 * @param title
	 * @param contant
	 * @return
	 */
	public static String pushBatchMessage(List<String> cids, String title, String contant) {

		IIGtPush push = IGtPushUtil.getPush();
		IBatch batch = push.getBatch();
		IPushResult ret = null;
		try {
			for (String cid : cids) {
				AbstractTemplate template = TemplateUtil.getNotificationTemplate(title, contant, contant);
				SingleMessage message = MessageUtil.getSingleMessage(template);
				Target target = new Target();
				target.setAppId(IGtPushUtil.getAppId());
				target.setClientId(cid);
				batch.add(message, target);
			}
			ret = batch.submit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.getResponse().toString();
	}

	/**
	 * 对指定列表用户推送消息
	 * 
	 * @param cids
	 * @param title
	 * @param contant
	 * @param transmissionContent
	 * @return
	 */
	public static String pushListMessage(List<String> cids, String title, String contant, String transmissionContent) {

		IGtPush push = IGtPushUtil.getPush();
		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title, contant, contant);
		ListMessage message = MessageUtil.getListMessage(template);

		// 配置推送目标
		List<Target> targets = new ArrayList<Target>();
		for (int i = 0; i < cids.size(); i++) {
			Target target = new Target();
			target.setAppId(IGtPushUtil.getAppId());
			target.setClientId(cids.get(i));
			targets.add(target);
		}

		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		return ret.getResponse().toString();
	}

	public static class PushtoSingle {
		// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
		private static String appId = "PO070Fc8HmAU7rfXj4EFN7";
		private static String appKey = "SXd5SRCDDPAvQrndpG6mn2";
		private static String masterSecret = "ycOPxHlnRS7B6gkeV7JmV8";

		static String CID = "f8a0e0ce66fc61cc90220dca726e8fe4";
		// 别名推送方式
		// static String Alias = "";
		static String host = "http://sdk.open.api.igexin.com/apiex.htm";

		public static void main(String[] args) throws Exception {
			IGtPush push = new IGtPush(host, appKey, masterSecret);
			LinkTemplate template = linkTemplateDemo();
			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			// 离线有效时间，单位为毫秒，可选
			message.setOfflineExpireTime(24 * 3600 * 1000);
			message.setData(template);
			// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
			message.setPushNetWorkType(0);
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(CID);
			// target.setAlias(Alias);
			IPushResult ret = null;
			try {
				ret = push.pushMessageToSingle(message, target);
			} catch (RequestException e) {
				e.printStackTrace();
				ret = push.pushMessageToSingle(message, target, e.getRequestId());
			}
			if (ret != null) {
				System.out.println(ret.getResponse().toString());
			} else {
				System.out.println("服务器响应异常");
			}
		}

		public static LinkTemplate linkTemplateDemo() {
			LinkTemplate template = new LinkTemplate();
			// 设置APPID与APPKEY
			template.setAppId(appId);
			template.setAppkey(appKey);

			Style0 style = new Style0();
			// 设置通知栏标题与内容
			style.setTitle("请输入通知栏标题");
			style.setText("请输入通知栏内容");
			// 配置通知栏图标
			style.setLogo("icon.png");
			// 配置通知栏网络图标
			style.setLogoUrl("");
			// 设置通知是否响铃，震动，或者可清除
			style.setRing(true);
			style.setVibrate(true);
			style.setClearable(true);
			template.setStyle(style);

			// 设置打开的网址地址
			template.setUrl("http://www.baidu.com");
			return template;
		}
	}

}
