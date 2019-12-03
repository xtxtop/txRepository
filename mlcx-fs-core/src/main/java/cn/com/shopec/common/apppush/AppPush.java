package cn.com.shopec.common.apppush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 个推提供JAVA、C#、PHP、Python等多种语言版本的服务端API SDK，可以和各种第三方应用服务器技术架构进行对接。
 * 为了最大程度提高消息推送性能，第三方开发者需要根据业务需求合理选择消息推送形式。如果是针对每 个用户进行定制化的消息推送、
 * 或是实现类似IM的点对点消息，请采用单推消息形式（SingleMessage）；如果需要根据特定条件筛选出一批CID 后推送相同的内容，
 * 请选择批量推送形式（ListMessage）；如果希望针对省市或全量用户进行推送，请选择群推形式（AppMessage）
 * @author fly
 * @date 2017-09-20
 */

public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "PO070Fc8HmAU7rfXj4EFN7";
    private static String appKey = "SXd5SRCDDPAvQrndpG6mn2";
    private static String masterSecret = "ycOPxHlnRS7B6gkeV7JmV8";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("行知租车国庆大回馈");
        template.setText("免费无极限！");
        template.setUrl("http://www.shopec.com.cn");

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}
