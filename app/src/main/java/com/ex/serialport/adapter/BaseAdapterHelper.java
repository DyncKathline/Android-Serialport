package com.ex.serialport.adapter;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashSet;

public class BaseAdapterHelper {

    private OnItemClickListener onItemClickListener;//单击事件
    private OnItemLongClickListener onItemLongClickListener;//长按单击事件
    private OnItemChildClickListener onItemChildClickListener;//单击事件
    private OnItemChildLongClickListener onItemChildLongClickListener;//长按单击事件
    /**
     * {@link BaseAdapterHelper#bindListener(RecyclerView.ViewHolder)}方法中调用
     */
    private OnBindViewListener onBindViewListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 调用该方法之前需要调用{@link BaseAdapterHelper#addChildClickViewIds (...viewIds)}
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 调用该方法之前需要调用{@link BaseAdapterHelper#addChildLongClickViewIds (...viewIds)}
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.onItemChildLongClickListener = onItemChildLongClickListener;
    }

    public void setBindViewListener(OnBindViewListener onBindViewListener) {
        this.onBindViewListener = onBindViewListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }

    public interface OnItemChildClickListener {
        void onClick(View v, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onLongClick(View v, int position);
    }

    public interface OnBindViewListener {
        void onBind(RecyclerView.ViewHolder holder);
    }

    /**
     * 用于保存需要设置点击事件的 item
     */
    private LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet<>();

    public LinkedHashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    /**
     * 设置需要点击事件的子view
     * @param viewIds IntArray
     */
    public void addChildClickViewIds(@IdRes int ...viewIds) {
        for (int i = 0; i < viewIds.length; i++) {
            int viewId = viewIds[i];
            childClickViewIds.add(viewId);
        }
    }

    /**
     * 用于保存需要设置长按点击事件的 item
     */
    private LinkedHashSet<Integer> childLongClickViewIds = new LinkedHashSet<>();

    public LinkedHashSet<Integer> getChildLongClickViewIds() {
        return childLongClickViewIds;
    }

    /**
     * 设置需要长按点击事件的子view
     * @param viewIds IntArray
     */
    public void addChildLongClickViewIds(@IdRes int ...viewIds) {
        for (int i = 0; i < viewIds.length; i++) {
            int viewId = viewIds[i];
            childLongClickViewIds.add(viewId);
        }
    }

    /**
     * 在onCreateViewHolder方法中调用
     * @param holder
     */
    public void bindListener(final RecyclerView.ViewHolder holder) {
        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, holder.getBindingAdapterPosition());
                }
            });
        }
        if(onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongClick(v, holder.getBindingAdapterPosition());
                }
            });
        }
        if(onItemChildClickListener != null) {
            for (int viewId: getChildClickViewIds()) {
                View view = holder.itemView.findViewById(viewId);
                if(view == null) return;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!v.isClickable()) {
                            v.setClickable(true);
                        }
                        int position = holder.getBindingAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {
                            return;
                        }
                        onItemChildClickListener.onClick(v, position);
                    }
                });
            }
        }
        if(onItemChildLongClickListener != null) {
            for (int viewId: getChildLongClickViewIds()) {
                View view = holder.itemView.findViewById(viewId);
                if (view == null) return;
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!v.isLongClickable()) {
                            v.setLongClickable(true);
                        }
                        int position = holder.getAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {
                            return false;
                        }
                        return onItemChildLongClickListener.onLongClick(v, position);
                    }
                });
            }
        }
        if(onBindViewListener != null) {
            onBindViewListener.onBind(holder);
        }
    }
}
