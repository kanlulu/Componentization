package com.yql.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.yql.common.R;
import com.yql.common.utils.DialogUtils;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener, RecyclerViewHolder.OnItemViewClickListener {

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_HEADER = 3;
    public static final int TYPE_EMPTY = 4;

    private String footFinishContent;

    private OnItemClickListener onItemClickListener;
    private OnViewClickListener onViewClickListener;

    private LayoutInflater inflater;

    protected View headerView;
    protected View footerView;
    protected View emptyView;
    private TextView tvEmpty;
    private Button btnEmpty;

    protected List<T> datas;
    protected Context context;

    protected int headerViewCount;
    protected int footerViewCount;
    protected int itemRes;

    // 分页加载
    private int currentPage;//当前显示第几页
    private int pages;//总共的页数
    private int NUMS = 10;//每页显示数据条数
    private boolean noMore = false;//没有更多数据了
    private boolean isLoadingData = false;//正在加载数据，不要在请求了

    private MyTaskFinishListener myTaskFinishListener;

    public BaseRecyclerAdapter(Context context, List<T> datas, int itemLayoutResourse) {
        this.datas = datas;
        this.context = context;
        this.itemRes = itemLayoutResourse;
        inflater = LayoutInflater.from(context);
        myTaskFinishListener = new MyTaskFinishListener();
        footerView = inflater.inflate(R.layout.layout_footer, null);//这里默认就让每个recyclerview有footer
        emptyView = inflater.inflate(R.layout.layout_empty_view, null);//空数据显示的空布局
        tvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        btnEmpty = (Button) emptyView.findViewById(R.id.btn_empty);
        setFooterView(footerView);

        if (datas != null && datas.size() <= 0) DialogUtils.showProgressDialog("加载中");
    }

    //设置每页加载条数
    public void setNumsOnePage(int nums) {
        this.NUMS = nums;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_NORMAL:
                view = inflater.inflate(itemRes, parent, false);
                break;
            case TYPE_FOOTER:
                view = footerView;
                break;
            case TYPE_HEADER:
                view = headerView;
                break;
            case TYPE_EMPTY:
                view = emptyView;
                break;
            default:
                view = inflater.inflate(itemRes, parent, false);
                break;
        }
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        if (isEmpty()) {
            bindEmpty(holder);
        } else if (isFooter(position)) {
            bindFooter(holder);
        } else if (isHeader(position)) {
            bindHeader(holder);
        } else {
            final int index = position - headerViewCount;
            if (index < datas.size()) {
                bindViewHolder(index, holder);
                View itemView = holder.getItemView();
                final T data = datas.get(index);
                itemView.setTag(data);
            }
        }
    }

    public String TAG = "BaseRecyclerAdapter";

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size() + headerViewCount + footerViewCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return TYPE_EMPTY;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        } else if (isHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    public int getPosition(T data) {
        return datas.indexOf(data) + headerViewCount;
    }

    protected abstract void bindViewHolder(int position, RecyclerViewHolder holder);

    protected abstract void loadData(int start, int rows, ILoadFinish<T> iTaskFinishListener);

    protected void bindHeader(RecyclerViewHolder holder) {
        holder.getItemView().setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    protected void bindFooter(RecyclerViewHolder holder) {
        holder.getItemView().setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (!noMore && !isLoadingData && currentPage != 0) {
            isLoadingData = true;
            // currentPage 已经递增过了
//            loadData(currentPage + 1, NUMS, myTaskFinishListener);
            loadData(currentPage, NUMS, myTaskFinishListener);
        }
        if (noMore || datas.size() == 0) {
            holder.getItemView().setVisibility(View.GONE);//请将加载中改为没有更多数据了哦
            if (footerView != null) {
                TextView textView = (TextView) footerView.findViewById(R.id.footer_text_view);
                if (textView != null && footFinishContent != null) {
                    holder.getItemView().setVisibility(View.VISIBLE);
                    textView.setText(footFinishContent);
                    View view = footerView.findViewById(R.id.footer_pb_view);
                    if (view != null) {
                        view.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            holder.getItemView().setVisibility(View.VISIBLE);
            if (footerView != null) {
                holder.getItemView().setVisibility(View.VISIBLE);
                TextView textView = (TextView) footerView.findViewById(R.id.footer_text_view);
                if (textView != null) {
                    textView.setText("加载中");
                    View view = footerView.findViewById(R.id.footer_pb_view);
                    if (view != null) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    protected void bindEmpty(RecyclerViewHolder holder) {
        holder.getItemView().setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private class MyTaskFinishListener implements ILoadFinish<T> {

        @Override
        public void success(T object) {
            try {
                DialogUtils.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (object != null && object instanceof List) {
                List<T> list = (List<T>) object;
                if (list != null) {
                    if (list.size() < NUMS) {
                        noMore = true;
                        notifyDataSetChanged();
                    }

                    if (list.size() == 0 && currentPage == 0) {//第一页,没有数据时,清空Datas并刷新adapter
                        datas.clear();
                        notifyDataSetChanged();
                    } else if (list.size() > 0) {
                        currentPage++;

                        if (currentPage == 1) {
                            datas.clear();
                        }
                        datas.addAll(list);
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetChanged();
                    }
                }
            }
            isLoadingData = false;
            loadDataFinish();
        }

        @Override
        public void fail(String errorMsg) {
            try {
                DialogUtils.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isLoadingData = false;
            loadDataFinish();
        }
    }

    /**
     * 首次加载数据
     */
    public void loadDataFirstTime() {
        if (!isLoadingData) {
            noMore = false;
            currentPage = 0;
            loadData(0, NUMS, myTaskFinishListener);
            isLoadingData = true;

            DialogUtils.showProgressDialog("加载中");
        }
    }

    protected boolean isFooter(int position) {
        if (footerView == null) {
            return false;
        }
        return position == getItemCount() - 1;
    }

    protected boolean isHeader(int position) {
        if (headerView == null) {
            return false;
        }
        return position == 0;
    }

    protected boolean isEmpty() {
        if (emptyView == null) {
            return false;
        }

        if (!noMore) {
            return false;
        }
        return datas.size() <= 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public void setHeaderView(View headerView) {
        if (this.headerView == null) {
            if (headerView != null) {
                this.headerView = headerView;
                this.headerViewCount++;
                notifyItemInserted(0);
            }
        }
    }

    public void setFooterView(View footerView) {
        if (footerView != null) {
            this.footerView = footerView;
            this.footerViewCount = 1;
        }
    }

    public void setFooterFinishTextContent(String content) {
        this.footFinishContent = content;
    }

    public void removeHeaderView() {
        if (headerViewCount > 0) {
            this.headerView = null;
            this.headerViewCount--;
            this.notifyItemRemoved(0);
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, v.getTag());
        }
    }

    @Override
    public void onViewClick(View view) {
        if (onViewClickListener != null) {
            onViewClickListener.onViewClick(view, view.getTag());
        }
    }

    /**
     * 整个Item点击事件
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data);
    }

    /**
     * 整个Item中的子view设置点击事件
     * 注意：必须在Adapter中setTag，否则这里data没有数据，目前还没有更好的解决办法，如有请告知
     *
     * @param <T>
     */
    public interface OnViewClickListener<T> {
        void onViewClick(View view, T data);
    }

    public void setEmptyView(String emptyText, String btnText, View.OnClickListener btnClickListener) {
        if (emptyText != null)
            tvEmpty.setText(emptyText);
        if (btnClickListener != null)
            btnEmpty.setOnClickListener(btnClickListener);
        if (!TextUtils.isEmpty(btnText)) {
            btnEmpty.setVisibility(View.VISIBLE);
            btnEmpty.setText(btnText);
        } else {
            btnEmpty.setVisibility(View.GONE);
        }
    }

    private OnDataLoadFinish onDataLoadFinish;

    public void setOnDataLoadFinish(OnDataLoadFinish onDataLoadFinish) {
        this.onDataLoadFinish = onDataLoadFinish;
    }

    public interface OnDataLoadFinish {
        void loadDataFinished(int datasLength);
    }

    private void loadDataFinish() {
        if (onDataLoadFinish != null) {
            onDataLoadFinish.loadDataFinished(datas.size());
        }
    }
}
