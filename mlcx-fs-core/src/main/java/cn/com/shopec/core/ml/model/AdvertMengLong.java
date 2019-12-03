package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Advert 广告学数据实体类
 */
public class AdvertMengLong extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 广告编号
	private String advertNo;
	// 广告类型（1、活动广告）
	private Integer advertType;
	// 广告名称
	private String advertName;
	// 排名（相同广告类型时的排名，数字小的优先，0为最小值）
	private Integer ranking;
	// 广告图片url
	private String advertPicUrl;
	// 跳转链接url
	private String linkUrl;
	// 内容
	private String text;
	// 可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
	// 可用状态更新时间
	private Date availableUpdateTime;
	// 可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	// 可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;
	// 审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer censorStatus;
	// 审核人id
	private String censorId;
	// 审核时间
	private Date censorTime;
	// 审核时间 时间范围起（查询用）
	private Date censorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date censorTimeEnd;
	// 审核备注
	private String censorMemo;
	// 操作人类型
	private Integer operatorType;
	// 是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 操作人id
	private String operatorId;
	// 操作人姓名
	private String operatorName;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 修改时间
	private Date updateTime;
	// 修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 修改时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 广告位置 1、首页顶部轮播图，2、中间广告，3、底部列表区域 4.首页活动广告
	private String advertPosition;
	// 链接
	private String filePath;
	// 跳转类型(0 外部跳转 1 内容跳转 2绑定车辆)
	private Integer linkType;
	// 车型id
	private String carModelId;
	// 车型名称
	private String carModelName;
	//广告大类型
	private Integer type;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String getPK() {
		return advertNo;
	}

	public String getAdvertNo() {
		return advertNo;
	}

	public void setAdvertNo(String advertNo) {
		this.advertNo = advertNo;
	}

	public Integer getAdvertType() {
		return advertType;
	}

	public void setAdvertType(Integer advertType) {
		this.advertType = advertType;
	}

	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getAdvertPicUrl() {
		return advertPicUrl;
	}

	public void setAdvertPicUrl(String advertPicUrl) {
		this.advertPicUrl = advertPicUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}

	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}

	public Date getAvailableUpdateTimeStart() {
		return availableUpdateTimeStart;
	}

	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart) {
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}

	public Date getAvailableUpdateTimeEnd() {
		return availableUpdateTimeEnd;
	}

	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd) {
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}

	public String getCensorId() {
		return censorId;
	}

	public void setCensorId(String censorId) {
		this.censorId = censorId;
	}

	public Date getCensorTime() {
		return censorTime;
	}

	public void setCensorTime(Date censorTime) {
		this.censorTime = censorTime;
	}

	public Date getCensorTimeStart() {
		return censorTimeStart;
	}

	public void setCensorTimeStart(Date censorTimeStart) {
		this.censorTimeStart = censorTimeStart;
	}

	public Date getCensorTimeEnd() {
		return censorTimeEnd;
	}

	public void setCensorTimeEnd(Date censorTimeEnd) {
		this.censorTimeEnd = censorTimeEnd;
	}

	public String getCensorMemo() {
		return censorMemo;
	}

	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(String advertPosition) {
		this.advertPosition = advertPosition;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "AdvertMengLong [advertNo=" + advertNo + ", advertType="
				+ advertType + ", advertName=" + advertName + ", ranking="
				+ ranking + ", advertPicUrl=" + advertPicUrl + ", linkUrl="
				+ linkUrl + ", text=" + text + ", isAvailable=" + isAvailable
				+ ", availableUpdateTime=" + availableUpdateTime
				+ ", availableUpdateTimeStart=" + availableUpdateTimeStart
				+ ", availableUpdateTimeEnd=" + availableUpdateTimeEnd
				+ ", censorStatus=" + censorStatus + ", censorId=" + censorId
				+ ", censorTime=" + censorTime + ", censorTimeStart="
				+ censorTimeStart + ", censorTimeEnd=" + censorTimeEnd
				+ ", censorMemo=" + censorMemo + ", operatorType="
				+ operatorType + ", isDeleted=" + isDeleted + ", operatorId="
				+ operatorId + ", operatorName=" + operatorName
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", advertPosition=" + advertPosition + ", filePath="
				+ filePath + ", linkType=" + linkType + ", carModelId="
				+ carModelId + ", carModelName=" + carModelName + ", type="
				+ type + "]";
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
}
