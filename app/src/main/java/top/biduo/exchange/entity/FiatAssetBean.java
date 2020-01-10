package top.biduo.exchange.entity;

public class FiatAssetBean {


    /**
     * id : 10
     * balance : 0
     * frozenBalance : 0
     * releaseBalance : 0
     * isLock : 0
     * memberId : 47
     * version : 0
     * coin : {"name":"BTC","nameCn":"BTC","unit":"BTC","status":0,"minTxFee":1,"cnyRate":16000,"maxTxFee":1,"usdRate":4000,"sgdRate":0,"enableRpc":0,"sort":1,"canWithdraw":0,"canRecharge":0,"canTransfer":1,"canAutoWithdraw":0,"withdrawThreshold":1,"minWithdrawAmount":1,"maxWithdrawAmount":1,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":0,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0}
     */

    private int id;
    private double balance;
    private double frozenBalance;
    private double releaseBalance;
    private double isLock;
    private double memberId;
    private double version;
    private CoinBean coin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(double frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public double getReleaseBalance() {
        return releaseBalance;
    }

    public void setReleaseBalance(double releaseBalance) {
        this.releaseBalance = releaseBalance;
    }

    public double getIsLock() {
        return isLock;
    }

    public void setIsLock(double isLock) {
        this.isLock = isLock;
    }

    public double getMemberId() {
        return memberId;
    }

    public void setMemberId(double memberId) {
        this.memberId = memberId;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public CoinBean getCoin() {
        return coin;
    }

    public void setCoin(CoinBean coin) {
        this.coin = coin;
    }

    public static class CoinBean {
        /**
         * name : BTC
         * nameCn : BTC
         * unit : BTC
         * status : 0
         * minTxFee : 1
         * cnyRate : 16000
         * maxTxFee : 1
         * usdRate : 4000
         * sgdRate : 0
         * enableRpc : 0
         * sort : 1
         * canWithdraw : 0
         * canRecharge : 0
         * canTransfer : 1
         * canAutoWithdraw : 0
         * withdrawThreshold : 1
         * minWithdrawAmount : 1
         * maxWithdrawAmount : 1
         * isPlatformCoin : 0
         * hasLegal : false
         * allBalance : null
         * coldWalletAddress : null
         * hotAllBalance : null
         * minerFee : 0
         * withdrawScale : 0
         * minRechargeAmount : 0
         * masterAddress : null
         * maxDailyWithdrawRate : 0
         */

        private String name;
        private String nameCn;
        private String unit;
        private int status;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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
}
