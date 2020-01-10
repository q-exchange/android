package top.biduo.exchange.entity;


import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/26.
 */

public class User implements Serializable {

    private static final long serialVersionUID=7981560250804078637L;
    private String username;
    private int id;
    private String token;

    private String createTime;
    private int realVerified;
    private String emailVerified;
    private String phoneVerified;
    private String loginVerified;
    private String fundsVerified;
    private String realAuditing;
    private String mobilePhone;
    private String email;
    private String realName;
    private String realNameRejectReason;
    private String idCard;
    private String avatar;
    private String accountVerified;
    private String transactions;
    private String transactionTime;
    private String level;
    private String integration;
    private String kycStatus;
    private String memberGradeId;
    private String googleState;
    private String memberLevel;
    private Location location;
    private boolean isSelect;
    private Country country;
    private String promotionPrefix;
    private String promotionCode;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRealVerified() {
        return realVerified;
    }

    public void setRealVerified(int realVerified) {
        this.realVerified = realVerified;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(String phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getLoginVerified() {
        return loginVerified;
    }

    public void setLoginVerified(String loginVerified) {
        this.loginVerified = loginVerified;
    }

    public String getFundsVerified() {
        return fundsVerified;
    }

    public void setFundsVerified(String fundsVerified) {
        this.fundsVerified = fundsVerified;
    }

    public String getRealAuditing() {
        return realAuditing;
    }

    public void setRealAuditing(String realAuditing) {
        this.realAuditing = realAuditing;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealNameRejectReason() {
        return realNameRejectReason;
    }

    public void setRealNameRejectReason(String realNameRejectReason) {
        this.realNameRejectReason = realNameRejectReason;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(String accountVerified) {
        this.accountVerified = accountVerified;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getGoogleState() {
        return googleState;
    }

    public void setGoogleState(String googleState) {
        this.googleState = googleState;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getMemberGradeId() {
        return memberGradeId;
    }

    public void setMemberGradeId(String memberGradeId) {
        this.memberGradeId = memberGradeId;
    }

    public String getPromotionPrefix() {
        return promotionPrefix;
    }

    public void setPromotionPrefix(String promotionPrefix) {
        this.promotionPrefix = promotionPrefix;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", location=" + location +
                ", memberLevel=" + memberLevel +
                ", realName='" + realName + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    public static class Location implements Serializable {

        private String country;
        private String province;
        private String city;
        private String district;


        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

}
