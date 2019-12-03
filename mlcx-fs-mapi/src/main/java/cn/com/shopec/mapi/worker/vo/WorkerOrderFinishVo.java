package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderFinishVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//工单编号
	private String workerOrderNo;	
	//开始时间
	private Date startTime;
	//完成时间
	private Date finishTime;
	//调度工单状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
	private Integer workOrderStatus;
	//调度员id
	private String workerId;
	//调度员
	private String workerName;
	public String getWorkerOrderNo() {
		return workerOrderNo;
	}
	public void setWorkerOrderNo(String workerOrderNo) {
		this.workerOrderNo = workerOrderNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	@Override
	public String toString() {
		return "WorkerOrderFinishVo [workerOrderNo=" + workerOrderNo + ", startTime=" + startTime + ", finishTime="
				+ finishTime + ", workOrderStatus=" + workOrderStatus + "]";
	}
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	

}
