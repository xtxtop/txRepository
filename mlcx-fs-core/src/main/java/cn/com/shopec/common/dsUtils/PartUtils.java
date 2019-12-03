package cn.com.shopec.common.dsUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PartUtils {
	public static String sign(Map<String, String> map) throws Exception {
		String signPublic = "";
		if (!map.isEmpty()) {
			List<String> list = new ArrayList<String>();
			Set<String> params = map.keySet();
			for (String in : params) {
				list.add(in);
			}
			// 对字符串进行排序
			String[] paramKey = new String[list.size()];
			list.toArray(paramKey);
			Arrays.sort(paramKey);
			for (String ls : paramKey) {
				signPublic += ls + map.get(ls);
			}
		}
		return signPublic;
	}

	// 32位的的MD5加密,sign表示加密的字符串，flag表示需要转化的大小写1表示大写，0表示小写
	public static String signMD5(String sign, String flag) throws Exception {
		String result = "";
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((sign).getBytes("UTF-8"));
		String md1 = new BigInteger(1, md5.digest()).toString(16);
		if (ConstantCd.upCase.equals(flag)) {
			result = fillMD5(md1).toUpperCase();
		} else if (ConstantCd.smCase.equals(flag)) {
			result = fillMD5(md1).toUpperCase();
		}
		return result;
	}

	// 如果不足32位，用0递归去补足。
	public static String fillMD5(String md5) {
		return md5.length() == 32 ? md5 : fillMD5("0" + md5);
	}

}
