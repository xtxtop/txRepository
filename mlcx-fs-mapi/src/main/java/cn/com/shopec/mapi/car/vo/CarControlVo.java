package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CarControlVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -369828071333291640L;
	
		//开车门
		private String unLockCarDoor;
		//关车门
		private String lockCarDoor;
		//找车
		private String findCar;
		//开车门距离
        private String unLockCarDoorDistance;
      //电话号码(有可能 是连接地址)
        private String phone;
		//限制距离划线
        private Double range;
        //是否分享
        private  Integer is_SHARE;
      
        //客服热线
        private String telePhone;
        //启动页 图片
        private String startPage;
        //是否加密
        private String  isDecrypt; 
        //app注册是否需要优惠
        private Integer isFavourable;
      //外部链接url
    	private String externalLinkUrl;
    	//广告名称
    	private String advertName;
    	//是否显示座位数的筛选
    	private Integer isSeaTing;
    	//座位数集合
    	private List<SeaTingVo> seaTingList;
    	//取还车是否需要车辆拍照
    	private String isCarPhoto;
    	//设置所有车辆能否在场站外还车
    	private String returnCarStatus;
    	
        
		public Integer getIsSeaTing() {
			return isSeaTing;
		}
		public void setIsSeaTing(Integer isSeaTing) {
			this.isSeaTing = isSeaTing;
		}
		public String getUnLockCarDoor() {
			return unLockCarDoor;
		}
		public void setUnLockCarDoor(String unLockCarDoor) {
			this.unLockCarDoor = unLockCarDoor;
		}
		
		public String getLockCarDoor() {
			return lockCarDoor;
		}
		public void setLockCarDoor(String lockCarDoor) {
			this.lockCarDoor = lockCarDoor;
		}
		public String getFindCar() {
			return findCar;
		}
		public void setFindCar(String findCar) {
			this.findCar = findCar;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		public String getUnLockCarDoorDistance() {
			return unLockCarDoorDistance;
		}
		public void setUnLockCarDoorDistance(String unLockCarDoorDistance) {
			this.unLockCarDoorDistance = unLockCarDoorDistance;
		}
		
		@Override
		public String toString() {
			return "CarControlVo [unLockCarDoor=" + unLockCarDoor + ", lockCarDoor=" + lockCarDoor + ", findCar="
					+ findCar + ", unLockCarDoorDistance=" + unLockCarDoorDistance + "]";
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Double getRange() {
			return range;
		}
		public void setRange(Double range) {
			this.range = range;
		}
		public Integer getIs_SHARE() {
			return is_SHARE;
		}
		public void setIs_SHARE(Integer is_SHARE) {
			this.is_SHARE = is_SHARE;
		}
		public String getTelePhone() {
			return telePhone;
		}
		public void setTelePhone(String telePhone) {
			this.telePhone = telePhone;
		}
		public String getStartPage() {
			return startPage;
		}
		public void setStartPage(String startPage) {
			this.startPage = startPage;
		}
		public String getIsDecrypt() {
			return isDecrypt;
		}
		public void setIsDecrypt(String isDecrypt) {
			this.isDecrypt = isDecrypt;
		}
		public Integer getIsFavourable() {
			return isFavourable;
		}
		public void setIsFavourable(Integer isFavourable) {
			this.isFavourable = isFavourable;
		}
		public String getExternalLinkUrl() {
			return externalLinkUrl;
		}
		public void setExternalLinkUrl(String externalLinkUrl) {
			this.externalLinkUrl = externalLinkUrl;
		}
		public String getAdvertName() {
			return advertName;
		}
		public void setAdvertName(String advertName) {
			this.advertName = advertName;
		}
		public List<SeaTingVo> getSeaTingList() {
			return seaTingList;
		}
		public void setSeaTingList(List<SeaTingVo> seaTingList) {
			this.seaTingList = seaTingList;
		}
		public String getIsCarPhoto() {
			return isCarPhoto;
		}
		public void setIsCarPhoto(String isCarPhoto) {
			this.isCarPhoto = isCarPhoto;
		}
		public String getReturnCarStatus() {
			return returnCarStatus;
		}
		public void setReturnCarStatus(String returnCarStatus) {
			this.returnCarStatus = returnCarStatus;
		}
		
	
		
		
		
		
}
