package cn.com.shopec.common.exception;

/**
 * Created by guanfeng.li on 2016/7/6.
 * 自定义运行时异常
 */
public class BaseException extends RuntimeException {

    private static final long	serialVersionUID	= 42717324355691506L;

    private String errorCode  = "500"; //错误

    private String moduleName;//模块名

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String errorCode, String moduleName,String message) {
        super(message);
        this.errorCode = errorCode;
        this.moduleName = moduleName;
    }

    public BaseException(String errorCode, String moduleName,String message,Throwable cause) {
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

    //获取堆栈信息
    public String  getStackTraceInfo() {
        StackTraceElement[] stackTrace = this.getStackTrace();
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMessage()).append("\r\n");
        for (StackTraceElement ex : stackTrace) {
            sb.append(ex.getClassName()).append("(").append(ex.getLineNumber()).append(")").append("\r\n");
        }
       return sb.toString();
    }
}


