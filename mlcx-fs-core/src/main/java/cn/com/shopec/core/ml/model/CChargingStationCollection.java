package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * CChargingStationCollection 数据实体类
 */
public class CChargingStationCollection extends Entity<String> {

    private static final long serialVersionUID = 1l;

    /*Auto generated properties start*/

    private String collectionNo;
    //会员编号
    private String memberNo;
    //充电站编号
    private String stationNo;
    //收藏状态（1：收藏中，2：已取消）
    private Integer collectionStatus;
    private Date createTime;
    // 时间范围起（查询用）
    private Date createTimeStart;
    // 时间范围止（查询用）
    private Date createTimeEnd;
    private Date updateTime;
    // 时间范围起（查询用）
    private Date updateTimeStart;
    // 时间范围止（查询用）
    private Date updateTimeEnd;
    //操作人类型
    private Integer operatorType;
    //操作人id
    private String operatorId;

    /*Auto generated properties end*/



    /*Customized properties start*/

    /*Customized properties end*/



    /*Auto generated methods start*/

    @Override
    public String getPK(){
        return collectionNo;
    }

    public String getCollectionNo(){
        return collectionNo;
    }

    public void setCollectionNo(String collectionNo){
        this.collectionNo = collectionNo;
    }

    public String getMemberNo(){
        return memberNo;
    }

    public void setMemberNo(String memberNo){
        this.memberNo = memberNo;
    }

    public String getStationNo(){
        return stationNo;
    }

    public void setStationNo(String stationNo){
        this.stationNo = stationNo;
    }

    public Integer getCollectionStatus(){
        return collectionStatus;
    }

    public void setCollectionStatus(Integer collectionStatus){
        this.collectionStatus = collectionStatus;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Date getCreateTimeStart(){
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart){
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd(){
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd){
        this.createTimeEnd = createTimeEnd;
    }

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public Date getUpdateTimeStart(){
        return updateTimeStart;
    }

    public void setUpdateTimeStart(Date updateTimeStart){
        this.updateTimeStart = updateTimeStart;
    }

    public Date getUpdateTimeEnd(){
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Date updateTimeEnd){
        this.updateTimeEnd = updateTimeEnd;
    }

    public Integer getOperatorType(){
        return operatorType;
    }

    public void setOperatorType(Integer operatorType){
        this.operatorType = operatorType;
    }

    public String getOperatorId(){
        return operatorId;
    }

    public void setOperatorId(String operatorId){
        this.operatorId = operatorId;
    }


    /*Auto generated methods end*/



    /*Customized methods start*/

    /*Customized methods end*/


    @Override
    public String toString() {
        return "CChargingStationCollection ["
                + "collectionNo = " + collectionNo + ", memberNo = " + memberNo + ", stationNo = " + stationNo + ", collectionStatus = " + collectionStatus
                + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
                +"]";
    }
}
