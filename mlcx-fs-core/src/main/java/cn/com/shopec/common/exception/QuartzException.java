package cn.com.shopec.common.exception;


/**
 * 定时任务异常
 * @author zhiyuan.wu
 * @date 2016年7月4日
 * @version 1.0
 */
public class QuartzException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public QuartzException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
