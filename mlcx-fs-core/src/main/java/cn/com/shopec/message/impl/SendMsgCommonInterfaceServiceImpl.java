package cn.com.shopec.message.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.delivery.common.DeliveryCommonImpl;


public abstract class SendMsgCommonInterfaceServiceImpl implements SendMsgCommonInterfaceService {
	@Override
	public String doPost(String urlStr, String param) throws IOException {

		URL url;
		String sTotalString = "";
		try {
			url = new URL(urlStr);
			URLConnection connection = url.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");

			connection.setDoOutput(true);
			connection.setDoInput(true);
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
	@Override
	public String doGet(String urlStr, String param) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(urlStr + "?" + param);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();

			String sCurrentLine = "";
			InputStream l_urlStream = conn.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				buffer.append(sCurrentLine);
			}
			l_reader.close();
			l_urlStream.close();
			System.err.println(buffer.toString());
			return buffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getPath() throws UnsupportedEncodingException {
		String configPath = DeliveryCommonImpl.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "UTF-8");
	}
	@Override
	public String getString(String key) {
		InputStream in = null;
		try {
			Properties props = new Properties();
			in = new BufferedInputStream(new FileInputStream(
					(getPath() + "message.properties")));
			props.load(in);
			String value = new String(props.getProperty(key).getBytes("ISO-8859-1"),"UTF-8");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != in){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	@Override
	public Map<String, String> getPropertiesParams() {
		InputStream in = null;
		Map<String, String> params = new HashMap<>();
		try {
			Properties props = new Properties();
			in = new BufferedInputStream(new FileInputStream((getPath() + "message.properties")));
			props.load(in);
			Iterator<String> it = props.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				params.put(key, props.getProperty(key));
				System.out.println(key + ":" + props.getProperty(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return params;
	}
	//将map型转为请求参数型
    public String convertMapParamToStrParams(Map<String,String> params) {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            try {
                sb.append(key).append("=").append(URLEncoder.encode(params.get(key)+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
