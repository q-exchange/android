package top.biduo.exchange.ui.ieo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import org.angmarch.views.NiceSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.biduo.exchange.R;
import top.biduo.exchange.adapter.IeoOrderRecordAdapter;
import top.biduo.exchange.base.BaseActivity;
import top.biduo.exchange.customview.DropdownLayout;
import top.biduo.exchange.data.DataSource;
import top.biduo.exchange.data.RemoteDataSource;
import top.biduo.exchange.entity.IeoOrderRecordBean;
import top.biduo.exchange.utils.WonderfulCodeUtils;
import top.biduo.exchange.utils.WonderfulToastUtils;

public class IeoOrderRecordActivity extends BaseActivity {

    @BindView(R.id.rc_ieo_order_record)
    RecyclerView rc_record;
    private IeoOrderRecordAdapter adapter;
    List<IeoOrderRecordBean> mData = new ArrayList<>();
    private int pageNum = 1;
    String pageSize = "20";
    String userName="";
    String userMobile="";
    String status="";//状态 (0-失败，1-成功)
    @BindView(R.id.llTitle)
    ViewGroup llTitle;
    @BindView(R.id.dropdown_layout)
    DropdownLayout dropdown_layout;
    @BindView(R.id.line_2)
    LinearLayout line_2;
    @BindView(R.id.et_ieo_name)
    EditText et_ieo_name;
    @BindView(R.id.sp_ieo_status)
    NiceSpinner sp_ieo_status;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @OnClick(R.id.tv_start_time)
    public void showTimePickerStart(TextView tv_start_time){
        showTimePickerView(tv_start_time);
    }
    @OnClick(R.id.tv_end_time)
    public void showTimePickerEnd(TextView tv_end_time){
        showTimePickerView(tv_end_time);
    }
    @OnClick(R.id.tv_reset)
    public void resetFilter(){
        clearFilters();
    }
    @OnClick(R.id.tv_confirm)
    public void confirmFilter(){
        getIeoOrderRecord();
        hideFilter();
    }

    private void hideFilter() {
        dropdown_layout.hide();
        line_2.setVisibility(View.GONE);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_filter_no);
        iv_filter.setImageDrawable(drawable);
    }

    @OnClick(R.id.ibBack)
    public void onBackClick(){
        finish();
    }
    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_ieo_order_record;
    }
    @OnClick(R.id.iv_filter)
    public void showFilter(ImageView iv_filter){
        if (dropdown_layout.isOpen()) {
            hideFilter();
        } else {
            showFilter();
        }
    }

    @OnClick(R.id.view_zhe)
    public void cancelFilter(){
        hideFilter();
    }

    private void showFilter() {
        dropdown_layout.show();
        line_2.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_filter_orange);
        iv_filter.setImageDrawable(drawable);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rc_record.setLayoutManager(manager);
        adapter = new IeoOrderRecordAdapter(R.layout.adapter_ieo_order_record, mData);
        adapter.isFirstOnly(true);
        rc_record.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IeoOrderDetailActivity.actionStart(IeoOrderRecordActivity.this,mData.get(position));
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum++;
                getIeoOrderRecord();
            }
        }, rc_record);

        //方向选择（买0，卖1）
        sp_ieo_status.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.text_ieo_order_status))));
        sp_ieo_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    status = "";
                    sp_ieo_status.setText("");
                } else {
                    status = (position - 1) + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_ieo_status.setText("");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {
        getIeoOrderRecord();
    }

    private void getIeoOrderRecord() {
        String ieoName=et_ieo_name.getText().toString();
        String startTime=tv_start_time.getText().toString();
        String endTime=tv_end_time.getText().toString();
        RemoteDataSource.getInstance().getIeoOrderRecoed(pageNum + "", pageSize, "", "", ieoName, status,
                startTime, endTime, getToken(), new DataSource.DataCallback() {
                    @Override
                    public void onDataLoaded(Object obj) {
                        List<IeoOrderRecordBean> list= (List<IeoOrderRecordBean>) obj;
                        updateRecyclerView(list);
                    }

                    @Override
                    public void onDataNotAvailable(Integer code, String toastMessage) {
                        WonderfulCodeUtils.checkedErrorCode(IeoOrderRecordActivity.this,code,toastMessage);
                    }
                });
    }

    private void updateRecyclerView(List<IeoOrderRecordBean> data) {
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        if (data == null) {
            return;
        }
        if (pageNum == 1) {
            mData.clear();
            if (data.size() == 0) {
                adapter.loadMoreEnd();
            } else {
                mData.addAll(data);
            }
        } else {
            if (data.size() != 0) {
                mData.addAll(data);
            } else {
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            ImmersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }

    public void showTimePickerView(final TextView tvTime) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, 5, 1, 0, 0, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(System.currentTimeMillis());
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                tvTime.setText(df.format(date));
            }
        }).setType(new boolean[]{true, true, true, true, false, false})
                .setCancelText(getResources().getString(R.string.cancle))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.confirm))//确认按钮文字
                .setSubmitColor(Color.parseColor("#0b0b0b"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#787878"))//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .build();
        pvTime.show();
    }

    private void clearFilters() {
        status="";
        sp_ieo_status.setText("");
        et_ieo_name.setText("");
        tv_start_time.setText("");
        tv_end_time.setText("");
    }

}
