package top.biduo.exchange.entity;

public class CandyRecordBean {


    /**
     * id : 28
     * userId : 8
     * userName : null
     * userMobile : null
     * giftName : 五一大放送
     * giftCoin : LTB
     * giftAmount : 1690
     * createTime : 2019-04-29 20:47:38
     */

    private int id;
    private int userId;
    private String userName;
    private String userMobile;
    private String giftName;
    private String giftCoin;
    private String giftAmount;
    private String createTime;

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

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftCoin() {
        return giftCoin;
    }

    public void setGiftCoin(String giftCoin) {
        this.giftCoin = giftCoin;
    }

    public String getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(String giftAmount) {
        this.giftAmount = giftAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
