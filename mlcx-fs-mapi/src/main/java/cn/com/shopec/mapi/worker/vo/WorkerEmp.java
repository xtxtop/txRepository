package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 调度员表 数据实体类
 */
public class WorkerEmp extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//调度员编号
	private String workerNo;
	//工号
	private String empNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return workerNo;
	}
	
	public String getWorkerNo(){
		return workerNo;
	}
	
	public void setWorkerNo(String workerNo){
		this.workerNo = workerNo;
	}
	
	public String getEmpNo(){
		return empNo;
	}
	
	public void setEmpNo(String empNo){
		this.empNo = empNo;
	}

	@Override
	public String toString() {
		return "WorkerEmp [workerNo=" + workerNo + ", empNo=" + empNo + "]";
	}
	
	
}
