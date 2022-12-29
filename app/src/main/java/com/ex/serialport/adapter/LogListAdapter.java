package com.ex.serialport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.serialport.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author
 * Created Time 2017/12/14.
 */
public class LogListAdapter extends RecyclerView.Adapter<LogListAdapter.VH> {

    private final BaseAdapterHelper adapterHelper;
    private List<String> mData = new ArrayList<>();

    public LogListAdapter(List<String> list) {
        if(list != null) {
            this.mData = list;
        }
        adapterHelper = new BaseAdapterHelper();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH vh = new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
        adapterHelper.bindListener(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mData.size() > position) {
            final String item = mData.get(position);
            holder.mTvName.setText(item);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

    public void addData(String text) {
        mData.add(text);
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return mData;
    }

    public void clean() {
        mData.clear();
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView mTvName;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.textView);
        }
    }

}
