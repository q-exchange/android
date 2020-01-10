package top.biduo.exchange.ui.recharge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.app.UrlFactory;
import top.biduo.exchange.ui.common.ChooseCoinActivity;
import top.biduo.exchange.ui.wallet.ChongBiJLActivity;
import top.biduo.exchange.utils.SharedPreferenceInstance;
import top.biduo.exchange.utils.WonderfulBitmapUtils;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulCommonUtils;
import top.biduo.exchange.utils.WonderfulLogUtils;
import top.biduo.exchange.utils.WonderfulStringUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;
import top.biduo.exchange.utils.okhttp.StringCallback;
import top.biduo.exchange.utils.okhttp.WonderfulOkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Request;

public class RechargeActivity extends BaseActivity {

    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAddressText)
    TextView tvAddressText;
    @BindView(R.id.ivAddress)
    ImageView ivAddress;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.llAlbum)
    LinearLayout llAlbum;
    @BindView(R.id.llCopy)
    LinearLayout llCopy;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    private String coinName;
    private Bitmap saveBitmap;
    @BindView(R.id.view_back)
    View view_back;
    @OnClick(R.id.ibRegist)
    public void startChargeRecord(){
        ChongBiJLActivity.actionStart(this,null);
    }
    private String address;

    public static void actionStart(Context context, String coinName) {
        Intent intent = new Intent(context, RechargeActivity.class);
        intent.putExtra("coinName", coinName);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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
        llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copy();
            }
        });
        llAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    @Override
    protected void obtainData() {
        coinName = getIntent().getStringExtra("coinName");
        tvTitle.setText(coinName + getResources().getString(R.string.chargeMoney));
        tvAddressText.setText(coinName  );
        huoqu();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        obtainData();
    }

    private void save() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ATC/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        if (saveBitmap != null) {
            try {
                WonderfulBitmapUtils.saveBitmapToFile(saveBitmap, file, 100);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        WonderfulToastUtils.showToast(getResources().getString(R.string.savesuccess));
    }

    private void huoqu() {
        WonderfulOkhttpUtils.post().url(UrlFactory.getWalletUrl()).addHeader("x-auth-token", SharedPreferenceInstance.getInstance().getTOKEN()).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                super.onError(request, e);
                hideLoadingPopup();
            }

            @Override
            public void onResponse(String response) {
                hideLoadingPopup();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 0) {
                        List<Coin> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<Coin>>() {
                        }.getType());
                        for (int i = 0; i < objs.size(); i++) {
                            Coin coin1 = objs.get(i);
                            if (coinName.equals(coin1.getCoin().getUnit())) {
                                address = (coin1.getAddress());
                                break;
                            }
                        }
                        if (TextUtils.isEmpty(address)) {
                            WonderfulToastUtils.showToast(getResources().getString(R.string.creating_address));
                        } else {
                            erciLoad();
                        }
                    } else {
                        WonderfulCodeUtils.checkedErrorCode(RechargeActivity.this, object.optInt("code"), object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void copy() {
        WonderfulCommonUtils.copyText(this, tvAddress.getText().toString());
        WonderfulToastUtils.showToast(R.string.copy_success);
    }


    @Override
    protected void fillWidget() {

    }

    private void erciLoad() {
        if (tvTitle == null) {
            return;
        }

        if (TextUtils.isEmpty(address)) {
            tvAddress.setText(getResources().getString(R.string.unChargeMoneyTip1));
        }else{
            tvAddress.setText(address);
            ivAddress.post(new Runnable() {
                @Override
                public void run() {
                    saveBitmap = createQRCode(address, Math.min(ivAddress.getWidth(), ivAddress.getHeight()));
                    ivAddress.setImageBitmap(saveBitmap);
                }
            });
        }
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

    public static Bitmap createQRCode(String text, int size) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 2);   //设置白边大小 取值为 0- 4 越大白边越大
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick(R.id.ll_choose_coin)
    public void startChooseCoin(){
        ChooseCoinActivity.actionStart(this, ChooseCoinActivity.TYPE_CHARGE);

    }


}
