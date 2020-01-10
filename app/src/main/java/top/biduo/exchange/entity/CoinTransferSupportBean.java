package top.biduo.exchange.entity;

public class CoinTransferSupportBean   {

    /**
     * id : 2
     * name : BTC
     * nameCn : BTC
     * unit : BTC
     * status : 0
     * jyRate : 0.001
     * sellMinAmount : 1
     * buyMinAmount : 1
     * sort : 0
     * isPlatformCoin : 0
     * coinScale : 8
     * maxTradingTime : 0
     * maxVolume : 0
     */

    private int id;
    private String name;
    private String nameCn;
    private String unit;
    private int status;
    private double jyRate;
    private double sellMinAmount;
    private double buyMinAmount;
    private int sort;
    private int isPlatformCoin;
    private int coinScale;
    private int maxTradingTime;
    private int maxVolume;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getJyRate() {
        return jyRate;
    }

    public void setJyRate(double jyRate) {
        this.jyRate = jyRate;
    }

    public double getSellMinAmount() {
        return sellMinAmount;
    }

    public void setSellMinAmount(double sellMinAmount) {
        this.sellMinAmount = sellMinAmount;
    }

    public double getBuyMinAmount() {
        return buyMinAmount;
    }

    public void setBuyMinAmount(double buyMinAmount) {
        this.buyMinAmount = buyMinAmount;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsPlatformCoin() {
        return isPlatformCoin;
    }

    public void setIsPlatformCoin(int isPlatformCoin) {
        this.isPlatformCoin = isPlatformCoin;
    }

    public int getCoinScale() {
        return coinScale;
    }

    public void setCoinScale(int coinScale) {
        this.coinScale = coinScale;
    }

    public int getMaxTradingTime() {
        return maxTradingTime;
    }

    public void setMaxTradingTime(int maxTradingTime) {
        this.maxTradingTime = maxTradingTime;
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

}
