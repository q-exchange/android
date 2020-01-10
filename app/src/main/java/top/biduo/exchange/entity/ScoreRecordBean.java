package top.biduo.exchange.entity;

public class ScoreRecordBean {


    /**
     * id : 3
     * memberId : 8
     * type : 0
     * amount : 10
     * createTime : 2019-04-29 14:09:47
     */

    private int id;
    private int memberId;
    private int type;
    private int amount;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    String[] types={"推广","法币充值赠送","币币充值赠送"};
    public String getStringByType(){
        return types[type];
    }


}
