package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//工单编号
	private String workerOrderNo;
	//下发时间
	private String sendTime;
	//备注
	private String memo;
	//任务地点
	private String workerAddress;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return workerOrderNo;
	}
	
	public String getWorkerOrderNo(){
		return workerOrderNo;
	}
	
	public void setWorkerOrderNo(String workerOrderNo){
		this.workerOrderNo = workerOrderNo;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getWorkerAddress() {
		return workerAddress;
	}

	public void setWorkerAddress(String workerAddress) {
		this.workerAddress = workerAddress;
	}

	@Override
	public String toString() {
		return "WorkerOrderVo [workerOrderNo=" + workerOrderNo + ", sendTime=" + sendTime + ", memo=" + memo
				+ ", workerAddress=" + workerAddress + "]";
	}
	

}
