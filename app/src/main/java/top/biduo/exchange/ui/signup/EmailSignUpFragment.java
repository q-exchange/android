package top.biduo.exchange.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.captchasdk.TCaptchaDialog;
import com.tencent.captchasdk.TCaptchaVerifyListener;

import top.biduo.exchange.R;
import top.biduo.exchange.app.GlobalConstant;
import top.biduo.exchange.config.AppConfig;
import top.biduo.exchange.ui.country.CountryActivity;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.Captcha;
import top.biduo.exchange.entity.Country;
import top.biduo.exchange.ui.message_detail.MessageHelpActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

/**
 * Created by Administrator on 2018/2/2.
 */

public class EmailSignUpFragment extends BaseTransFragment implements SignUpContract.EmailView {
    public static final String TAG = EmailSignUpFragment.class.getSimpleName();
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvChangeType)
    TextView tvChangeType;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tuijian)
    EditText tuijian;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text_yonghu)
    TextView text_yonghu;
    private boolean isYan = false;
    private boolean isYan1 = false;
    @BindView(R.id.yan)
    ImageView yan;
    @BindView(R.id.yan1)
    ImageView yan1;
    @BindView(R.id.mLlCounty)
    LinearLayout mLlCounty;
    private Country country;
    private CountDownTimer timer;
    private SignUpContract.EmailPresenter presenter;
    private TCaptchaDialog dialog;
    private String email, username, password, tuijian2;

    public static EmailSignUpFragment getInstance() {
        EmailSignUpFragment emailSignUpFragment = new EmailSignUpFragment();
        return emailSignUpFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof BaseSignUpFragment.OperateCallback)) {
            throw new RuntimeException("The Activity which this fragment is located must implement the OperateCallback interface!");
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
    protected int getLayoutId() {
        return R.layout.fragment_email_sign_up;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new EmailSignUpPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
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
                ((BaseSignUpFragment.OperateCallback) getActivity()).switchType(BaseSignUpFragment.Type.PHONE);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpByEmail();
            }
        });
        mLlCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryActivity.actionStart(EmailSignUpFragment.this);
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

    private String countryStr;

    private void signUpByEmail() {
        email = etEmail.getText().toString();
        username = etUserName.getText().toString();
        password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        tuijian2 = tuijian.getText().toString();
        if (country == null) {
            countryStr = "中国";
        } else {
            countryStr = country.getZhName();
        }
        if (WonderfulStringUtils.isEmpty(email, username, password, rePassword, countryStr) ) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
            return;
        }
        if(!email.contains("@")){
            WonderfulToastUtils.showToast(getResources().getString(R.string.email_diff));
            return;
        }
        if (!checkbox.isChecked()) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.xieyi));
            return;
        }
        if (!password.equals(rePassword)) {
            WonderfulToastUtils.showToast(R.string.pwd_diff);
            return;
        } else {
            presenter.signUpByEmail(email, username, password, countryStr ,"", "",tuijian2);
            /*dialog = new TCaptchaDialog(getmActivity(), AppConfig.AUTH_APP_ID, listener, null);
            dialog.show();*/
        }
    }

    private TCaptchaVerifyListener listener = new TCaptchaVerifyListener() {
        @Override
        public void onVerifyCallback(JSONObject jsonObject) {
            int ret = 0;
            try {
                ret = jsonObject.getInt("ret");
                if (ret == 0) {
                    String ticket = jsonObject.getString("ticket");
                    String randstr = jsonObject.getString("randstr");
                    String seccode = "";
                    Log.i("ticket+randstr",ticket+"-"+randstr);
                    presenter.signUpByEmail(email, username, password, countryStr ,ticket, randstr,tuijian2);
                    dialog.cancel();
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
    public void setPresenter(SignUpContract.EmailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signUpByEmailSuccess(String obj) {
        WonderfulToastUtils.showToast(obj);
        finish();
    }

    @Override
    public void signUpByEmailFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }


    @Override
    protected String getmTag() {
        return TAG;
    }
}
