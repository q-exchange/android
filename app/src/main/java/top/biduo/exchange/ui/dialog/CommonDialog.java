package top.biduo.exchange.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import top.biduo.exchange.R;
import top.biduo.exchange.ui.setting.SettingActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;

public class CommonDialog {

    public static AlertDialog createDialogInput(Activity activity, String title, String message, String buttonTextYes, String buttonTextNo, boolean showLeftButton, final OnCommitClickListener listener) {
        View contentView = View.inflate(activity, R.layout.dialog_common, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity,R.style.custom_dialog).setView(contentView).create();
        dialog.show();
        ((TextView) contentView.findViewById(R.id.tv_dialog_title)).setText(title);
        final EditText et_dialog_message= (EditText) contentView.findViewById(R.id.et_dialog_message);
        et_dialog_message.setHint(message);
        et_dialog_message.setVisibility(View.VISIBLE);
        ((TextView) contentView.findViewById(R.id.tv_dialog_yes)).setText(buttonTextYes);
        if (!showLeftButton) {
            contentView.findViewById(R.id.tv_dialog_no).setVisibility(View.GONE);
            contentView.findViewById(R.id.dialog_middle_line).setVisibility(View.GONE);
        }else {
            ((TextView) contentView.findViewById(R.id.tv_dialog_no)).setText(buttonTextNo);
            contentView.findViewById(R.id.tv_dialog_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contentView.findViewById(R.id.tv_dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCommitClick(et_dialog_message.getText().toString());
            }
        });
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int)(display.getWidth() * 0.7);
        dialog.getWindow().setAttributes(layoutParams);
        return dialog;
    }

    public static AlertDialog getInstance(Activity activity, String title, String message, String buttonTextYes, String buttonTextNo, boolean showLeftButton, View.OnClickListener listener) {
        View contentView = View.inflate(activity, R.layout.dialog_common, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity,R.style.custom_dialog).setView(contentView).create();
        dialog.show();
        ((TextView) contentView.findViewById(R.id.tv_dialog_title)).setText(title);
        if(!TextUtils.isEmpty(message)){
            final EditText et_dialog_message= (EditText) contentView.findViewById(R.id.et_dialog_message);
            et_dialog_message.setText(message);
            et_dialog_message.setInputType(InputType.TYPE_CLASS_TEXT);
            et_dialog_message.setSingleLine(false);
            et_dialog_message.setMaxLines(10);
            et_dialog_message.setEnabled(false);
        }
        ((TextView) contentView.findViewById(R.id.tv_dialog_yes)).setText(buttonTextYes);
        if (!showLeftButton) {
            contentView.findViewById(R.id.tv_dialog_no).setVisibility(View.GONE);
            contentView.findViewById(R.id.dialog_middle_line).setVisibility(View.GONE);
        }else {
            ((TextView) contentView.findViewById(R.id.tv_dialog_no)).setText(buttonTextNo);
            contentView.findViewById(R.id.tv_dialog_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        contentView.findViewById(R.id.tv_dialog_yes).setOnClickListener(listener);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int)(display.getWidth() * 0.7);
        layoutParams.height=(int)(display.getHeight()*0.9);
        dialog.getWindow().setAttributes(layoutParams);
        return dialog;


    }


    public interface OnCommitClickListener{
        void onCommitClick(String pass);
    }
}