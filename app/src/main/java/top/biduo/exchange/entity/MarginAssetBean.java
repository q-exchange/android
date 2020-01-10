package top.biduo.exchange.entity;

import java.io.Serializable;
import java.util.List;

public class MarginAssetBean implements Serializable{


    /**
     * memberId : 47
     * symbol : ETH/USDT
     * riskRate : 0
     * baseCanLoan : 0
     * coinCanLoan : 0
     * baseLoanCount : 0
     * coinLoanCount : 0
     * baseAccumulativeCount : 0
     * coinAccumulativeCount : 0
     * leverWalletList : [{"id":5,"memberId":47,"memberName":"明哥123","mobilePhone":"15838008016","email":null,"leverCoin":{"id":1,"symbol":"ETH/USDT","coinSymbol":"ETH","baseSymbol":"USDT","enable":1,"sort":1,"proportion":5,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0},"coin":{"name":"USDT","nameCn":"USDT","unit":"USDT","status":0,"minTxFee":0.01,"cnyRate":6.71,"maxTxFee":0.01,"usdRate":1,"sgdRate":0,"enableRpc":1,"sort":0,"canWithdraw":1,"canRecharge":1,"canTransfer":1,"canAutoWithdraw":1,"withdrawThreshold":100,"minWithdrawAmount":10,"maxWithdrawAmount":1,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":4,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0},"balance":0,"frozenBalance":0,"isLock":0,"status":0,"foldBtc":0},{"id":6,"memberId":47,"memberName":"明哥123","mobilePhone":"15838008016","email":null,"leverCoin":{"id":1,"symbol":"ETH/USDT","coinSymbol":"ETH","baseSymbol":"USDT","enable":1,"sort":1,"proportion":5,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0},"coin":{"name":"ETH","nameCn":"ETH","unit":"ETH","status":0,"minTxFee":10,"cnyRate":1148.46,"maxTxFee":10,"usdRate":171.41171752,"sgdRate":0,"enableRpc":1,"sort":1,"canWithdraw":0,"canRecharge":1,"canTransfer":1,"canAutoWithdraw":0,"withdrawThreshold":100,"minWithdrawAmount":10,"maxWithdrawAmount":10,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":0,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0},"balance":0,"frozenBalance":0,"isLock":0,"status":0,"foldBtc":0}]
     * proportion : 5
     * explosionRiskRate : 120
     * explosionPrice : 0
     */

    private double memberId;
    private String symbol;
    private double riskRate;
    private double baseCanLoan;
    private double coinCanLoan;
    private double baseLoanCount;
    private double coinLoanCount;
    private double baseAccumulativeCount;
    private double coinAccumulativeCount;
    private double proportion;
    private double explosionRiskRate;
    private double explosionPrice;
    private List<LeverWalletListBean> leverWalletList;

    public double getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(int riskRate) {
        this.riskRate = riskRate;
    }

    public double getBaseCanLoan() {
        return baseCanLoan;
    }

    public void setBaseCanLoan(int baseCanLoan) {
        this.baseCanLoan = baseCanLoan;
    }

    public double getCoinCanLoan() {
        return coinCanLoan;
    }

    public void setCoinCanLoan(int coinCanLoan) {
        this.coinCanLoan = coinCanLoan;
    }

    public double getBaseLoanCount() {
        return baseLoanCount;
    }

    public void setBaseLoanCount(int baseLoanCount) {
        this.baseLoanCount = baseLoanCount;
    }

    public double getCoinLoanCount() {
        return coinLoanCount;
    }

    public void setCoinLoanCount(int coinLoanCount) {
        this.coinLoanCount = coinLoanCount;
    }

    public double getBaseAccumulativeCount() {
        return baseAccumulativeCount;
    }

    public void setBaseAccumulativeCount(int baseAccumulativeCount) {
        this.baseAccumulativeCount = baseAccumulativeCount;
    }

    public double getCoinAccumulativeCount() {
        return coinAccumulativeCount;
    }

    public void setCoinAccumulativeCount(int coinAccumulativeCount) {
        this.coinAccumulativeCount = coinAccumulativeCount;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }

    public double getExplosionRiskRate() {
        return explosionRiskRate;
    }

    public void setExplosionRiskRate(int explosionRiskRate) {
        this.explosionRiskRate = explosionRiskRate;
    }

    public double getExplosionPrice() {
        return explosionPrice;
    }

    public void setExplosionPrice(int explosionPrice) {
        this.explosionPrice = explosionPrice;
    }

    public List<LeverWalletListBean> getLeverWalletList() {
        return leverWalletList;
    }

    public void setLeverWalletList(List<LeverWalletListBean> leverWalletList) {
        this.leverWalletList = leverWalletList;
    }

    public static class LeverWalletListBean implements Serializable{
        /**
         * id : 5
         * memberId : 47
         * memberName : 明哥123
         * mobilePhone : 15838008016
         * email : null
         * leverCoin : {"id":1,"symbol":"ETH/USDT","coinSymbol":"ETH","baseSymbol":"USDT","enable":1,"sort":1,"proportion":5,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0}
         * coin : {"name":"USDT","nameCn":"USDT","unit":"USDT","status":0,"minTxFee":0.01,"cnyRate":6.71,"maxTxFee":0.01,"usdRate":1,"sgdRate":0,"enableRpc":1,"sort":0,"canWithdraw":1,"canRecharge":1,"canTransfer":1,"canAutoWithdraw":1,"withdrawThreshold":100,"minWithdrawAmount":10,"maxWithdrawAmount":1,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":4,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0}
         * balance : 0
         * frozenBalance : 0
         * isLock : 0
         * status : 0
         * foldBtc : 0
         */

        private double id;
        private double memberId;
        private String memberName;
        private String mobilePhone;
        private Object email;
        private LeverCoinBean leverCoin;
        private CoinBean coin;
        private double balance;
        private double frozenBalance;
        private double isLock;
        private double status;
        private double foldBtc;

        public double getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public LeverCoinBean getLeverCoin() {
            return leverCoin;
        }

        public void setLeverCoin(LeverCoinBean leverCoin) {
            this.leverCoin = leverCoin;
        }

        public CoinBean getCoin() {
            return coin;
        }

        public void setCoin(CoinBean coin) {
            this.coin = coin;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public double getFrozenBalance() {
            return frozenBalance;
        }

        public void setFrozenBalance(int frozenBalance) {
            this.frozenBalance = frozenBalance;
        }

        public double getIsLock() {
            return isLock;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
        }

        public double getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getFoldBtc() {
            return foldBtc;
        }

        public void setFoldBtc(int foldBtc) {
            this.foldBtc = foldBtc;
        }

        public static class LeverCoinBean implements Serializable{
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

            public double getEnable() {
                return enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public double getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public double getProportion() {
                return proportion;
            }

            public void setProportion(int proportion) {
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

            public void setMinTurnIntoAmount(int minTurnIntoAmount) {
                this.minTurnIntoAmount = minTurnIntoAmount;
            }

            public double getMinTurnOutAmount() {
                return minTurnOutAmount;
            }

            public void setMinTurnOutAmount(int minTurnOutAmount) {
                this.minTurnOutAmount = minTurnOutAmount;
            }
        }

        public static class CoinBean implements Serializable{
            /**
             * name : USDT
             * nameCn : USDT
             * unit : USDT
             * status : 0
             * minTxFee : 0.01
             * cnyRate : 6.71
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
             * maxWithdrawAmount : 1
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

            public void setUsdRate(int usdRate) {
                this.usdRate = usdRate;
            }

            public double getSgdRate() {
                return sgdRate;
            }

            public void setSgdRate(int sgdRate) {
                this.sgdRate = sgdRate;
            }

            public double getEnableRpc() {
                return enableRpc;
            }

            public void setEnableRpc(int enableRpc) {
                this.enableRpc = enableRpc;
            }

            public double getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public double getCanWithdraw() {
                return canWithdraw;
            }

            public void setCanWithdraw(int canWithdraw) {
                this.canWithdraw = canWithdraw;
            }

            public double getCanRecharge() {
                return canRecharge;
            }

            public void setCanRecharge(int canRecharge) {
                this.canRecharge = canRecharge;
            }

            public double getCanTransfer() {
                return canTransfer;
            }

            public void setCanTransfer(int canTransfer) {
                this.canTransfer = canTransfer;
            }

            public double getCanAutoWithdraw() {
                return canAutoWithdraw;
            }

            public void setCanAutoWithdraw(int canAutoWithdraw) {
                this.canAutoWithdraw = canAutoWithdraw;
            }

            public double getWithdrawThreshold() {
                return withdrawThreshold;
            }

            public void setWithdrawThreshold(int withdrawThreshold) {
                this.withdrawThreshold = withdrawThreshold;
            }

            public double getMinWithdrawAmount() {
                return minWithdrawAmount;
            }

            public void setMinWithdrawAmount(int minWithdrawAmount) {
                this.minWithdrawAmount = minWithdrawAmount;
            }

            public double getMaxWithdrawAmount() {
                return maxWithdrawAmount;
            }

            public void setMaxWithdrawAmount(int maxWithdrawAmount) {
                this.maxWithdrawAmount = maxWithdrawAmount;
            }

            public double getIsPlatformCoin() {
                return isPlatformCoin;
            }

            public void setIsPlatformCoin(int isPlatformCoin) {
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

            public void setMinerFee(int minerFee) {
                this.minerFee = minerFee;
            }

            public double getWithdrawScale() {
                return withdrawScale;
            }

            public void setWithdrawScale(int withdrawScale) {
                this.withdrawScale = withdrawScale;
            }

            public double getMinRechargeAmount() {
                return minRechargeAmount;
            }

            public void setMinRechargeAmount(int minRechargeAmount) {
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

            public void setMaxDailyWithdrawRate(int maxDailyWithdrawRate) {
                this.maxDailyWithdrawRate = maxDailyWithdrawRate;
            }
        }
    }
}
