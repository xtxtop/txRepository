package cn.com.shopec.common.component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.MlPost;
import cn.com.shopec.common.dsUtils.MlcxUrl;
import cn.com.shopec.core.ml.model.CMlToken;
import cn.com.shopec.core.ml.service.CMlTokenService;

@Component
public class MlToken {
	 public static MlToken t;  
	 @PostConstruct  
	    public void init() {      
	        t = this;  
	    }   
	@Resource
	private CMlTokenService mlTokenService;
	
	
	//t.chargingStationService.getChargingStation("CDZ1540364440233")
	//获取token
	public static String getToken(){
		return t.mlTokenService.getCMlToken("T0001").getToken();
	}
	//更新token
	public static ResultInfo<CMlToken> upToken() throws Exception{
		ResultInfo<CMlToken> result=new ResultInfo<CMlToken>();
		JSONObject object=MlPost.post(MlcxUrl.getToken(), MlcxUrl.token_url);
		CMlToken mt=new CMlToken();
		if("success".equals(object.get("jl"))){
			mt.setTokenId("T0001");
			mt.setToken(object.get("nr").toString());
			return t.mlTokenService.updateCMlToken(mt);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg(object.get("nr").toString());
			return result;
		}
	}
}
