package cn.com.shopec.common.dsUtils;

import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;
import cn.com.shopec.core.ml.model.BillingScheme;

/**
 * @author daiyuanbao
 * @category 猛龙出行 url
 *
 */
public class MlcxUrl {
	//客户号
	public static final String dm="8";
	//账号
	public static final String login="mlkc";
	//密码
	public static final String password="mlkc";
	
	//token url
	public static final String token_url="http://183.129.139.219:8012/api/queryToken";
	//档案接口
	public static final String data_url="http://183.129.139.219:8012/api/Cdz";
	 
	 //获取token json数据
	public static JSONObject getToken() throws Exception{
		JSONObject  object=new JSONObject();
		object.put("gn","queryToken");
		object.put("loginname", MlcxUrl.login);
		object.put("password",PartUtils.signMD5(MlcxUrl.password,"0"));
		return object;
	}
	 //新增/编辑充电桩数据
	public static JSONObject getChargingPile(String gn,String cdzbm,String jffabb,String lx,String token){
			JSONObject  object=new JSONObject();
			object.put("gn",gn);
			object.put("dm", MlcxUrl.dm);
			object.put("cdzbm",cdzbm);
			object.put("jffabb",jffabb);
			object.put("lx",lx);
			object.put("token",token);
		return object;
	}
	//删除充电桩
	public static JSONObject delChargingPile(String gn,String cdzbm,String token){
		JSONObject  object=new JSONObject();
		object.put("gn",gn);
		object.put("cdzbm",cdzbm);
		object.put("token",token);
	return object;
}
	 //新增充电枪数据
	public static JSONObject getChargingGun(String gn,String cdzbm,String qbh,String token){
			JSONObject  object=new JSONObject();
			object.put("gn",gn);
			object.put("cdzbm",cdzbm);
			object.put("qbh",qbh);
			object.put("token",token);
		return object;
	}
	//新增计费方案
	public static JSONObject getBillingScheme(BillingScheme billingScheme,String token){
		SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");
		JSONObject  object=new JSONObject();
		object.put("gn","addjffa");
		object.put("jffabb",billingScheme.getSchemeVersions());
		object.put("effective_date",sdf.format(billingScheme.getEffectiveDate()));
		object.put("invalid_date",sdf.format(billingScheme.getInvalidDate()));
		object.put("advance_frozen_money",billingScheme.getAdvanceFrozenMoney());
		object.put("min_frozen_money",billingScheme.getMinFrozenMoney());
		object.put("time_num","1");
		object.put("opint_price",billingScheme.getOpintPrice());
		object.put("peak_price",billingScheme.getPeakPrice());
		object.put("flat_price",billingScheme.getFlatPrice());
		object.put("valley_price",billingScheme.getValleyPrice());
		object.put("ordered_rate",billingScheme.getOrderedRate());
		object.put("tip",billingScheme.getServiceCharge());
		object.put("warming_price",billingScheme.getWarmingPrice());
		object.put("jffamx","00:00,24:00,1");
		object.put("token",token);
		return object;
	}
	//删除计费方案
	public static JSONObject delBillingScheme(BillingScheme billingScheme,String token){
		JSONObject  object=new JSONObject();
		object.put("gn","deletejffa");
		object.put("jffabb",billingScheme.getSchemeVersions());
		object.put("token",token);
		return object;
	}
	public static void main(String[] args) throws Exception {
		JSONObject object=MlPost.post(MlcxUrl.getToken(), MlcxUrl.token_url);
		
		System.out.println(object.get("jl").equals("success"));
	}
}
