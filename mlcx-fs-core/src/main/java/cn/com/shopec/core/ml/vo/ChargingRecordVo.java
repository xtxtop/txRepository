package cn.com.shopec.core.ml.vo;

import java.util.Date;

public class ChargingRecordVo {
    private String recordNo;
    private String chargingPileName;
    private String chargingGunNo;
    //记录时间
    private Date recordTime;
    //交易流水号
    private String serialNumber;
    //应用卡号
    private String cardNumber;
    //创建日期
    private Date createTime;
    //开始时间
    private Date startTime;
    //结束时间
    private Date finishTime;


    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getChargingPileName() {
        return chargingPileName;
    }

    public void setChargingPileName(String chargingPileName) {
        this.chargingPileName = chargingPileName;
    }

    public String getChargingGunNo() {
        return chargingGunNo;
    }

    public void setChargingGunNo(String chargingGunNo) {
        this.chargingGunNo = chargingGunNo;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }



 }
