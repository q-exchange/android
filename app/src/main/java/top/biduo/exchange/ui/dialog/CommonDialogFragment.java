package top.biduo.exchange.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import top.biduo.exchange.R;
import top.biduo.exchange.utils.SharedPreferenceInstance;

/**
 * User:Iks_ruan
 * <p>
 * date:2019/5/18
 */

public class CommonDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final byte TYPE_GOOGLE_CODE=10;//谷歌验证码
    public static final byte TYPE_DEFALUT=11;//默认
    public static final byte TYPE_LOGINOUT=12;//退出登陆

    private String title;
    private String message;
    private String btnOk;
    private String btnNo;
    private boolean isShowLeftBtn;
    private byte currentType=TYPE_DEFALUT;
    private OnCommitClickListener commitClickListener;
    private EditText et_dialog_message;

    public static CommonDialogFragment getInstance(byte currentType,String title,String message,String btnOk,String btnNo,boolean isShowLeftBtn){
        CommonDialogFragment dialogFragment=new CommonDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putByte("type",currentType);
        bundle.putString("title",title);
        bundle.putString("message",message);
        bundle.putString("btnOk",btnOk);
        bundle.putString("btnNo",btnNo);
        bundle.putBoolean("isShowLeftBtn",isShowLeftBtn);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Bundle bundle=getArguments();
        if(bundle!=null){
            currentType=bundle.getByte("type");
            title=bundle.getString("title");
            message=bundle.getString("message");
            btnNo=bundle.getString("btnNo");
            btnOk=bundle.getString("btnOk");
            isShowLeftBtn=bundle.getBoolean("isShowLeftBtn");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.custom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    private View initView(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_common, null);
        TextView title=view.findViewById(R.id.tv_dialog_title);
        title.setText(this.title);
        TextView btnOk=view.findViewById(R.id.tv_dialog_yes);
        btnOk.setText(this.btnOk);
        if (!isShowLeftBtn) {
            view.findViewById(R.id.tv_dialog_no).setVisibility(View.GONE);
            view.findViewById(R.id.dialog_middle_line).setVisibility(View.GONE);
        }else {
            ((TextView) view.findViewById(R.id.tv_dialog_no)).setText(btnNo);
            view.findViewById(R.id.tv_dialog_no).setOnClickListener(this);
        }
        btnOk.setOnClickListener(this);
        et_dialog_message= view.findViewById(R.id.et_dialog_message);
        switch (currentType){
            case TYPE_DEFALUT:
                et_dialog_message.setVisibility(View.VISIBLE);
                et_dialog_message.setText(message);
                et_dialog_message.setInputType(InputType.TYPE_CLASS_TEXT);
                et_dialog_message.setSingleLine(false);
                et_dialog_message.setMaxLines(10);
                et_dialog_message.setEnabled(false);
                break;
            case TYPE_GOOGLE_CODE:
                et_dialog_message.setHint(message);
                et_dialog_message.setVisibility(View.VISIBLE);
                break;
            case TYPE_LOGINOUT:
                et_dialog_message.setVisibility(View.GONE);
                break;
        }
        return view;
    }

    public void setCommitClickListener(OnCommitClickListener commitClickListener) {
        this.commitClickListener = commitClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dialog_no:
                dismiss();
                break;
            case R.id.tv_dialog_yes:
                if(commitClickListener!=null){
                    switch (currentType){
                        case TYPE_DEFALUT:
                            commitClickListener.onCommitClick("");
                            break;
                        case TYPE_GOOGLE_CODE:
                            commitClickListener.onCommitClick(et_dialog_message.getText().toString());
                            break;
                        case TYPE_LOGINOUT:
                            commitClickListener.onCommitClick("");
                            break;
                    }
                }
                dismiss();
                break;
        }
    }

    public interface OnCommitClickListener{
        void onCommitClick(String pass);
    }
}
