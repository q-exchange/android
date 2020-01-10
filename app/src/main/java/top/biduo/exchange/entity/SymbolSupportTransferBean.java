package top.biduo.exchange.entity;

public class SymbolSupportTransferBean {

    /**
     * id : 1
     * symbol : ETH/USDT
     * coinSymbol : ETH
     * baseSymbol : USDT
     * enable : 1
     * sort : 1
     * proportion : 4.0
     * interestRate : 2.0
     * minTurnIntoAmount : 0.0
     * minTurnOutAmount : 0.0
     */

    private int id;
    private String symbol;
    private String coinSymbol;
    private String baseSymbol;
    private int enable;
    private int sort;
    private double proportion;
    private double interestRate;
    private double minTurnIntoAmount;
    private double minTurnOutAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public String getBaseSymbol() {
        return baseSymbol;
    }

    public void setBaseSymbol(String baseSymbol) {
        this.baseSymbol = baseSymbol;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinTurnIntoAmount() {
        return minTurnIntoAmount;
    }

    public void setMinTurnIntoAmount(double minTurnIntoAmount) {
        this.minTurnIntoAmount = minTurnIntoAmount;
    }

    public double getMinTurnOutAmount() {
        return minTurnOutAmount;
    }

    public void setMinTurnOutAmount(double minTurnOutAmount) {
        this.minTurnOutAmount = minTurnOutAmount;
    }

}
