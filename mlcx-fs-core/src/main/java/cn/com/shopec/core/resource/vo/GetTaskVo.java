package cn.com.shopec.core.resource.vo;

public class GetTaskVo {
	// 调度员编号
	private String workerNo;
	// 任务状态：1-待处理 2-处理中 3-已完成 4-已取消
	private Integer taskStatus;
	// 任务类型：0-挪车 1-洗车 2-维修 3-保养 4-调度任务 5-巡检任务
	private Integer taskType;
	// 电量排序：1-由高到低 2-由低到高
	private Integer electricity;
	// 任务下发时间：1-由近到远 2-由远到近
	private Integer timeOrdering;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 删除标识，0、未删除，1、已删除，默认0
	private Integer isDeleted;
	// 查询条件 1:我发起的任务，2：我指派的任务
	private Integer isTask;

	public String getWorkerNo() {
		return workerNo;
	}

	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}

	public Integer getElectricity() {
		return electricity;
	}

	public void setElectricity(Integer electricity) {
		this.electricity = electricity;
	}

	public Integer getTimeOrdering() {
		return timeOrdering;
	}

	public void setTimeOrdering(Integer timeOrdering) {
		this.timeOrdering = timeOrdering;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

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

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getIsTask() {
		return isTask;
	}

	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}

}
