package cn.com.shopec.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class ECMd5Utils {
	
	/**
	 * 私有构造函数，不允许本类生成实例 
	 */
	private ECMd5Utils(){
		
	}
	
	private static final String sv = "Ix4c$S732nqix*cH";

	/**
	 * 用MD5算法进行加密
	 * @param pStrPW
	 * @return
	 */
	public static String crypt(String pStrPW) {
		String strCrypt = hash(pStrPW);
		if(strCrypt.length() > 0) {
			strCrypt += sv;
			strCrypt = hash(strCrypt);
		}
		
		return strCrypt;
	}
	/**
	 * 用MD5算法进行加密(16位)
	 * @param strPW
	 * @return
	 */
	public static String encryptMD5(String strPW) {
		String strCrypt = hash(strPW);
		if(strCrypt.length() > 0) {
			strCrypt += sv;
			strCrypt = hash(strCrypt);
		}
		
		return strCrypt.substring(8,24);
	}
	
	/**
	 * MD5算法进行散列
	 * @param str
	 * @return
	 */
	public static String hash(String str) {
		String result = "";
		if (str == null || str.equals("")) { // 如果传入参数为空，则返回空字符串
			return result;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] data = str.getBytes();
			int l = data.length;
			for (int i = 0; i < l; i++)
				md.update(data[i]);
			byte[] digest = md.digest();
			
			result = ECByteUtils.byteArrayToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(),e);
		}

		return result;		
	}
	
	
	/**
	 * MD5算法进行散列
	 * @param str
	 * @return
	 */
	public static String hash16(String str) {
		String result = "";
		if (str == null || str.equals("")) { // 如果传入参数为空，则返回空字符串
			return result;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] data = str.getBytes();
			int l = data.length;
			for (int i = 0; i < l; i++)
				md.update(data[i]);
			byte[] digest = md.digest();
			
			result = ECByteUtils.byteArrayToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(),e);
		}

		return result;		
	}

	public static void main(String args[]) {
		String pwd = "123456";
		if (args != null && args.length > 0) {
			pwd = args[0];
		}
		
		try {
			System.out.println(ECMd5Utils.hash(pwd));
			System.out.println(ECMd5Utils.crypt(pwd));
			System.out.println(ECMd5Utils.encryptMD5(pwd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    /**
     * 获取 md5 加密字符串
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String md5(String str) throws Exception {
        return getSign(str, "MD5");
    }

    /**
     * 获取加密签名
     *
     * @param str  字符
     * @param type 加密类型
     * @return
     * @throws Exception
     */
    public static String getSign(String str, String type) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance(type);
        crypt.reset();
        crypt.update(str.getBytes("UTF-8"));
        return str = byteToHex(crypt.digest());
    }

    /**
     * 字节转换 16 进制
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
   	 * 用MD5算法进行加密
   	 * @param pStrPW
   	 * @return
   	 */
   	public static String cryptTJFS(String pStrPW) {
   		String strCrypt = hash(pStrPW);
   		/*if(strCrypt.length() > 0) {
   			strCrypt += sv;
   			strCrypt = hash(strCrypt);
   		}
   		*/
   		return strCrypt;
   	}


}
