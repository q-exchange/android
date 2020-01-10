package top.biduo.exchange.ui.edit_login_pwd;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import top.biduo.exchange.R;
import top.biduo.exchange.ui.dialog.CommonDialog;
import top.biduo.exchange.ui.dialog.CommonDialogFragment;
import top.biduo.exchange.ui.main.MainActivity;
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.entity.User;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

public class EditLoginPwdActivity extends BaseActivity implements EditLoginPwdContract.View {

    @BindView(R.id.ibBack)
    ImageButton ibBack;
//    @BindView(R.id.ibForgotPwd)
//    TextView ibForgotPwd;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etRepeatPwd)
    EditText etRepeatPwd;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    private EditLoginPwdContract.Presenter presenter;
    private String phone;
    private CountDownTimer timer;
    @BindView(R.id.yan)
    ImageView yan;
    @BindView(R.id.yan1)
    ImageView yan1;
    @BindView(R.id.yan2)
    ImageView yan2;
    @BindView(R.id.view_back)
    View view_back;

    private boolean isYan=false;
    private boolean isYan1=false;
    private boolean isYan2=false;
    private AlertDialog authDialog;

    public static void actionStart(Context context, String phone) {
        Intent intent = new Intent(context, EditLoginPwdActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_edit_login_pwd;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new EditLoginPwdPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        ibForgotPwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ForgotPwdActivity.actionStart(EditLoginPwdActivity.this);
//            }
//        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPwd();
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
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
                    etOldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan.setImageDrawable(yes);

                }else {
                    etOldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan.setImageDrawable(no);
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
                    etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan1.setImageDrawable(yes);

                }else {
                    etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan1.setImageDrawable(no);
                }
            }
        });
        yan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYan2=!isYan2;
                Drawable no = getResources().getDrawable(R.drawable.yan_no);
                Drawable yes = getResources().getDrawable(R.drawable.yan_yes);
                if (isYan2){
                    //显示
                    etRepeatPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan2.setImageDrawable(yes);

                }else {
                    etRepeatPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan2.setImageDrawable(no);
                }
            }
        });

    }

    private void sendCode() {
        tvSend.setEnabled(false);
        presenter.sendEditLoginPwdCode(getToken());
    }

    String AUTH_GOOGLE_NO="0";
    String AUTH_GOOGLE_YES="1";
    private void editPwd() {
        final String oldPassword = etOldPwd.getText().toString();
        final String newPassword = etNewPwd.getText().toString();
        String repeatePwd = etRepeatPwd.getText().toString();
        final String code = etCode.getText().toString();
        if (WonderfulStringUtils.isEmpty(oldPassword, newPassword, repeatePwd, code)){
            WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
            return;
        }
        if (!newPassword.equals(repeatePwd)){
            WonderfulToastUtils.showToast(getResources().getString(R.string.pwd_diff));
            return;
        }
        //如果开启了谷歌验证，还需要填写谷歌验证码
        String type=MyApplication.getApp().getCurrentUser().getGoogleState();
        if(AUTH_GOOGLE_NO.equals(type)){
            presenter.editPwd(getToken(), oldPassword, newPassword, code,"");
        }else if(AUTH_GOOGLE_YES.equals(type)){
            CommonDialogFragment commonDialogFragment=CommonDialogFragment.getInstance(CommonDialogFragment.TYPE_GOOGLE_CODE,getString(R.string.text_input_google_code),getString(R.string.text_input_google_code),getString(R.string.confirm),getString(R.string.cancle),true);
            commonDialogFragment.show(getSupportFragmentManager(),"google");
            commonDialogFragment.setCommitClickListener(new CommonDialogFragment.OnCommitClickListener() {
                @Override
                public void onCommitClick(String pass) {
                    if(TextUtils.isEmpty(pass)){
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_input_google_code));
                    }else{
                        presenter.editPwd(getToken(), oldPassword, newPassword, code,pass);
                        authDialog.cancel();
                    }
                }
            });
        }
    }

    @Override
    protected void obtainData() {
        phone = MyApplication.getApp().getCurrentUser().getMobile();
    }

    @Override
    protected void fillWidget() {
        tvNumber.setText(getResources().getString(R.string.changeLoginPasswordTip1)+ phone.substring(0, 3) + "****" + phone.substring(7));
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void setPresenter(EditLoginPwdContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void sendEditLoginPwdCodeSuccess(String obj) {
        WonderfulToastUtils.showToast(obj);
        fillCodeView(90L * 1000);
    }

    private void fillCodeView(long time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSend.setText(getResources().getString(R.string.re_send)+"(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                tvSend.setText(getResources().getString(R.string.send_code));
                tvSend.setEnabled(true);
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }

    @Override
    public void sendEditLoginPwdCodeFail(Integer code, String toastMessage) {
        tvSend.setEnabled(true);
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

    @Override
    public void editPwdSuccess(String obj) {
        WonderfulToastUtils.showToast(getResources().getString(R.string.changeLoginPasswordTip2));
        finish();
        MyApplication.getApp().setCurrentUser(new User());
        MainActivity.actionStart(this);
//        LoginActivity.actionStart(this);
    }

    @Override
    public void editPwdFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }
}
