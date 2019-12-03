package cn.com.shopec.common.utils.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRcodeImageCreater {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String url = "www.baidu.com?pid=520";
		generateQRCode(url, 10, "png", "f:", "qrcode", "qrcode");
		System.out.println("二维码生成完毕");
	}

	/**
	 * 生成二维码
	 * 
	 * @param url
	 *            二维码的url文本内容
	 * @param path
	 *            要存放二维码的路径
	 * @throws IOException
	 */
	public static String generateQRCode(String url, int size, String format, String rootName, String resImgPath,
			String fileName) throws IOException {
		Qrcode x = new Qrcode();
		x.setQrcodeErrorCorrect('Q');// 纠错等级（四种等级）可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
		x.setQrcodeEncodeMode('B');// N代表数字，A代表a-Z,B代表其他字符
		x.setQrcodeVersion(size);// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大

		int width = 67 + 12 * (size - 1);// 设置二维码的大小公式：67 + 12 * （version - 1）
		int height = 67 + 12 * (size - 1);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufferedImage.createGraphics();

		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, width, height);// 清除画板的内容

		int pixoff = 2;// 添加一个偏移量

		byte[] d = url.getBytes("utf-8");
		if (d.length > 0 && d.length < 120) {
			boolean[][] s = x.calQrcode(d);

			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j][i]) {
						gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
					}
				}
			}
		}
		gs.dispose();
		bufferedImage.flush();
		File outputFileDir = new File(rootName + "/" + resImgPath);
		if (!outputFileDir.exists()) {
			outputFileDir.mkdirs();
		}
		String resFilePathName = resImgPath + "/" + fileName + "." + format;
		File outputFile = new File(rootName + "/" + resImgPath + "/" + fileName + "." + format);
		ImageIO.write(bufferedImage, format, outputFile);
		return resFilePathName;
	}

}
