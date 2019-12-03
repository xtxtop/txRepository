package cn.com.shopec.mapi.station.vo;

/**
 * 周围配套设施模型
 * 
 * @author Administrator
 *
 */
public class CMatchingVo {
	// 配套ID
	private String matchingId;
	// 配套名称
	private String matchingName;
	// 套餐图片url
	private String matchingPicUrl;

	public String getMatchingId() {
		return matchingId;
	}

	public void setMatchingId(String matchingId) {
		this.matchingId = matchingId;
	}

	public String getMatchingName() {
		return matchingName;
	}

	public void setMatchingName(String matchingName) {
		this.matchingName = matchingName;
	}

	public String getMatchingPicUrl() {
		return matchingPicUrl;
	}

	public void setMatchingPicUrl(String matchingPicUrl) {
		this.matchingPicUrl = matchingPicUrl;
	}

}
