package cn.com.shopec.common.utils;

import cn.com.shopec.common.component.MlToken;
import net.sf.json.JSONObject;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	//
	private static final String FMT_PARAM2URL = "%1$s?%2$s";
	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";
	/** boundary就是request头和上传文件内容的分隔符 */
	private static final String BOUNDARY = "---------------------------123821742118716";
	private static final byte[] ENDDATA = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
	//
	public static final String KEY_CONNECTION = "Connection";
	public static final String KEY_CONTENT_TYPE = "Content-Type";
	public static final String KEY_CONTENT_DISPOSITION = "Content-Disposition";
	public static final String VALUE_ALIVE = "Keep-Alive";
	public static final String VALUE_FORM_DATA = "form-data";
	public static final String VALUE_JSON = "application/json;";
	public static final String VALUE_FORMHEAD = "multipart/form-data; boundary=" + BOUNDARY;
	//
	/** 超时时间(请求/读取) */
	private static final int TIMEOUT = 20000;
	private HttpURLConnection conn;
	private static HttpUtils sInstance;
	//
	private boolean add_header = false;

	private HttpUtils(String path, Map<String, String> map, int timeout, boolean ssl)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		URL url = new URL(param2url(path, map));
//		if (ssl) SslUtils.ignoreSsl();
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// addHead(KEY_CONNECTION, VALUE_ALIVE);
	}

	public byte[] get() throws IOException {
		conn.setRequestMethod(METHOD_GET);
		if (!add_header) addDefaultHead();
		return getResult();
	}

	public byte[] post() throws IOException {
		conn.setRequestMethod(METHOD_POST);
		if (!add_header) addDefaultHead();
		return getResult();
	}

	public byte[] post(Map<String, String> map) throws IOException {
		conn.setRequestMethod(METHOD_POST);
		if (!add_header) addDefaultHead();
		if (EmptyUtils.isNotEmpty(map)) {
			OutputStream os = conn.getOutputStream();
			os.write(map2str(map).getBytes("UTF-8"));
			closeIOQuietly(os);
		}
		return getResult();
	}

	public byte[] post(byte[] datas) throws IOException {
		conn.setRequestMethod(METHOD_POST);
		if (!add_header) addDefaultHead();
		if (EmptyUtils.isNotEmpty(datas)) {
			OutputStream os = conn.getOutputStream();
			os.write(datas);
			closeIOQuietly(os);
		}
		return getResult();
	}

	public byte[] form(Map<String, String> files) throws IOException {
		conn.setRequestMethod(METHOD_POST);
		if (!add_header) addDefaultHead();
		addHead(KEY_CONTENT_TYPE, VALUE_FORMHEAD);
		if (EmptyUtils.isNotEmpty(files)) {
			OutputStream os = conn.getOutputStream();
			writeFileMap2from(files, os);
			os.write(ENDDATA);
			closeIOQuietly(os);
		}
		return getResult();
	}

	public byte[] form(Map<String, String> txt, Map<String, String> files) throws IOException {
		conn.setRequestMethod(METHOD_POST);
		if (!add_header) addDefaultHead();
		addHead(KEY_CONTENT_TYPE, VALUE_FORMHEAD);
		if (EmptyUtils.isNotEmpty(files) && EmptyUtils.isNotEmpty(txt)) {
			OutputStream os = conn.getOutputStream();
			writeTxtMap2from(txt, os);
			writeFileMap2from(files, os);
			os.write(ENDDATA);
			closeIOQuietly(os);
		}
		return getResult();
	}

	public byte[] other(String method, Map<String, String> map) throws IOException {
		conn.setRequestMethod(method);
		if (!add_header) addDefaultHead();
		if (EmptyUtils.isNotEmpty(map)) {
			OutputStream os = conn.getOutputStream();
			os.write(map2str(map).getBytes("UTF-8"));
			closeIOQuietly(os);
		}
		return getResult();
	}

	public HttpUtils addHead(String key, String value) {
		conn.setRequestProperty(key, value);
		if (!add_header) add_header = true;
		return sInstance;
	}

	public HttpUtils addHead(Map<String, String> map) {
		for (String key : map.keySet()) {
			conn.setRequestProperty(key, map.get(key));
		}
		if (!add_header) add_header = true;
		return sInstance;
	}

	public String bt2string(byte[] datas) throws IOException {
		InputStream is = StreamUtils.byte2InputStream(datas);
		return StreamUtils.streamToString(is);
	}

	public static HttpUtils getInstance(String url, Map<String, String> map, int timeout, boolean ssl)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		sInstance = new HttpUtils(url, map, timeout, ssl);
		return sInstance;
	}

	public static HttpUtils getInstance(String url, Map<String, String> map, int timeout)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, map, timeout, false);
	}

	public static HttpUtils getInstance(String url, Map<String, String> map, boolean ssl)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, map, TIMEOUT, ssl);
	}

	public static HttpUtils getInstance(String url, Map<String, String> map) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, map, false);
	}

	public static HttpUtils getInstance(String url, int timeout, boolean ssl) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, null, timeout, ssl);
	}

	public static HttpUtils getInstance(String url, int timeout) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, timeout, false);
	}

	public static HttpUtils getInstance(String url, boolean ssl) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, TIMEOUT, ssl);
	}

	public static HttpUtils getInstance(String url) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getInstance(url, false);
	}

	private byte[] getResult() {
		InputStream is = null;
		try {
			conn.connect();
			int rt = conn.getResponseCode();
			if (rt == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
				byte[] datas = StreamUtils.stream2Byte(is);
				return datas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeIOQuietly(is);
		}
		return null;
	}

	private void addDefaultHead() {
		conn.setRequestProperty(KEY_CONNECTION, VALUE_ALIVE);
		conn.setRequestProperty("Charset", "utf-8"); // 设置编码
		// conn.setRequestProperty("Content-Type", "multipart/form-data;
		// boundary=" + BOUNDARY);
		conn.setRequestProperty(KEY_CONTENT_TYPE, VALUE_JSON);
	}

	private String param2url(String url, Map<String, String> map) {
		String param = map2str(map);
		if (EmptyUtils.isEmpty(param)) { return url; }
		return String.format(FMT_PARAM2URL, url, map2str(map));
	}

	private String map2str(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (EmptyUtils.isNotEmpty(map)) for (Entry<String, String> entry : map.entrySet()) {
			try {
				sb.append("&");
				sb.append(URLEncoder.encode(entry.getKey(), "utf-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 不论是电脑还是手机访问网络时可能都有缓存,如果在地址后加个随机数
		// 则每次都是从服务器取最新的数据
		// sb.append(Math.random());
		return sb.length() > 1 ? sb.substring(1) : sb.toString();
	}

	private void writeFile(File file, OutputStream os) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			os.write(bufferOut, 0, bytes);
		}
		in.close();
	}

	@SuppressWarnings("rawtypes")
	private void writeTxtMap2from(Map<String, String> map, OutputStream os) throws IOException {
		if (map != null) {
			StringBuffer strBuf = new StringBuffer();
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String inputName = (String) entry.getKey();
				String inputValue = (String) entry.getValue();
				if (inputValue == null) {
					continue;
				}
				strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
				strBuf.append(inputValue);
			}
			os.write(strBuf.toString().getBytes());
		}
	}

	@SuppressWarnings("rawtypes")
	private void writeFileMap2from(Map<String, String> map, OutputStream os) throws IOException {
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			String inputName = (String) entry.getKey();
			String inputValue = (String) entry.getValue();
			if (inputValue == null) {
				continue;
			}
			File file = new File(inputValue);
			String filename = file.getName();
			String contentType = "";
			// String contentType = new MimetypesFileTypeMap()
			// .getContentType(file);
			if (filename.endsWith(".gif")) {
				contentType = "image/gif";
			}
			if (contentType == null || contentType.equals("")) {
				contentType = "application/octet-stream";
			}
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
			strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			os.write(strBuf.toString().getBytes());
			writeFile(file, os);
		}
	}

	private static void closeIOQuietly(final Closeable... closeables) {
		if (closeables == null) return;
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception ignored) {}
			}
		}
	}
}
