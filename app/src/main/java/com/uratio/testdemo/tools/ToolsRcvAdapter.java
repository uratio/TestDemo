package com.uratio.testdemo.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uratio.testdemo.R;

import java.util.List;

/**
 * @author lang
 * @data 2021/8/26
 */
public class ToolsRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> data;
    private LayoutInflater inflater;
    private OnClickItemListener listener;

    public ToolsRcvAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rcv_tools, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).button.setText(data.get(position));
            if (listener != null) {
                ((ViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClickItem(position, data.get(position));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }

    public interface OnClickItemListener{
        void onClickItem(int position, String type);
    }
}
