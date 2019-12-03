package cn.com.shopec.common.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cn.com.shopec.delivery.common.DeliveryCommonImpl;

public class BinaryFileUtil implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3246886019594676931L;

	public static void main(String[] args) throws IOException {
		String conent = "542abc2915c6bc5cd4b2882282e601f077c57602832934647b39b2b60e2f7083eca24177fb7b66c6";
		dataOutputStream(conent);
		dataInputStream();
	}

	/**
	 * 生成二进制数据文件
	 * 
	 * @throws IOException
	 */
	public static void dataOutputStream(String content) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(getPath()+"dos.dat"));
		System.out.println(content);
		dos.writeBytes(content);
		dos.close();
	}

	/**
	 * 解析二进制数据文件
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String dataInputStream() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(getPath()+"dos.dat"));
		String resultStr = dis.readLine();
		dis.close();
		System.out.println(resultStr);
		String result = "";
		try {
			DesUtils des = new DesUtils("leemenz");// 解码密钥
			result = des.decrypt(resultStr);
			System.out.println("解密后的字符：" + des.decrypt(resultStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 解析二进制数据文件
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String dataInputStream(String filtPath) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(filtPath+"/dos.dat"));
		String resultStr = dis.readLine();
		dis.close();
		System.out.println(resultStr);
		String result = "";
		try {
			DesUtils des = new DesUtils("leemenz");// 解码密钥
			result = des.decrypt(resultStr);
//			System.out.println("解密后的字符：" + des.decrypt(resultStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getPath() throws UnsupportedEncodingException {
		String configPath = DeliveryCommonImpl.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "UTF-8");
	}
}