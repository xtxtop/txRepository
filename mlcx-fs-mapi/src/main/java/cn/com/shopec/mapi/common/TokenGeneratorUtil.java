package cn.com.shopec.mapi.common;

import cn.com.shopec.common.utils.ECUuidGenUtils;

public class TokenGeneratorUtil {
	
	public static String tokenGenerator(){
		return ECUuidGenUtils.genUUID();
	}
	public static void main(String[] args) {
		System.out.println(TokenGeneratorUtil.tokenGenerator());;
	}
}
