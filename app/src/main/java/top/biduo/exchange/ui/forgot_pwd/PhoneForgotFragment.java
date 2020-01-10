package top.biduo.exchange.ui.forgot_pwd;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import top.biduo.exchange.R;
import top.biduo.exchange.base.BaseTransFragment;
import top.biduo.exchange.entity.Captcha;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import org.json.JSONObject;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

public class PhoneForgotFragment extends BaseTransFragment implements ForgotPwdContract.PhoneView {
    public static final String TAG = PhoneForgotFragment.class.getSimpleName();
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
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.yan)
    ImageView yan;
    private boolean isYan=false;
    private boolean isYan1=false;

    @BindView(R.id.yan1)
    ImageView yan1;

    private CountDownTimer timer;
    private ForgotPwdContract.PhonePresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof BaseForgotFragment.OperateCallback)) {
            throw new RuntimeException("fragment所在的Activity必须实现OperateCallback接口！");
        }
    }

    public static PhoneForgotFragment getInstance() {
        PhoneForgotFragment phoneForgotFragment = new PhoneForgotFragment();
        return phoneForgotFragment;
    }

    @Override
    public void onDestroyView() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_forgot;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new PhoneForgotPresenter(Injection.provideTasksRepository(getActivity().getApplicationContext()), this);
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
                ((BaseForgotFragment.OperateCallback) getActivity()).switchType(BaseForgotFragment.Type.EMAIL);
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        yan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYan=!isYan;
                Drawable no = getResources().getDrawable(R.drawable.yan_no);
                Drawable yes = getResources().getDrawable(R.drawable.yan_yes);
                if (isYan){
                    //显示
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan.setImageDrawable(no);

                }else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan.setImageDrawable(yes);
                }
            }
        });
        yan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYan1=!isYan1;
                Drawable no = getResources().getDrawable(R.drawable.yan_no);
                Drawable yes = getResources().getDrawable(R.drawable.yan_yes);
                if (isYan1){
                    //显示
                    etRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan1.setImageDrawable(no);

                }else {
                    etRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan1.setImageDrawable(yes);
                }
            }
        });

    }

    private void submit() {
        String account = etUsername.getText().toString();
        String code = etCode.getText().toString();
        String mode = "0";
        String password = etPassword.getText().toString();
        String passwordRe = etRePassword.getText().toString();

        if (WonderfulStringUtils.isEmpty(account, code, mode, password, passwordRe)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
            return;}
        if (!password.equals(passwordRe)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.pwd_diff));
            return;
        }
        presenter.forgotPwd(account, code, mode, password);
    }

    private void getCode() {
        String phone = etUsername.getText().toString();
        if (WonderfulStringUtils.isEmpty(phone) || phone.length() < 11) {
            WonderfulToastUtils.showToast(R.string.phone_not_correct);
            return;
        }
        tvGetCode.setEnabled(false);
        presenter.phoneForgotCode(phone,"","","");
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
    public void setPresenter(ForgotPwdContract.PhonePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void phoneForgotCodeSuccess(String obj) {
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
    public void phoneForgotCodeFail(Integer code, String toastMessage) {
        tvGetCode.setEnabled(true);
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    public void forgotPwdSuccess(String obj) {

        WonderfulToastUtils.showToast(obj);
        finish();
    }

    @Override
    public void forgotPwdFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(getmActivity(), code, toastMessage);
    }

    @Override
    protected String getmTag() {
        return TAG;
    }
}
