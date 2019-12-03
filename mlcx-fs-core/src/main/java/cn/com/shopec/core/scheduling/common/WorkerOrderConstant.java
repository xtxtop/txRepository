package cn.com.shopec.core.scheduling.common;

public class WorkerOrderConstant {
	
	public static final String fail_code = "0";
	public static final String success_code = "1";
	
	/**系统错误*/
	public static final String fail_msg = "系统错误";
	
	
	/**当前车辆在调度员的任务中*/
	public static final String IS_IN_WORKER_ORDER_MSG = "当前车辆在调度员的任务中!";
	/**当前车辆不在调度员的任务中*/
	public static final String ISNOT_IN_WORKER_ORDER_MSG = "当前车辆不在调度员的任务中!";
	
	//调度工单 的待审核的数据
	public static final String NO_CENSORSTATUS_WORKER_ORDER = "NO_CENSORSTATUS_WORKER_ORDER";
	
}
