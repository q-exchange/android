package top.biduo.exchange.ui.account_pwd;

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
import top.biduo.exchange.app.MyApplication;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.ui.dialog.CommonDialog;
import top.biduo.exchange.ui.dialog.CommonDialogFragment;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

import butterknife.BindView;
import top.biduo.exchange.app.Injection;

public class EditAccountPwdActivity extends BaseActivity implements AccountPwdContract.EditView {

    @BindView(R.id.ibBack)
    ImageButton ibBack;

    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.etLoginPwd)
    EditText etLoginPwd;
    @BindView(R.id.etOld)
    EditText etOld;
    @BindView(R.id.etNew)
    EditText etNew;
    @BindView(R.id.etRepeate)
    EditText etRepeate;
    @BindView(R.id.tvForgot)
    TextView tvForgot;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.yan)
    ImageView yan;
    @BindView(R.id.yan1)
    ImageView yan1;
    @BindView(R.id.yan2)
    ImageView yan2;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvSend)
    TextView tvSend;
    private boolean isYan=false;
    private boolean isYan1=false;
    private boolean isYan2=false;
    @BindView(R.id.view_back)
    View view_back;
    private CountDownTimer timer;
    private AccountPwdContract.EditPresenter presenter;
    private AlertDialog authDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditAccountPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ResetAccountPwdActivity.RETURN_FROM_RESETACCOUNT_PWD) {
                finish();
            }
        }
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_edit_account_pwd;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        new EditPwdPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPwd();
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toReset();
            }
        });
        view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    etOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan.setImageDrawable(yes);

                }else {
                    etOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                    etNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan1.setImageDrawable(yes);

                }else {
                    etNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                    etRepeate.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    yan2.setImageDrawable(yes);

                }else {
                    etRepeate.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    yan2.setImageDrawable(no);
                }
            }
        });

    }

    private void sendCode() {
        tvSend.setEnabled(false);
        presenter.sendEditAccountPwdCode(getToken());
    }

    @Override
    public void sendEditAccountPwdCodeSuccess(String obj) {
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
    public void sendEditAccountPwdCodeFail(Integer code, String toastMessage) {
        tvSend.setEnabled(true);
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

    private void toReset() {
        ResetAccountPwdActivity.actionStart(this);
    }

    String AUTH_GOOGLE_NO="0";
    String AUTH_GOOGLE_YES="1";
    private void editPwd() {
        final String newPassword = etNew.getText().toString();
        final String oldPassword = etOld.getText().toString();
        String re = etRepeate.getText().toString();
        final String code = etCode.getText().toString();
        if (WonderfulStringUtils.isEmpty(newPassword, oldPassword, re,code)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.Incomplete_information));
            return;
        }
        if (!re.equals(newPassword)) {
            WonderfulToastUtils.showToast(getResources().getString(R.string.pwd_diff));
            return;
        }
        //如果开启了谷歌验证，还需要填写谷歌验证码
        String type= MyApplication.getApp().getCurrentUser().getGoogleState();
        if(AUTH_GOOGLE_NO.equals(type)){
            presenter.editAccountPed(getToken(), newPassword, oldPassword,code,"");
        }else if(AUTH_GOOGLE_YES.equals(type)){
            CommonDialogFragment commonDialogFragment=CommonDialogFragment.getInstance(CommonDialogFragment.TYPE_GOOGLE_CODE,getString(R.string.text_input_google_code),getString(R.string.text_input_google_code),getString(R.string.confirm),getString(R.string.cancle),true);
            commonDialogFragment.show(getSupportFragmentManager(),"google");
            commonDialogFragment.setCommitClickListener(new CommonDialogFragment.OnCommitClickListener() {
                @Override
                public void onCommitClick(String pass) {
                    if(TextUtils.isEmpty(pass)){
                        WonderfulToastUtils.showToast(getResources().getString(R.string.text_input_google_code));
                    }else{
                        presenter.editAccountPed(getToken(), newPassword, oldPassword,code,pass);
                        authDialog.cancel();
                    }
                }
            });
        }
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
            isSetTitle = true;
            ImmersionBar.setTitleBar(this, llTitle);
        }
    }

    @Override
    public void setPresenter(AccountPwdContract.EditPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void editAccountPedSuccess(String obj) {
        WonderfulToastUtils.showToast(obj);
        finish();
    }

    @Override
    public void editAccountPedFail(Integer code, String toastMessage) {
        WonderfulCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
