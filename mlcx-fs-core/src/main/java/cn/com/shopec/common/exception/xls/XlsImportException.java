package cn.com.shopec.common.exception.xls;

/**
 * 导入异常
 * @author zhanmin.zheng
 * @createDate 2016/06/30
 * @version 1.0
 */
@SuppressWarnings("serial")
public class XlsImportException extends Exception {
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public XlsImportException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
}
