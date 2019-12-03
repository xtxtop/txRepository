package cn.com.shopec.common;

/**
 * 返回结果
 * @author xieyong
 *
 */
public class ResultInfo<T extends Object> {
	
	private String code;//编码
	
	private String msg;//消息
	
	private T data;//数据
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
