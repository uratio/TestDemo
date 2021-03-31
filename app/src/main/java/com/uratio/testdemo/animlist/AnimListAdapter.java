package com.uratio.testdemo.animlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AnimListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HCMessage> list;
    private AnimRcvListener listener;
    private LayoutInflater inflater;

    public AnimListAdapter(Context mContext, List<HCMessage> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAnimRcvListener(AnimRcvListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData() {
        int size = list.size() - 1;
        HCMessage message = new HCMessage();
        message.setType(size % 2);
        message.setContent((size +1)+"");
        list.add(size, message);

        notifyDataSetChanged();
    }

    public void removeData() {
        list.remove(list.size() - 1);
        notifyDataSetChanged();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
//        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            QViewHolder vh;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_anim_list_question, parent, false);
                vh = new QViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (QViewHolder) convertView.getTag();
            }

            HCMessage message = list.get(position);
            vh.etQuestion.setText("有没有适合我的理财\t" + message.getContent());

            vh.etQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
//                    isHasFocus = hasFocus;
                    vh.etQuestion.setTextColor(mContext.getResources().getColor(hasFocus ? R.color.color_FFFFFF : R.color.color_666666));
                    vh.etQuestion.setBackgroundResource(hasFocus ? R.drawable.shape_question_text_bg : 0);
                    vh.btnSend.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
                    vh.tvHint.setVisibility(!hasFocus ? View.VISIBLE : View.GONE);
                    if (hasFocus && listener != null) {
                        listener.onClickQuestion(position);
                    }
                }
            });
            vh.tvHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSoftInputFromWindow(vh.etQuestion);
//                    if (listener != null) {
//                        listener.onClickQuestion(position);
//                    }
                }
            });
            convertView.animate().setDuration(0);

        } else if (viewType == 1){
            AViewHolder vh;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_anim_list_answer, parent, false);
                vh = new AViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (AViewHolder) convertView.getTag();
            }


        } else {
            BottomViewHolder vh;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_anim_list_bottom, parent, false);
                vh = new BottomViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (BottomViewHolder) convertView.getTag();
            }

        }

        return convertView;
    }

    class QViewHolder {
        EditText etQuestion;
        TextView tvHint;
        Button btnSend;

        public QViewHolder(@NonNull View itemView) {
            etQuestion = itemView.findViewById(R.id.et_question);
            tvHint = itemView.findViewById(R.id.tv_hint);
            btnSend = itemView.findViewById(R.id.btn_send);
        }
    }

    class AViewHolder {

        public AViewHolder(@NonNull View itemView) {

        }
    }

    class BottomViewHolder {

        public BottomViewHolder(@NonNull View itemView) {

        }
    }

    public interface AnimRcvListener {
        void onClickQuestion(int position);
    }
}
