package cn.com.shopec.core.device.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Device 数据实体类
 */
public class DeviceUploadFile extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 主键
	private String id;
	// 终端序列号
	private String deviceSn;
	// 文件路径
	private String filePath;
	// 文件类型
	private int fileType;
	// 创建日期
	private Date createTime;

	@Override
	public String getPK() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
