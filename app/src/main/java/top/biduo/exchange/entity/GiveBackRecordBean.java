package top.biduo.exchange.entity;

public class GiveBackRecordBean {


    /**
     * id : 267
     * memberId : 47
     * loanRecordId : 48
     * coin : {"name":"USDT","nameCn":"USDT","unit":"USDT","status":0,"minTxFee":0.01,"cnyRate":6.9,"maxTxFee":0.01,"usdRate":1,"sgdRate":0,"enableRpc":1,"sort":0,"canWithdraw":1,"canRecharge":1,"canTransfer":1,"canAutoWithdraw":1,"withdrawThreshold":100,"minWithdrawAmount":10,"maxWithdrawAmount":100000,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":4,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0}
     * leverCoin : {"id":1,"symbol":"ETH/USDT","coinSymbol":"ETH","baseSymbol":"USDT","enable":1,"sort":1,"proportion":5,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0}
     * paymentType : 3
     * status : 0
     * createTime : 2019-05-16 11:29:51
     * amount : 1010
     * principal : 1000
     * interest : 10
     * interestRate : null
     */

    private double id;
    private double memberId;
    private double loanRecordId;
    private CoinBean coin;
    private LeverCoinBean leverCoin;
    private double paymentType;
    private double status;
    private String createTime;
    private double amount;
    private double principal;
    private double interest;
    private double interestRate;

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getMemberId() {
        return memberId;
    }

    public void setMemberId(double memberId) {
        this.memberId = memberId;
    }

    public double getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(double loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public CoinBean getCoin() {
        return coin;
    }

    public void setCoin(CoinBean coin) {
        this.coin = coin;
    }

    public LeverCoinBean getLeverCoin() {
        return leverCoin;
    }

    public void setLeverCoin(LeverCoinBean leverCoin) {
        this.leverCoin = leverCoin;
    }

    public double getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(double paymentType) {
        this.paymentType = paymentType;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public static class CoinBean {
        /**
         * name : USDT
         * nameCn : USDT
         * unit : USDT
         * status : 0
         * minTxFee : 0.01
         * cnyRate : 6.9
         * maxTxFee : 0.01
         * usdRate : 1
         * sgdRate : 0
         * enableRpc : 1
         * sort : 0
         * canWithdraw : 1
         * canRecharge : 1
         * canTransfer : 1
         * canAutoWithdraw : 1
         * withdrawThreshold : 100
         * minWithdrawAmount : 10
         * maxWithdrawAmount : 100000
         * isPlatformCoin : 0
         * hasLegal : false
         * allBalance : null
         * coldWalletAddress : null
         * hotAllBalance : null
         * minerFee : 0
         * withdrawScale : 4
         * minRechargeAmount : 0
         * masterAddress : null
         * maxDailyWithdrawRate : 0
         */

        private String name;
        private String nameCn;
        private String unit;
        private double status;
        private double minTxFee;
        private double cnyRate;
        private double maxTxFee;
        private double usdRate;
        private double sgdRate;
        private double enableRpc;
        private double sort;
        private double canWithdraw;
        private double canRecharge;
        private double canTransfer;
        private double canAutoWithdraw;
        private double withdrawThreshold;
        private double minWithdrawAmount;
        private double maxWithdrawAmount;
        private double isPlatformCoin;
        private boolean hasLegal;
        private Object allBalance;
        private Object coldWalletAddress;
        private Object hotAllBalance;
        private double minerFee;
        private double withdrawScale;
        private double minRechargeAmount;
        private Object masterAddress;
        private double maxDailyWithdrawRate;

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

        public double getStatus() {
            return status;
        }

        public void setStatus(double status) {
            this.status = status;
        }

        public double getMinTxFee() {
            return minTxFee;
        }

        public void setMinTxFee(double minTxFee) {
            this.minTxFee = minTxFee;
        }

        public double getCnyRate() {
            return cnyRate;
        }

        public void setCnyRate(double cnyRate) {
            this.cnyRate = cnyRate;
        }

        public double getMaxTxFee() {
            return maxTxFee;
        }

        public void setMaxTxFee(double maxTxFee) {
            this.maxTxFee = maxTxFee;
        }

        public double getUsdRate() {
            return usdRate;
        }

        public void setUsdRate(double usdRate) {
            this.usdRate = usdRate;
        }

        public double getSgdRate() {
            return sgdRate;
        }

        public void setSgdRate(double sgdRate) {
            this.sgdRate = sgdRate;
        }

        public double getEnableRpc() {
            return enableRpc;
        }

        public void setEnableRpc(double enableRpc) {
            this.enableRpc = enableRpc;
        }

        public double getSort() {
            return sort;
        }

        public void setSort(double sort) {
            this.sort = sort;
        }

        public double getCanWithdraw() {
            return canWithdraw;
        }

        public void setCanWithdraw(double canWithdraw) {
            this.canWithdraw = canWithdraw;
        }

        public double getCanRecharge() {
            return canRecharge;
        }

        public void setCanRecharge(double canRecharge) {
            this.canRecharge = canRecharge;
        }

        public double getCanTransfer() {
            return canTransfer;
        }

        public void setCanTransfer(double canTransfer) {
            this.canTransfer = canTransfer;
        }

        public double getCanAutoWithdraw() {
            return canAutoWithdraw;
        }

        public void setCanAutoWithdraw(double canAutoWithdraw) {
            this.canAutoWithdraw = canAutoWithdraw;
        }

        public double getWithdrawThreshold() {
            return withdrawThreshold;
        }

        public void setWithdrawThreshold(double withdrawThreshold) {
            this.withdrawThreshold = withdrawThreshold;
        }

        public double getMinWithdrawAmount() {
            return minWithdrawAmount;
        }

        public void setMinWithdrawAmount(double minWithdrawAmount) {
            this.minWithdrawAmount = minWithdrawAmount;
        }

        public double getMaxWithdrawAmount() {
            return maxWithdrawAmount;
        }

        public void setMaxWithdrawAmount(double maxWithdrawAmount) {
            this.maxWithdrawAmount = maxWithdrawAmount;
        }

        public double getIsPlatformCoin() {
            return isPlatformCoin;
        }

        public void setIsPlatformCoin(double isPlatformCoin) {
            this.isPlatformCoin = isPlatformCoin;
        }

        public boolean isHasLegal() {
            return hasLegal;
        }

        public void setHasLegal(boolean hasLegal) {
            this.hasLegal = hasLegal;
        }

        public Object getAllBalance() {
            return allBalance;
        }

        public void setAllBalance(Object allBalance) {
            this.allBalance = allBalance;
        }

        public Object getColdWalletAddress() {
            return coldWalletAddress;
        }

        public void setColdWalletAddress(Object coldWalletAddress) {
            this.coldWalletAddress = coldWalletAddress;
        }

        public Object getHotAllBalance() {
            return hotAllBalance;
        }

        public void setHotAllBalance(Object hotAllBalance) {
            this.hotAllBalance = hotAllBalance;
        }

        public double getMinerFee() {
            return minerFee;
        }

        public void setMinerFee(double minerFee) {
            this.minerFee = minerFee;
        }

        public double getWithdrawScale() {
            return withdrawScale;
        }

        public void setWithdrawScale(double withdrawScale) {
            this.withdrawScale = withdrawScale;
        }

        public double getMinRechargeAmount() {
            return minRechargeAmount;
        }

        public void setMinRechargeAmount(double minRechargeAmount) {
            this.minRechargeAmount = minRechargeAmount;
        }

        public Object getMasterAddress() {
            return masterAddress;
        }

        public void setMasterAddress(Object masterAddress) {
            this.masterAddress = masterAddress;
        }

        public double getMaxDailyWithdrawRate() {
            return maxDailyWithdrawRate;
        }

        public void setMaxDailyWithdrawRate(double maxDailyWithdrawRate) {
            this.maxDailyWithdrawRate = maxDailyWithdrawRate;
        }
    }

    public static class LeverCoinBean {
        /**
         * id : 1
         * symbol : ETH/USDT
         * coinSymbol : ETH
         * baseSymbol : USDT
         * enable : 1
         * sort : 1
         * proportion : 5
         * interestRate : 0.01
         * minTurnIntoAmount : 0
         * minTurnOutAmount : 0
         */

        private double id;
        private String symbol;
        private String coinSymbol;
        private String baseSymbol;
        private double enable;
        private double sort;
        private double proportion;
        private double interestRate;
        private double minTurnIntoAmount;
        private double minTurnOutAmount;

        public double getId() {
            return id;
        }

        public void setId(double id) {
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

        public double getEnable() {
            return enable;
        }

        public void setEnable(double enable) {
            this.enable = enable;
        }

        public double getSort() {
            return sort;
        }

        public void setSort(double sort) {
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
}
