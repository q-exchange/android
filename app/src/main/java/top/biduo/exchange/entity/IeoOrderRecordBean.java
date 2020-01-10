package top.biduo.exchange.entity;

import java.io.Serializable;

public class IeoOrderRecordBean implements Serializable{


    /**
     * id : 4
     * userId : 2
     * userName : guos
     * userMobile : 15738776414
     * ieoName : 第一期
     * saleCoin : BTD
     * saleAmount : 10000
     * raiseCoin : USDT
     * ratio : 10
     * startTime : 2019-02-02 00:00:00
     * endTime : 2019-07-02 00:00:00
     * status : 1  状态 (0-失败，1-成功)
     * receiveAmount : 1000
     * payAmount : 100
     * expectTime : 2019-10-02 00:00:00
     * createTime : 2019-04-27 16:32:20
     */

    private int id;
    private int userId;
    private String userName;
    private String userMobile;
    private String ieoName;
    private String saleCoin;
    private double saleAmount;
    private String raiseCoin;
    private double ratio;
    private String startTime;
    private String endTime;
    private String status;
    private double receiveAmount;
    private double payAmount;
    private String expectTime;
    private String createTime;
    private String picView;

    public String getPicView() {
        return picView;
    }

    public void setPicView(String picView) {
        this.picView = picView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIeoName() {
        return ieoName;
    }

    public void setIeoName(String ieoName) {
        this.ieoName = ieoName;
    }

    public String getSaleCoin() {
        return saleCoin;
    }

    public void setSaleCoin(String saleCoin) {
        this.saleCoin = saleCoin;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getRaiseCoin() {
        return raiseCoin;
    }

    public void setRaiseCoin(String raiseCoin) {
        this.raiseCoin = raiseCoin;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
