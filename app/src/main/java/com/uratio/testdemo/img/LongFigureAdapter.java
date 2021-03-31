package com.uratio.testdemo.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uratio.testdemo.R;

import java.util.List;

/**
 * @author lang
 * @data 2021/3/9
 */
public class LongFigureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Bitmap> list;
    private Context context;
    private LayoutInflater inflater;

    public LongFigureAdapter(List<Bitmap> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rcv_long_figure, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (list.size() < 1) return;
            ((ViewHolder) holder).ivItem.setImageBitmap(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.iv_item);
        }
    }
}
