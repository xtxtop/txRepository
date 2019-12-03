package cn.com.shopec.mapi.station.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.CLabelVo;
import cn.com.shopec.mapi.homepage.vo.AdvertTopVo;

/**
 * 充电站静态资源模型
 * 
 * @author Administrator
 *
 */
public class ChargingStationVoResource {
	// 广告集合
	private List<AdvertTopVo> advertList;
	// 系统参数（0-1成超低首付，厂商直供全国联保，15分钟快速获贷）
	private List<AdvertTextVo> advertTextVoList;
	// 标签集合
	private List<CLabelVo> labelList;
	// 综合排序
	// private List<Subsume> subsumeList;
	// 类型排序
	// private List<TypeSort> typeSort;

	public List<AdvertTopVo> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<AdvertTopVo> advertList) {
		this.advertList = advertList;
	}

	public List<CLabelVo> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<CLabelVo> labelList) {
		this.labelList = labelList;
	}

	public List<AdvertTextVo> getAdvertTextVoList() {
		return advertTextVoList;
	}

	public void setAdvertTextVoList(List<AdvertTextVo> advertTextVoList) {
		this.advertTextVoList = advertTextVoList;
	}

}