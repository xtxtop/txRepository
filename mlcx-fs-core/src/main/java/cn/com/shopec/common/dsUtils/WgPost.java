package cn.com.shopec.common.dsUtils;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import net.sf.json.JSONObject;

public class WgPost {
	// 如果不足32位，用0递归去补足。
	public static String fillMD5(String md5) {
		return md5.length() == 32 ? md5 : fillMD5("0" + md5);
	}

	public static JSONObject post(String lockid, String url) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("lockid", lockid);
		map.put("appId", ConstantCd.appId);
		map.put("time", ECDateUtils.formatDate(new Date()));
		// 新建一个客户对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 新建一个HttpPost请求的对象 --并将uri传给这个对象
		HttpPost post = new HttpPost(ConstantCd.headUrl + url);
		post.setHeader("Content-Type", "application/json");
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("lockid", lockid);
		jsonParam.put("appId", ConstantCd.appId);
		jsonParam.put("sign", PartUtils.signMD5(PartUtils.sign(map) + ConstantCd.appSecret, ConstantCd.upCase));
		jsonParam.put("time", map.get("time"));
		post.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
		// 新建一个响应对象来接收客户端执行post的结果
		CloseableHttpResponse response = client.execute(post);
		// 7.从响应对象中提取需要的结果-->实际结果,与预期结果进行对比
		// if (response.getStatusLine().getStatusCode() == 200) {
		// System.out.println(EntityUtils.toString(response.getEntity(),
		// "utf-8"));
		// JSONObject jsStr =
		// JSONObject.fromObject(EntityUtils.toString(response.getEntity(),
		// "utf-8"));
		// JSONObject jsStr2 = (JSONObject) jsStr.get("Data");
		// String id = jsStr2.getString("Id");
		// System.out.println(id);
		return JSONObject.fromObject(EntityUtils.toString(response.getEntity(), "utf-8"));
	}

	public static void main(String[] args) throws Exception {
		// post("1001dc", Constant.moveLockUp);
		System.out.println(post("359369082130309", "/Api/Nbiot/GetLockState"));
	}
}
