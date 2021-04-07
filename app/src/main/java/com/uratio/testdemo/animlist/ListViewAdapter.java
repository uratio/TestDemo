package com.uratio.testdemo.animlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.uratio.testdemo.R;

import java.util.List;

/**
 * @author lang
 * @data 2021/3/17
 */
public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list;
    private LayoutInflater inflater;

    public ListViewAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_anim_list_view, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {

        public ViewHolder(@NonNull View itemView) {
//            itemView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.list_anim));
        }
    }
}
