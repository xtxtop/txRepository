package cn.com.shopec.core.ml.vo;

public class CLabelVo {

	// 标签ID
	private String labelId;
	// 标签名称
	private String labelName;

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public CLabelVo(String labelId, String labelName) {
		super();
		this.labelId = labelId;
		this.labelName = labelName;
	}

	public CLabelVo() {
		super();
	}

}
