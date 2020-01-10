package top.biduo.exchange.entity;

import java.io.Serializable;

public class IeoBean implements Serializable{


    /**
     * id : 1
     * ieoName : 第一期
     * picView : ###
     * pic : ###
     * saleCoin : BTD
     * saleAmount : 10000
     * raiseCoin : USDT
     * ratio : 10
     * startTime : 2019-02-02 00:00:00
     * endTime : 2019-07-02 00:00:00
     * fee : 0
     * expectTime : 2019-10-02 00:00:00
     * successRatio : 0.6
     * limitAmount : 10
     * haveAmount : 1
     * haveCoin : USDT
     * surplusAmount : 9000
     * sellMode : 1111
     * sellDetail : 1111
     * createTime : 2019-04-27 14:49:32
     * createUser : null
     * updateTime : null
     * updateUser : null
     */

    private int id;
    private String ieoName;
    private String picView;
    private String pic;
    private String saleCoin;
    private double saleAmount;
    private String raiseCoin;
    private double ratio;
    private String startTime;
    private String endTime;
    private double fee;
    private String expectTime;
    private double successRatio;
    private double limitAmount;
    private double haveAmount;
    private String haveCoin;
    private double surplusAmount;
    private String sellMode;
    private String sellDetail;
    private String createTime;
    private Object createUser;
    private Object updateTime;
    private Object updateUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIeoName() {
        return ieoName;
    }

    public void setIeoName(String ieoName) {
        this.ieoName = ieoName;
    }

    public String getPicView() {
        return picView;
    }

    public void setPicView(String picView) {
        this.picView = picView;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public double getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(double successRatio) {
        this.successRatio = successRatio;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public double getHaveAmount() {
        return haveAmount;
    }

    public void setHaveAmount(double haveAmount) {
        this.haveAmount = haveAmount;
    }

    public String getHaveCoin() {
        return haveCoin;
    }

    public void setHaveCoin(String haveCoin) {
        this.haveCoin = haveCoin;
    }

    public double getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(double surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public String getSellMode() {
        return sellMode;
    }

    public void setSellMode(String sellMode) {
        this.sellMode = sellMode;
    }

    public String getSellDetail() {
        return sellDetail;
    }

    public void setSellDetail(String sellDetail) {
        this.sellDetail = sellDetail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Object updateUser) {
        this.updateUser = updateUser;
    }
}
