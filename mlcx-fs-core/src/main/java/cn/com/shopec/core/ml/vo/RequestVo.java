package cn.com.shopec.core.ml.vo;

import net.sf.json.JSONObject;

public class RequestVo {
	private String tp;
	private String msg;
	private JSONObject data;

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
}
