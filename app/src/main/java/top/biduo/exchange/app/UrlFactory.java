package top.biduo.exchange.app;

import top.biduo.exchange.config.AppConfig;

/**
 * Created by Administrator on 2018/1/29.
 */

public class UrlFactory {

    public static final String host = AppConfig.BASE_URL;

    //获取充币地址
    public static String getChongbi() {
        return host + "/uc/asset/wallet/reset-address";
    }

    //申请成为商家
    public static String getSellerApply() {
        return host + "/uc/approve/certified/business/apply";
    }

    //    提币验证码
    public static String getCode() {
        return host + "/uc/mobile/withdraw/code";
    }

    //    提币接口
    public static String getTIBi() {
        return host + "/uc/withdraw/apply";
    }

    //获取保证金币种列表
    public static String getDepositList() {
        return host + "/uc/approve/business-auth-deposit/list";
    }

    //帮助中心
    public static String getHelp() {
        return host + "/uc/ancillary/more/help";
    }

    //帮助中心
    public static String getHelpNew() {
        return host + "/uc/ancillary/system/help";
    }


    public static String getHelpXinShou() {
        return host + "/uc/ancillary/more/help/page";
    }


    //交易明细接口
    public static String getCha() {
        return host + "/uc/asset/transaction/all";
    }

    //提币明细
    public static String getChaTiBi() {
        return host + "/uc/withdraw/record";
    }

    //
    public static String getShangjia() {
        return host + "/uc/approve/certified/business/status";
    }

    // 获取汇率
    public static String getRateUrl() {
        return host + "/market/exchange-rate/usd-cny";
    }

    public static String getPhoneCodeUrl() {
        return host + "/uc/mobile/code";
    }

    //注册
    public static String getSignUpByPhone() {
        return host + "/uc/register/phone";
    }

    public static String getSignUpByEmail() {
        return host + "/uc/register/email";
    }

    public static String getLoginUrl() {
        return host + "/uc/login";
    }

    public static String getLoginCodeUrl() {
        return host + "/uc/login/phone";
    }

    public static String getKDataUrl() {
        return host + "/market/history";
    }

    public static String getAllCurrency() {
        return host + "/market/symbol-thumb";
    }

    /**
     * 首页获取所有的币种
     */
    public static String getAllCurrencys() {
        return host + "/market/overview";
    }

    /**
     * 得到信息，来设置输入小数点位数的限制
     */
    public static String getSymbolInfo() {
        return host + "/market/symbol-info";
    }

    public static String getFindUrl() {
        return host + "/exchange/favor/find";
    }

    public static String getDeleteUrl() {
        return host + "/exchange/favor/delete";
    }

    public static String getAddUrl() {
        return host + "/exchange/favor/add";
    }

    public static String getExChangeUrl() {
        return host + "/exchange/order/add";
    }

    public static String getWalletUrl() {
        return host + "/uc/asset/wallet/";
    }//币币资产


    public static String getFiatAssetUrl() {
        return host + "/uc/otc/wallet/get";
    }//法币资产

    public static String getMarginAssetUrl() {
        return host + "/margin-trade/lever_wallet/list";
    }//杠杆资产

    public static String getAllUrl() {
        return host + "/otc/coin/all";
    }

    public static String getAdvertiseUrl() {
        return host + "/otc/advertise/page";
    }

    public static String getCountryUrl() {
        return host + "/uc/support/country";
    }

    public static String getReleaseAdUrl() {
        return host + "/otc/advertise/create";
    }

    public static String getUploadPicUrl() {
        return host + "/uc/upload/oss/base64";
    }

    public static String getNameUrl() {
        return host + "/uc/approve/real/name";
    }

    public static String getAccountPwdUrl() {
        return host + "/uc/approve/transaction/password";
    }

    public static String getAllAdsUrl() {
        return host + "/otc/advertise/all";
    }

    public static String getReleaseUrl() {
        return host + "/otc/advertise/on/shelves";
    }

    public static String getDeleteAdsUrl() {
        return host + "/otc/advertise/delete";
    }

    public static String getOffShelfUrl() {
        return host + "/otc/advertise/off/shelves";
    }

    public static String getAdDetailUrl() {
        return host + "/otc/advertise/detail";
    }

    public static String getUpdateAdUrl() {
        return host + "/otc/advertise/update";
    }

    public static String getC2CInfoUrl() {
        return host + "/otc/order/pre";
    }

    public static String getC2CBuyUrl() {
        return host + "/otc/order/buy";
    }

    public static String getC2CSellUrl() {
        return host + "/otc/order/sell";
    }

    public static String getMyOrderUrl() {
        return host + "/otc/order/self";
    }

    public static String getExtractinfoUrl() {
        return host + "/uc/withdraw/support/coin/info";
    }

    public static String getExtractUrl() {
        return host + "/uc/withdraw/apply";
    }

    public static String getAllTransactionUrl2() {
        return host + "/uc/asset/transaction";
    }

    public static String getSafeSettingUrl() {
        return host + "/uc/approve/security/setting";
    }

    public static String getAvatarUrl() {
        return host + "/uc/approve/change/avatar";
    }

    public static String getBindPhoneUrl() {
        return host + "/uc/approve/bind/phone";
    }

    public static String getSendCodeUrl() {
        return host + "/uc/mobile/bind/code";
    }

    public static String getBindEmailUrl() {
        return host + "/uc/approve/bind/email";
    }

    public static String getSendEmailCodeUrl() {
        return host + "/uc/bind/email/code";
    }

    public static String getEditLoginPwdUrl() {
        return host + "/uc/mobile/update/password/code";
    }

    public static String getSendEditAccountPwdCodeUrl() {
        return host + "/uc/mobile/trade/code";
    }

    public static String getEditPwdUrl() {
        return host + "/uc/approve/update/password";
    }

    public static String getPlateUrl() {
        return host + "/market/exchange-plate";
    }

    /**
     * 查询当前委托
     */
    public static String getEntrustUrl() {
        return host + "/exchange/order/personal/current";
    }

    /**
     * 获取历史委托记录
     */
    public static String getHistoryEntrus() {
        return host + "/exchange/order/personal/history";
    }

    public static String getCancleEntrustUrl() {
        return host + "/exchange/order/cancel/";
    }

    public static String getPhoneForgotPwdCodeUrl() {
        return host + "/uc/mobile/reset/code";
    }

    public static String getEmailForgotPwdCodeUrl() {
        return host + "/uc/reset/email/code";
    }

    public static String getForgotPwdUrl() {
        return host + "/uc/reset/login/password";
    }

    public static String getCaptchaUrl() {
        return host + "/uc/start/captcha";
    }

    public static String getSendChangePhoneCodeUrl() {
        return host + "/uc/mobile/change/code";
    }

    public static String getChangePhoneUrl() {
        return host + "/uc/approve/change/phone";
    }

    public static String getMessageUrl() {
        return host + "/uc/announcement/page";
    }

    public static String getMessageDetailUrl() {
        return host + "/uc/announcement/";
    }

    public static String getMessageHelpUrl() {
        return host + "/uc/ancillary/more/help/detail";
    }

    public static String getMessageHelpUrlNew() {
        return host + "/uc/ancillary/system/help/";
    }


    public static String getRemarkUrl() {
        return host + "/uc/feedback";
    }

    public static String getAppInfoUrl() {
        return host + "/uc/ancillary/website/info";
    }

    public static String getBannersUrl() {
        return host + "/uc/ancillary/system/advertise";
    }

    public static String getOrderDetailUrl() {
        return host + "/otc/order/detail";
    }

    public static String getCancleUrl() {
        return host + "/otc/order/cancel";
    }

    public static String getpayDoneUrl() {
        return host + "/otc/order/pay";
    }

    public static String getReleaseOrderUrl() {
        return host + "/otc/order/release";
    }

    public static String getAppealUrl() {
        return host + "/otc/order/appeal";
    }

    public static String getEditAccountPwdUrl() {
        return host + "/uc/approve/update/transaction/password";
    }

    public static String getResetAccountPwdUrl() {
        return host + "/uc/approve/reset/transaction/password";
    }

    public static String getResetAccountPwdCodeUrl() {
        return host + "/uc/mobile/transaction/code";
    }

    public static String getHistoryMessageUrl() {
        return host + "/chat/getHistoryMessage";
    }

    public static String getEntrustHistory() {
        return host + "/exchange/order/history";
    }

    public static String getCreditInfo() {
        return host + "/uc/approve/real/detail";
    }

    public static String getNewVision() {
        return host + "/uc/ancillary/system/app/version/0";
    }

    public static String getSymbolUrl() {
        return host + "/market/symbol";
    }

    public static String getAccountSettingUrl() {
        return host + "/uc/approve/account/setting";
    }

    public static String getBindBankUrl() {
        return host + "/uc/approve/bind/bank";
    }

    public static String getUpdateBankUrl() {
        return host + "/uc/approve/update/bank";
    }

    public static String getBindAliUrl() {
        return host + "/uc/approve/bind/ali";
    }

    public static String getUpdateAliUrl() {
        return host + "/uc/approve/update/ali";
    }

    public static String getBindWechatUrl() {
        return host + "/uc/approve/bind/wechat";
    }

    public static String getUpdateWechatUrl() {
        return host + "/uc/approve/update/wechat";
    }

    public static String getCheckMatchUrl() {
        return host + "/uc/asset/wallet/match-check";
    }

    public static String getStartMatchUrl() {
        return host + "/uc/asset/wallet/match";
    }

    public static String getPromotionUrl() {
        return host + "/uc/promotion/record";
    }

    public static String getPromotionRewardUrl() {
        return host + "/uc/promotion/reward/record";
    }

    public static String getDepth() {
        return host + "/market/exchange-plate-full";
    } // 获取深度图数据

    public static String getVolume() {
        return host + "/market/latest-trade";
    } // 获取成交数据

    public static String getLoginAuthType() {
        return host + "/uc/get/user";
    } // 获取认证方式（是否绑定谷歌认证）

    public static String getServiceFeeAll() {
        return host + "/uc/integration/grade";
    } // 获取所有手续费

    public static String getUserWithdrawLimit() {
        return host + "/uc/integration/day_withdraw/limit";
    } // 获取用户当日提币数量和次数

    public static String getScoreRecord() {
        return host + "/uc/integration/record/page_query";
    } // 用户积分查询

    public static String getCandyRecord() {
        return host + "/uc/gift/record";
    } // 获取用户糖果记录

    public static String getRandom() {
        return host + "/uc/approve/video/random";
    } // 获取视频随机数

    public static String uploadVideo() {
        return host + "/uc/upload/video";
    } // 上传视频

    public static String creditVideo() {
        return host + "/uc/approve/kyc/real/name";
    } // 提交视频认证

    public static String getTransferSupportCoin() {
        return host + "/uc/transfer/support_coin";
    } // 获取支持划转币种

    public static String transferFiat() {
        return host + "/uc/otc/wallet/transfer";
    } // 币币和法币划转

    public static String transferIntoMargin() {
        return host + "/margin-trade/lever_wallet/change_into";
    } // 币币转杠杆
    public static String transferOutMargin() {
        return host + "/margin-trade/lever_wallet/turn_out";
    } // 杠杆转币币

    public static String borrowCoin() {
        return host + "/margin-trade/loan/loan";
    } // 借贷

    public static String borrowRecord() {
        return host + "/margin-trade/loan/record_list";
    } // 借贷记录

    public static String getRepaymentRecord() {
        return host + "/margin-trade/repayment/history";
    } // 归还记录

    public static String giveBackCoin() {
        return host + "/margin-trade/loan/repayment";
    } // 还币


    public static String addMarginOrder() {
        return host + "/margin-trade/order/add";
    } // 添加杠杆订单

    public static String getOrderHistoryMargin() {
        return host + "/margin-trade/order/history";
    } // 查询历史委托-杠杆

    public static String getOrderCurrentMargin() {
        return host + "/margin-trade/order/current";
    } // 查询当前委托-杠杆

    public static String getOrderDetailMargin() {
        return host + "/margin-trade/order/detail/";
    } // 查询杠杆委托成交明细
    public static String getCancelOrderMargin() {
        return host + "/margin-trade/order/cancel/";
    } // 取消当前杠杆委托


    public static String getIeo() {
        return host + "/uc/ieo/all";
    } // 获取ieo

    public static String getIeoRecord() {
        return host + "/uc/ieo/record";
    } // 获取ieo记录

    public static String takeOrderIeo() {
        return host + "/uc/ieo/order";
    } // ieo下单

    public static String getDefaultSymbol() {
        return host + "/market/default/symbol";
    } // 获取首次进入默认的交易对


    public static String getTransactionType() {
        return host + "/uc/asset/transaction_type";
    } // 获取资产流水的交易类型


    public static String getFeeRate() {
        return host + "/uc/member/getFeeRate";
    }

    public static String sendLoginCode() {
        return host + "/uc/mobile/login/send";
    }

    public static String getTotalAssets() {
        return host + "/margin-trade/lever_wallet/getTotalAssets";
    }


}
