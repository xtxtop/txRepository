/**
 * BussinessException.java 2009-11-11
 * 
 * 万程 版权所有(c) 2009 。
 */
package cn.com.shopec.common.exception;

/**
 * @Author xieyong
 * @ClassName EcException
 * @Date 2016-01-20
 * @Description:
 */
public class ECException extends Exception {

	private static final long	serialVersionUID	= 42717324355691506L;
	
	private String errorCode; //错误
	
	private String moduleName;//模块名
	
	public ECException(String message) {
		super(message);
	}
	
	public ECException(Throwable cause) {
		super(cause);
	}
	
	public ECException(String errorCode, String moduleName,String message) {
		super(message);
		this.errorCode = errorCode;
		this.moduleName = moduleName;
	}
	
	public ECException(String errorCode, String moduleName,String message,Throwable cause) {
		super(message,cause);
		this.errorCode = errorCode;
		this.moduleName = moduleName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
