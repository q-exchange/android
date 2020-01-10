package top.biduo.exchange.entity;

public class FeeBean {


    /**
     * id : 1
     * gradeName : V1
     * gradeCode : V1
     * withdrawCoinAmount : 100
     * dayWithdrawCount : 2
     * exchangeFeeRate : 0.02
     * gradeBound : 1000
     */

    private String id;
    private String gradeName;
    private String gradeCode;
    private String withdrawCoinAmount;
    private String dayWithdrawCount;
    private double exchangeFeeRate;
    private String gradeBound;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getWithdrawCoinAmount() {
        return withdrawCoinAmount;
    }

    public void setWithdrawCoinAmount(String withdrawCoinAmount) {
        this.withdrawCoinAmount = withdrawCoinAmount;
    }

    public String getDayWithdrawCount() {
        return dayWithdrawCount;
    }

    public void setDayWithdrawCount(String dayWithdrawCount) {
        this.dayWithdrawCount = dayWithdrawCount;
    }

    public double getExchangeFeeRate() {
        return exchangeFeeRate;
    }

    public void setExchangeFeeRate(double exchangeFeeRate) {
        this.exchangeFeeRate = exchangeFeeRate;
    }

    public String getGradeBound() {
        return gradeBound;
    }

    public void setGradeBound(String gradeBound) {
        this.gradeBound = gradeBound;
    }
}
