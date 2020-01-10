package top.biduo.exchange.entity;

import java.io.Serializable;
import java.util.List;

public class BorrowRecordBean {


    /**
     * content : [{"id":6,"memberId":47,"memberName":"明哥123","coin":{"name":"BTC","nameCn":"BTC","unit":"BTC","status":0,"minTxFee":1,"cnyRate":16000,"maxTxFee":1,"usdRate":4000,"sgdRate":0,"enableRpc":0,"sort":1,"canWithdraw":0,"canRecharge":0,"canTransfer":1,"canAutoWithdraw":0,"withdrawThreshold":1,"minWithdrawAmount":1,"maxWithdrawAmount":1,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":0,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0},"leverCoin":{"id":4,"symbol":"BTC/USDT","coinSymbol":"BTC","baseSymbol":"USDT","enable":1,"sort":1,"proportion":3,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0},"amount":24,"interestRate":0.01,"loanBalance":24,"accumulative":0.24,"createTime":"2019-05-10 15:05:49","repayment":0}]
     * last : true
     * totalElements : 1
     * totalPages : 1
     * sort : [{"direction":"DESC","property":"id","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}]
     * first : true
     * numberOfElements : 1
     * size : 20
     * number : 0
     */

    private boolean last;
    private int totalElements;
    private int totalPages;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    private List<ContentBean> content;
    private List<SortBean> sort;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public List<SortBean> getSort() {
        return sort;
    }

    public void setSort(List<SortBean> sort) {
        this.sort = sort;
    }

    public static class ContentBean implements Serializable{
        /**
         * id : 6
         * memberId : 47
         * memberName : 明哥123
         * coin : {"name":"BTC","nameCn":"BTC","unit":"BTC","status":0,"minTxFee":1,"cnyRate":16000,"maxTxFee":1,"usdRate":4000,"sgdRate":0,"enableRpc":0,"sort":1,"canWithdraw":0,"canRecharge":0,"canTransfer":1,"canAutoWithdraw":0,"withdrawThreshold":1,"minWithdrawAmount":1,"maxWithdrawAmount":1,"isPlatformCoin":0,"hasLegal":false,"allBalance":null,"coldWalletAddress":null,"hotAllBalance":null,"minerFee":0,"withdrawScale":0,"minRechargeAmount":0,"masterAddress":null,"maxDailyWithdrawRate":0}
         * leverCoin : {"id":4,"symbol":"BTC/USDT","coinSymbol":"BTC","baseSymbol":"USDT","enable":1,"sort":1,"proportion":3,"interestRate":0.01,"minTurnIntoAmount":0,"minTurnOutAmount":0}
         * amount : 24
         * interestRate : 0.01
         * loanBalance : 24
         * accumulative : 0.24
         * createTime : 2019-05-10 15:05:49
         * repayment : 0
         */

        private int id;
        private int memberId;
        private String memberName;
        private CoinBean coin;
        private LeverCoinBean leverCoin;
        private double amount;
        private double interestRate;
        private double loanBalance;
        private double accumulative;
        private String createTime;
        private int repayment;

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

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
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

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(double interestRate) {
            this.interestRate = interestRate;
        }

        public double getLoanBalance() {
            return loanBalance;
        }

        public void setLoanBalance(double loanBalance) {
            this.loanBalance = loanBalance;
        }

        public double getAccumulative() {
            return accumulative;
        }

        public void setAccumulative(double accumulative) {
            this.accumulative = accumulative;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getRepayment() {
            return repayment;
        }

        public void setRepayment(int repayment) {
            this.repayment = repayment;
        }

        public static class CoinBean implements Serializable{
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
            private int sort;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
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

        public static class LeverCoinBean implements Serializable{
            /**
             * id : 4
             * symbol : BTC/USDT
             * coinSymbol : BTC
             * baseSymbol : USDT
             * enable : 1
             * sort : 1
             * proportion : 3
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

    public static class SortBean {
        /**
         * direction : DESC
         * property : id
         * ignoreCase : false
         * nullHandling : NATIVE
         * ascending : false
         * descending : true
         */

        private String direction;
        private String property;
        private boolean ignoreCase;
        private String nullHandling;
        private boolean ascending;
        private boolean descending;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public String getNullHandling() {
            return nullHandling;
        }

        public void setNullHandling(String nullHandling) {
            this.nullHandling = nullHandling;
        }

        public boolean isAscending() {
            return ascending;
        }

        public void setAscending(boolean ascending) {
            this.ascending = ascending;
        }

        public boolean isDescending() {
            return descending;
        }

        public void setDescending(boolean descending) {
            this.descending = descending;
        }
    }
}
