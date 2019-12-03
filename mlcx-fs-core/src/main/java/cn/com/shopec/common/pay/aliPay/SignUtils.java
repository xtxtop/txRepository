package cn.com.shopec.common.pay.aliPay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUtils {

	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = AlipayConfig.rsa_private;
	//支付宝公钥
	public static final String RSA_PUBLIC=AlipayConfig.rsa_public;

//	/**
//	 * 签名方法
//	 * @param orderInfo  订单信息
//	 * @return 将订单进行RSA签名
//     */
//	public static String sign(String orderInfo) {
//		try {
//			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
//					Base64.decode(RSA_PRIVATE));
//			KeyFactory keyf = KeyFactory.getInstance("RSA");
//			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
//			java.security.Signature signature = java.security.Signature
//					.getInstance("SHA1WithRSA");
//			signature.initSign(priKey);
//			signature.update(orderInfo.getBytes("UTF-8"));
//			byte[] signed = signature.sign();
//			return Base64.encode(signed);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	public static String sign(String content) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(RSA_PRIVATE));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
     * get the sign type we use. 获取签名方式
     *
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }
    /**
     * <p>
     * 校验数字签名
     * </p>
     * 
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * 
     * @return
     * @throws Exception
     * 
     */
    public static boolean verify(String param,String sign)
            throws Exception {
        byte[] keyBytes = Base64.decode(RSA_PUBLIC);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicK);
        signature.update(param.getBytes("UTF-8"));
        return signature.verify(Base64.decode(sign));
    }
}
