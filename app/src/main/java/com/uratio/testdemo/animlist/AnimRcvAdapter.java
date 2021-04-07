package com.uratio.testdemo.animlist;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uratio.testdemo.R;

import java.util.List;

/**
 * @author lang
 * @data 2021/3/19
 */
public class AnimRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private List<HCMessage> list;
    private LayoutInflater inflater;
    private AnimRcvListener listener;
    private EditAbleListAdapterListener mListener;

    public boolean isHasFocus = false;
    public String compileText = "";

    public AnimRcvAdapter(Activity context, List<HCMessage> list, AnimRcvListener listener,
                          EditAbleListAdapterListener mListener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.mListener = mListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAnimRcvListener(AnimRcvListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = inflater.inflate(R.layout.item_anim_rcv_top, parent, false);
            return new TopViewHolder(view);
        } else if (viewType == 1) {
            view = inflater.inflate(R.layout.item_anim_rcv_question, parent, false);
            return new QViewHolder(view);
        } else if (viewType == 2) {
            view = inflater.inflate(R.layout.item_anim_rcv_answer, parent, false);
            return new AViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_anim_rcv_bottom, parent, false);
            return new BottomViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HCMessage message = list.get(position);
        if (holder instanceof QViewHolder) {
            ((QViewHolder) holder).initViewData(position, message);
        } else if (holder instanceof AViewHolder) {

        }
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
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public void addData() {
        int size = list.size() - 1;
        HCMessage message = new HCMessage();
        message.setType(size % 2);
        message.setContent((size + 1) + "");
        list.add(size, message);

        notifyItemInserted(size);
    }

    public void addData2() {
        int size = list.size();
        HCMessage message = new HCMessage();
        message.setType(size % 2 + 1);
        message.setContent((size + 1) + "");
        list.add(size, message);

        notifyItemInserted(size);
    }

    public void addData(int position) {
        HCMessage message = new HCMessage();
        message.setType(position % 2);
        message.setContent(position + "");
        list.add(position - 1, message);
        notifyItemInserted(position);
    }

    public void removeData() {
        int index = list.size() - 1;
        list.remove(index);
        notifyItemRemoved(index);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class QViewHolder extends RecyclerView.ViewHolder {
        EditText etQuestion;
        TextView tvHint;
        Button btnSend;

        TxtWatcher mTxtWatcher;

        public QViewHolder(@NonNull View itemView) {
            super(itemView);
            etQuestion = itemView.findViewById(R.id.et_question);
            tvHint = itemView.findViewById(R.id.tv_hint);
            btnSend = itemView.findViewById(R.id.btn_send);


            mTxtWatcher = new TxtWatcher();
        }

        public void initViewData(int position, HCMessage message) {
            etQuestion.setText("有没有适合我的理财\t" + message.getContent());

            mTxtWatcher.buildWatcher(position, etQuestion);

            etQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    isHasFocus = hasFocus;
                    if (hasFocus) {
                        etQuestion.addTextChangedListener(mTxtWatcher);
                    } else {
                        etQuestion.removeTextChangedListener(mTxtWatcher);
                    }
                    etQuestion.setTextColor(context.getResources().getColor(hasFocus ? R.color.color_FFFFFF :
                            R.color.color_7B7B8C));
                    etQuestion.setBackgroundResource(hasFocus ? R.drawable.shape_question_text_bg : 0);
                    btnSend.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
                    tvHint.setVisibility(!hasFocus ? View.VISIBLE : View.INVISIBLE);
                    if (listener != null) {
                        listener.onClickQuestion(position, hasFocus);
                    }

                }
            });
            etQuestion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    compileText = s.toString();
                }
            });
            tvHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSoftInputFromWindow(etQuestion);
                }
            });
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickSend(etQuestion.getText().toString());
                    }
                }
            });
//            tvHint.setVisibility(!message.isCanEdit() ? View.VISIBLE : View.GONE);
//            itemView.animate().setDuration(0);
        }
    }

    class AViewHolder extends RecyclerView.ViewHolder {

        public AViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {

        public BottomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface AnimRcvListener {
        void onClickQuestion(int position, boolean hasFocus);
        void onClickSend(String text);
    }

    public interface EditAbleListAdapterListener {
        void onEditTextChanged(int position, String value);
    }

    public class TxtWatcher implements TextWatcher {

        private int mPosition;
        private EditText editText;

        public void buildWatcher(int position, EditText editText) {
            this.mPosition = position;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                if (mListener != null) {
                    mListener.onEditTextChanged(mPosition, s.toString());
                    editText.setText(String.valueOf(list.get(mPosition).getContent()));
                }
            } else {
                if (mListener != null) {
                    mListener.onEditTextChanged(mPosition, "");
                    editText.setText("");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
