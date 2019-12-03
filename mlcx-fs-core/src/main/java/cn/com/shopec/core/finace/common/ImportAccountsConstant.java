package cn.com.shopec.core.finace.common;

public class ImportAccountsConstant {
	
	public static final String fail_code = "0";
	public static final String success_code = "1";
	
	/**账单已存在，无发导入错误*/
	public static final String fail_date_is_existence = "该日账单数据已存在!";
	/**日期参数错误*/
	public static final String fail_date_parameter = "日期参数错误!";
	
	/**支付宝接口调用成功*/
	public static final String success_alipay = "支付宝接口调用失败!";
	/**支付宝接口调用错误*/
	public static final String fail_alipay = "支付宝接口调用失败!";
	/**支付宝账单文件下载错误*/
	public static final String fail_alipay_download = "支付宝业务账单文件下载失败!";
	/**解析支付宝下载的业务文件错误*/
	public static final String fail_alipay_decompression = "解析支付宝业务账单文件失败!";
	/**支付宝返回数据解析后数据为空*/
	public static final String fail_alipay_data_empty =  "该日无支付宝账单数据！";
	
	/**微信接口调用成功*/
	public static final String success_wechart = "微信接口调用成功!";
	/**微信接口调用错误*/
	public static final String fail_wechart =  "微信接口调用失败！";
	/**微信返回数据解析后数据为空*/
	public static final String fail_wechart_data_empty =  "该日无微信账单数据！";
}
