package cn.com.shopec.mgt.common.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Xss漏洞处理类
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	HttpServletRequest orgRequest = null;
	
	public XssHttpServletRequestWrapper(HttpServletRequest request){
		super(request);
		orgRequest = request;
	}
	
	/**
	 * 覆盖getParameter方法，讲参数名和参数值都做xss过滤。
	 * 如果需要获得原始的值，则通过supper.getParameterValues(name)来获取
	 * getParameterNames,getParameterValues,getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name){
		String value = super.getParameter(stripXSS(name));
		if(value != null){
			try{
				value = URLDecoder.decode(value,"utf-8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			value = filter(value);
			value = stripXSS(name);
		}
		return value;
	}
	

	@Override
	public String[] getParameterValues(String parameter){
		String [] values = super.getParameterValues(parameter);
		if(values == null){
			return null;
		}
		int count = values.length;
		String [] encodedValues = new String [count];
		for(int i = 0; i < count; i++){
			String v = values[i];
			//v = StringEscapeUtils.escapeSql(v);
			try{
				v = URLDecoder.decode(v.replaceAll("%","%25"),"utf-8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			v = filter(v);
			encodedValues[i] = stripXSS(v);
		}
		return encodedValues;
	}
	
	/**
	 * 覆盖getHeader方法，讲参数名和参数值都做xss过滤。
	 * 如果需要获得原始的值，则通过supper.getHeaders(name)来获取
	 * getHeaders也可能需要覆盖
	 */
	@Override
	public String getHeader(String name){
		String value = super.getHeader(stripXSS(name));
		if(value != null){			
			value = stripXSS(value);
		}
		return value;
	}
	
	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 */
	private static String xssEncode(String s){
		if(s == null || "".equals(s)){
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			switch (c){
				case '>':
					sb.append('＞');
					break;
				case '<':
					sb.append('＜');
					break;
				case '\'':
					sb.append('‘');
					break;
				case '\"':
					sb.append('“');
					break;
				case '&':
					sb.append('＆');
					break;
				case '\\':
					sb.append('＼');
					break;
				case '|':
					sb.append('｜');
					break;
				case '*':
					sb.append('ｘ');
					break;
				case '(':
					sb.append('（');
					break;
				case ')':
					sb.append('）');
					break;
				case '$':
					sb.append('￥');
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	/**
	 * 使用正则表达式过滤链接
	 */
	private static String stripXSS(String value){
		if(value != null ){
			value = value.replaceAll("", "");
			value = value.replaceAll("-->", "");
			value = value.replaceAll("<--", "");
			
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE 
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",Pattern.CASE_INSENSITIVE 
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("</script>",Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("javascript:",Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("vbscript:",Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("onload(.*?)=:",Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
		}		
		return value;
	}
	/**
	 * 获取最原始的request
	 */
	public HttpServletRequest getOrgRequest(){
		
		return orgRequest;
	}
	
	/**
	 * 获取最原始的request的静态方法
	 */
	public HttpServletRequest getOrgRequest(HttpServletRequest req){
		if(req instanceof XssHttpServletRequestWrapper){
			return ((XssHttpServletRequestWrapper) req).getOrgRequest();
		}
		return req;
	}
	
	/**
	 * 符号转义
	 */
	private static String filter(String value){
		if(value == null || "".equals(value)){
			return value;
		}	
		StringBuilder result = null;
		String filtered = null;
		for(int i = 0; i < value.length(); i++){
			filtered = null;
			char c = value.charAt(i);
			switch (c){
				case '<':
					filtered = "&lt;";
					break;
				case '>':
					filtered = "&gt;";
					break;
				case '&':
					filtered = "&amp;";
					break;
				case '"':
					filtered = "&quot;";
					break;
				case '\'':
					filtered = "&#39;";
					break;
			}
			if(result == null){
				if(filtered != null){
					result = new StringBuilder(value.length() + 50);
					if(i > 0){
						result.append(value.substring(0, i));
					}
					result.append(filtered);
				}				
			}else{
				if(filtered == null){			
					result.append(value.charAt(i));
				}else{
					result.append(filtered);
				}
			}
		}
		return result == null ? value : result.toString();
	}
}
