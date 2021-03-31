package com.uratio.testdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lang
 * @data 2020/6/22
 */
public class RcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public RcvAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rcv_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).rcvItem.setLayoutManager(new LinearLayoutManager(context));
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add("i=" + i);
            }
            RcvItemAdapter adapter = new RcvItemAdapter(context, list);
            ((ViewHolder) holder).rcvItem.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rcvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvItem = itemView.findViewById(R.id.rcv_item);
        }
    }
}
