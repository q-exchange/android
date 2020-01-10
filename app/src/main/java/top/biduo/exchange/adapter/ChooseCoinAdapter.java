package top.biduo.exchange.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import top.biduo.exchange.R;
import top.biduo.exchange.entity.Coin;
import top.biduo.exchange.utils.ScreenUtils;

public class ChooseCoinAdapter extends RecyclerView.Adapter<ChooseCoinAdapter.ViewHolder> {

    private Context mContext;
    private List<Coin> mList;
    private OnItemClickListener mOnItemClickListener;
    int itemHeight=0;

    public ChooseCoinAdapter(Context context, List<Coin> list) {
        this.mContext = context;
        this.mList = list;
        itemHeight= ScreenUtils.dip2px(context,40);
    }

    @NonNull
    @Override
    public ChooseCoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_textview_coin, parent, false);
        ChooseCoinAdapter.ViewHolder viewHolder = new ChooseCoinAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChooseCoinAdapter.ViewHolder holder, int position) {
        String text = mList.get(position).getCoin().getUnit();
        holder.mTextView.setText(text);
        final View view = holder.itemView;
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClick(v, holder.getAdapterPosition());
                }
            });
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(!TextUtils.isEmpty(mKeyword)&&!mList.get(position).getCoin().getUnit().toUpperCase().startsWith(mKeyword.toUpperCase())){
            lp.height=0;
        }else{
            lp.height=itemHeight;
        }
        view.setLayoutParams(lp);
    }

    private void setOnItemClick(View v, int position) {
        getOnItemClickListener().onItemClick(this, v, position);
    }

    private OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    /**
     * 判断position对应的Item是否是组的第一项
     *
     * @param position
     * @return
     */
    public boolean isItemHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            String lastGroupName = mList.get(position - 1).getCoin().getUnit().substring(0, 1);
            String currentGroupName = mList.get(position).getCoin().getUnit().substring(0, 1);
            //判断上一个数据的组别和下一个数据的组别是否一致，如果不一致则是不同组，也就是为第一项（头部）
            if (lastGroupName.equals(currentGroupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 获取position对应的Item组名
     *
     * @param position
     * @return
     */
    public String getGroupName(int position) {
        return mList.get(position).getCoin().getUnit().substring(0, 1);
    }


    /**
     * 自定义ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_text);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(ChooseCoinAdapter adapter, View view, int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    String mKeyword="";
    public void setKeyword(String keyword){
        mKeyword=keyword;
        notifyDataSetChanged();
    }

}