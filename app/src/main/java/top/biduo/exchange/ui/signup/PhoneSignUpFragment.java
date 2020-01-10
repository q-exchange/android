package top.biduo.exchange.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import top.biduo.exchange.R;
import top.biduo.exchange.config.AppConfig;
import top.biduo.exchange.ui.country.CountryActivity;
import top.biduo.exchange.ui.login.LoginActivity;
import top.biduo.exchange.ui.message_detail.MessageHelpActivity;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.Country;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import com.tencent.captchasdk.TCaptchaDialog;
import com.tencent.captchasdk.TCaptchaVerifyListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

/**
 * Created by Administrator on 2018/2/2.
 */

public class PhoneSignUpFragment extends BaseTransFragment implements SignUpContract.PhoneView {

    public static final String TAG = PhoneSignUpFragment.class.getSimpleName();
    public static String token = "";
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvChangeType)
    TextView tvChangeType;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvToRegist)
    TextView tvToRegist;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text_yonghu)
    TextView text_yonghu;
    @BindView(R.id.mLlCounty)
    LinearLayout mLlCounty;

    private boolean isYan = false;
    private boolean isYan1 = false;
    @BindView(R.id.yan)
    ImageView yan;
    @BindView(R.id.yan1)
    ImageView yan1;
    @BindView(R.id.tuijian)
    EditText tuijian;
    private Country country;
    private CountDownTimer timer;
    private SignUpContract.PhonePresenter presenter;

    private String challenge="";
    private String validate="";
    private String seccode;

    private TCaptchaDialog dialog;
    private TCaptchaVerifyListener listener = new TCaptchaVerifyListener() {
        @Override
        public void onVerifyCallback(JSONObject jsonObject) {
            int ret = 0;
            try {
                ret = jsonObject.getInt("ret");
                if (ret == 0) {
                    //验证成功回调
                    //jsonObject.getInt("ticket")为验证码票据
                    //jsonObject.getString("appid")为appid
                    //jsonObject.getString("randstr")为随机串
                    WonderfulLogUtils.logi("miao", countryStr + "----" + phone);
                    presenter.phoneCode(phone, countryStr);
                    challenge = jsonObject.getString("ticket");
                    validate = jsonObject.getString("randstr");
                    seccode = "";
                    tvGetCode.setEnabled(false);
                } else if (ret == -1001) {
                    //验证码首个TCaptcha.js加载错误，业务可以根据需要重试
                    //jsonObject.getString("info")为错误信息
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                } else {
                    //验证失败回调，一般为用户关闭验证码弹框
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof BaseSignUpFragment.OperateCallback)) {
            throw new RuntimeException("The Activity which fragment is located must implement the OperateCallback interface!");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static PhoneSignUpFragment getInstance() {
        PhoneSignUpFragment phoneSignUpFragment = new PhoneSignUpFragment();
        return phoneSignUpFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_sign_up;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new PhoneSignUpPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvChangeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseSignUpFragment.OperateCallback) getActivity()).switchType(BaseSignUpFragment.Type.EMAIL);
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpByPhone();

            }
        });
        mLlCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryActivity.actionStart(PhoneSignUpFragment.this);
            }
        });
        tvToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.actionStart(getActivity());
            }
        });
        text_yonghu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageHelpActivity.actionStart(getmActivity(), GlobalConstant.USER_AGREEMENT_ID);
            }
        });
        yan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYan = !isYan;
                Drawable no = getResources().getDrawable(R.drawable.yan_no);
                Drawable yes = getResources().getDrawable(R.drawable.yan_yes);
                if (isYan) {
                    //显示
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan.setImageDrawable(yes);

                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan.setImageDrawable(no);
                }
            }
        });
        yan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYan1 = !isYan1;
                Drawable no = getResources().getDrawable(R.drawable.yan_no);
                Drawable yes = getResources().getDrawable(R.drawable.yan_yes);
                if (isYan1) {
                    //显示
                    etRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan1.setImageDrawable(yes);

                } else {
                    etRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan1.setImageDrawable(no);
                }
            }
        });
    }

    private void signUpByPhone() {
        String phone = etPhone.getText().toString();
        String username = etUsername.getText().toString();
        String code = etCode.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        String country = "";
        String tuijianma = tuijian.getText().toString();
        if (this.country == null) country = "中国";
        else country = this.country.getZhName();
        if (WonderfulStringUtils.isEmpty(phone, code, username, password, rePassword, country)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
            return;
        }
        if (!checkbox.isChecked()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.xieyi));
            return;
        }
        if (!password.equals(rePassword)) {
            WonderfulToastUtils.showToast(R.string.pwd_diff);
            return;
        }
        WonderfulLogUtils.logi("miao", challenge + "-----" + validate);
        presenter.signUpByPhone(phone, username, password, country, code, tuijianma, challenge, validate, seccode);
    }

    private String countryStr;
    private String phone;

    private void getCode() {
        countryStr = "";
        phone = "";
        if (country == null) countryStr = "中国";
        else countryStr = country.getZhName();
        phone = etPhone.getText().toString();
        if (WonderfulStringUtils.isEmpty(phone) || phone.length() < 11) {
            WonderfulToastUtils.showToast(R.string.phone_not_correct);
            return;
        }
        tvGetCode.setEnabled(false);
        /**
         @param context，上下文
         @param appid，业务申请接入验证码时分配的appid
         @param listener，验证码验证结果回调
         @param jsonString，业务自定义参数
         */
        /*dialog = new TCaptchaDialog(getmActivity(), AppConfig.AUTH_APP_ID, listener, null);
        dialog.show();*/
        presenter.phoneCode(phone, countryStr);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(getActivity(), llTitle);
            isSetTitle = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CountryActivity.RETURN_COUNTRY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                country = (Country) data.getSerializableExtra("country");
                int languageCode= SharedPreferenceInstance.getInstance().getLanguageCode();
                if(languageCode==1){
                    tvCountry.setText(country.getZhName());
                }else if(languageCode==2){
                    tvCountry.setText(country.getEnName());
                }

            }
        }
    }

    @Override
    public void setPresenter(SignUpContract.PhonePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void phoneCodeSuccess(String obj) {
        WonderfulToastUtils.showToast(obj);
        fillCodeView(90 * 1000);

    }

    private void fillCodeView(long time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText(getActivity().getResources().getString(R.string.re_send) + "（" + millisUntilFinished / 1000 + "）");
            }

            @Override
            public void onFinish() {
                tvGetCode.setText(R.string.send_code);
                tvGetCode.setEnabled(true);
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }

    @Override
    public void phoneCodeFail(Integer code, String toastMessage) {
        tvGetCode.setEnabled(true);
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void signUpByPhoneSuccess(String obj) {
        WonderfulToastUtils.showToast(obj);
        finish();

    }

    @Override
    public void signUpByPhoneFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    protected String getmTag() {
        return TAG;
    }
}
