package cn.com.shopec.core.ml.model;

import cn.com.shopec.core.common.Entity;

/** 
 * CMlToken 数据实体类
 */
public class CMlToken extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//token id
	private String tokenId;
	//token
	private String token;
	//时间戳
	private String timestamp;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return tokenId;
	}
	
	public String getTokenId(){
		return tokenId;
	}
	
	public void setTokenId(String tokenId){
		this.tokenId = tokenId;
	}
	
	public String getToken(){
		return token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public String getTimestamp(){
		return timestamp;
	}
	
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CMlToken ["
		 + "tokenId = " + tokenId + ", token = " + token + ", timestamp = " + timestamp
		+"]";
	}
}
