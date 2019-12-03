package cn.com.shopec.common.pay.wxPay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;

import cn.com.shopec.common.utils.HttpURLRequestUtil;
import freemarker.core.ParseException;




@SuppressWarnings("deprecation")
public class GetWxOrderno {
	public GetWxOrderno(){}
	private static final class GetWxOrdernoSingal{
		private static final GetWxOrderno getWxOrdernoSingal = new GetWxOrderno();
	}
	public static GetWxOrderno getInstance(){
		return GetWxOrdernoSingal.getWxOrdernoSingal;
	}
	    /**
	     * 向HTTPS地址发送POST请求
	     * @param reqURL 请求地址
	     * @param params 请求参数
	     * @return 响应内容
	     */ 
	    @SuppressWarnings("finally") 
	    public static String sendSSLPostRequest(String reqURL,String xmlParam){ 
	        long responseLength = 0;                         //响应长度 
	        String responseContent = null;                   //响应内容 
	        @SuppressWarnings("resource")
			HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例 
	        X509TrustManager xtm = new X509TrustManager(){   //创建TrustManager 
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {} 
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {} 
	            public X509Certificate[] getAcceptedIssuers() { return null; } 
	        }; 
	        try { 
	            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
	            SSLContext ctx = SSLContext.getInstance("TLS"); 
	             
	            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用 
	            ctx.init(null, new TrustManager[]{xtm}, null); 
	             
	            //创建SSLSocketFactory 
	            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx); 
	             
	            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上 
	            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory)); 
	             
	            HttpPost httpPost = new HttpPost(reqURL);                        //创建HttpPost 
//	            List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数 
//	            for(Map.Entry<String,String> entry : params.entrySet()){ 
//	                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
//	            } 
	           // httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); 
	            httpPost.setEntity(new StringEntity(xmlParam, "UTF-8"));
	            HttpResponse response = httpClient.execute(httpPost); //执行POST请求 
	            HttpEntity entity = response.getEntity();             //获取响应实体 
	             
	            if (null != entity) { 
	                responseLength = entity.getContentLength(); 
	                responseContent = EntityUtils.toString(entity, "UTF-8"); 
	                EntityUtils.consume(entity); //Consume response content 
	            } 
	            System.out.println("请求地址: " + httpPost.getURI()); 
	            System.out.println("响应状态: " + response.getStatusLine()); 
	            System.out.println("响应长度: " + responseLength); 
	            System.out.println("响应内容: " + responseContent); 
	        } catch (KeyManagementException e) { 
	            e.printStackTrace(); 
	        } catch (NoSuchAlgorithmException e) { 
	            e.printStackTrace(); 
	        } catch (UnsupportedEncodingException e) { 
	            e.printStackTrace(); 
	        } catch (ClientProtocolException e) { 
	            e.printStackTrace(); 
	        } catch (ParseException e) { 
	            e.printStackTrace(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } finally { 
	            httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源 
	            return responseContent; 
	        } 
	    } 
	public static String getPayNo1(String url, String xmlParam) {
		DefaultHttpClient httpclient;
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			
			return jsonStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
		
	}
	public  String getPayNo(String url, String xmlParam) throws Exception {
		HttpClient httpClient = null;

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(getPath() + WxpayConfig.getCertLocalPath()));//加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, WxpayConfig.getCertPassword().toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WxpayConfig.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        
//		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        HttpPost httpost = new HttpPost(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpClient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			
			if (jsonStr.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			prepay_id = (String) doXMLParse(jsonStr).get("prepay_id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			httpost.abort();
		}
		return prepay_id;
	}
//查询订单
	@SuppressWarnings("resource")
	public static String getOrderSearch(String url, String xmlParam) {
		DefaultHttpClient httpclient;
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			//Map map = doXMLParse(jsonStr);
			//String state=(String)map.get("trade_state");
			int a=jsonStr.indexOf("<trade_state><![CDATA[");
			int b=jsonStr.indexOf("]]></trade_state>");
			String status=jsonStr.substring(a+22,b);
			return status;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查询退款订单详情
	@SuppressWarnings("resource")
	public static String getOrderRefundSearch(String url, String xmlParam) {
		DefaultHttpClient httpclient;
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			Map map = doXMLParse(jsonStr);
			String state=(String)map.get("refund_status_0");
			System.out.println(state);
			return jsonStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	public static String getPath() throws UnsupportedEncodingException {
		String configPath = GetWxOrderno.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}
	public String getPayNoByPost(String createOrderURL, SortedMap<String, String> packageParams){
		String result="";
		String prepay_id="";
		try {
			result = HttpURLRequestUtil.doMsgPost(createOrderURL, packageParams, null);
			if(result!=null&&!result.equals("")){
				prepay_id=(String) doXMLParse(result).get("prepay_id");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prepay_id;
	}
}