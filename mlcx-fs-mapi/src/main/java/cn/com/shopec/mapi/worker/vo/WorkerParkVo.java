package cn.com.shopec.mapi.worker.vo;


import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerParkVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//场站经度
	private String longitude;
	//场站纬度
	private String latitude;
	//任务量
	private int workerOrderNum;
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public int getWorkerOrderNum() {
		return workerOrderNum;
	}
	public void setWorkerOrderNum(int workerOrderNum) {
		this.workerOrderNum = workerOrderNum;
	}
	@Override
	public String toString() {
		return "WorkerOrderParkVo [longitude=" + longitude + ", latitude=" + latitude + ", workerOrderNum="
				+ workerOrderNum + "]";
	}
	
	


}
