package cn.com.shopec.common.dsUtils;

public class OutputInfo {
	// 返回状态
	private String Success;
	// 提示信息
	private String Msg;
	// 结果集
	private Object Data;

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}
}
