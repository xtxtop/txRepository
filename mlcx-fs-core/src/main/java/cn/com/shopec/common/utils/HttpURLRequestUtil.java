package cn.com.shopec.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.json.JSONObject;

public class HttpURLRequestUtil {
	/**
	 * post请求通用实现
	 * 
	 * @throws Exception
	 */
	public static String doMsgPost(String url, String message) throws IOException {
		URL localURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) localURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write(message);
		out.flush();
		out.close();

		String sCurrentLine = "";
		String sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}

		return sTotalString;
	}

	/**
	 * post请求通用实现
	 * 
	 * @throws Exception
	 */
	public static String doMsgPost(String url, String message, int connectionTimeout, int readTimeout)
			throws IOException {
		URL localURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) localURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setConnectTimeout(connectionTimeout);
		connection.setReadTimeout(readTimeout);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write(message);
		out.flush();
		out.close();

		String sCurrentLine = "";
		String sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}

		return sTotalString;
	}

	/**
	 * post请求通用实现（带文件上传）
	 * 
	 * @throws Exception
	 */
	public static String doMsgPost(String url, Map<String, String> textDataMap,
			Map<String, Map<String, byte[]>> fileInputDataMap) throws Exception {
		// if(fileInputDataMap == null || fileInputDataMap.isEmpty()) {
		// return doMsgPost(url, message);
		// }

		String boundary = "------------httpost123";
		URL localURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) localURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		DataOutputStream ds = new DataOutputStream(connection.getOutputStream());

		writeFileParam(ds, fileInputDataMap, boundary);
		writeTextParam(ds, textDataMap, boundary);
		paramsEnd(ds, boundary);

		// OutputStreamWriter out = new
		// OutputStreamWriter(connection.getOutputStream()/*, "GB2312"*/);
		//
		//
		// out.write(message);
		// out.flush();
		// out.close();

		ds.flush();
		ds.close();

		String sCurrentLine = "";
		String sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}

		connection.disconnect();
		return sTotalString;
	}

	/**
	 * post请求通用实现（带文件上传）
	 * 
	 * @throws Exception
	 */
	public static String doMsgPost(String url, Map<String, String> textDataMap,
			Map<String, Map<String, byte[]>> fileInputDataMap, int connectionTimeout, int readTimeout)
			throws Exception {
		// if(fileInputDataMap == null || fileInputDataMap.isEmpty()) {
		// return doMsgPost(url, message);
		// }

		String boundary = "------------httpost123";
		URL localURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) localURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		connection.setConnectTimeout(connectionTimeout);
		connection.setReadTimeout(readTimeout);
		DataOutputStream ds = new DataOutputStream(connection.getOutputStream());

		writeFileParam(ds, fileInputDataMap, boundary);
		writeTextParam(ds, textDataMap, boundary);
		paramsEnd(ds, boundary);

		// OutputStreamWriter out = new
		// OutputStreamWriter(connection.getOutputStream()/*, "GB2312"*/);
		//
		//
		// out.write(message);
		// out.flush();
		// out.close();

		ds.flush();
		ds.close();

		String sCurrentLine = "";
		String sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}

		connection.disconnect();
		return sTotalString;
	}

	/**
	 * 输出文件参数的数据
	 * 
	 * @param ds
	 * @param fileInputDataMap
	 * @param boundary
	 * @throws Exception
	 */
	private static void writeFileParam(DataOutputStream ds, Map<String, Map<String, byte[]>> fileInputDataMap,
			String boundary) throws Exception {
		if (fileInputDataMap == null || fileInputDataMap.isEmpty()) {
			return;
		}
		Set<String> inputNames = fileInputDataMap.keySet();
		for (String inputName : inputNames) {
			Map<String, byte[]> fileDataMap = fileInputDataMap.get(inputName);
			Set<String> fileNames = fileDataMap.keySet();
			for (String fileName : fileNames) {
				ds.writeBytes("--" + boundary + "\r\n");
				ds.writeBytes("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\""
						+ encode(fileName) + "\"\r\n");
				ds.writeBytes("Content-Type: " + getContentType(fileDataMap.get(fileName)) + "\r\n");
				ds.writeBytes("\r\n");
				ds.write(fileDataMap.get(fileName));
				ds.writeBytes("\r\n");

			}
		}
	}

	/**
	 * 输出文字参数的数据
	 * 
	 * @param ds
	 * @param textDataMap
	 * @param boundary
	 * @throws Exception
	 */
	private static void writeTextParam(DataOutputStream ds, Map<String, String> textDataMap, String boundary)
			throws Exception {
		if (textDataMap == null || textDataMap.isEmpty()) {
			return;
		}
		Set<String> keySet = textDataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String name = it.next();
			String value = textDataMap.get(name);
			ds.writeBytes("--" + boundary + "\r\n");
			ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
			ds.writeBytes("\r\n");
			ds.writeBytes(encode(value) + "\r\n");
		}
	}

	// 添加结尾数据
	private static void paramsEnd(DataOutputStream ds, String boundary) throws Exception {
		ds.writeBytes("--" + boundary + "--" + "\r\n");
		ds.writeBytes("\r\n");
	}

	// 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
	private static String encode(String value) throws Exception {
		return URLEncoder.encode(value, "UTF-8");
	}

	private static String getContentType(byte[] buf) throws Exception {

		// return "application/octet-stream"; //
		// 此行不再细分是否为图片，全部作为application/octet-stream 类型
		ImageInputStream imagein = ImageIO.createImageInputStream(buf);
		if (imagein == null) {
			return "application/octet-stream";
		}
		Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);
		if (!it.hasNext()) {
			imagein.close();
			return "application/octet-stream";
		}
		imagein.close();
		return "image/" + it.next().getFormatName().toLowerCase();// 将FormatName返回的值转换成小写，默认为大写

	}

	/**
	 * get请求通用实现
	 */
	public static String doMsgGet(String url) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		if (httpURLConnection.getResponseCode() >= 5000) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param url
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String url, String fileName, String savePath) throws Exception {
		FileOutputStream fos = null;
		InputStream inputStream = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(3 * 1000); // 设置超时间为3秒
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			inputStream = conn.getInputStream();
			byte[] getData = readInputStream(inputStream);
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	
	public static String doGet(String url, String charset, int timeout) {
		String result = "";
		try {
			URL u = new URL(url);
			try {
				URLConnection conn = u.openConnection();
				conn.connect();
				conn.setConnectTimeout(timeout);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				String line = "";
				while ((line = in.readLine()) != null) {

					result = result + line;
				}
				in.close();
			} catch (IOException e) {
				return result;
			}
		} catch (MalformedURLException e) {
			return result;
		}

		return result;
	}
	
	public static String doPost(String requrl, String param) {
		URL url;
		String sTotalString = "";
		try {
			url = new URL(requrl);
			URLConnection connection = url.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "text/xml");
			// connection.setRequestProperty("Content-Length",
			// body.getBytes().length+"");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");

			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(param); // 向页面传递数据。post的关键所在！
			out.flush();
			out.close();
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sCurrentLine;

			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			// 传说中的三层包装阿！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine + "\r\n";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(sTotalString);
		return sTotalString;
	}
	
	/**
    *
    * @param strUrl 请求地址
    * @
    * 
    */
   public static String net(String strUrl, String method) throws Exception {
	   URL localURL = new URL(strUrl);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		if (httpURLConnection.getResponseCode() >= 5000) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}

}


