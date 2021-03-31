package com.uratio.testdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uratio.pikers.util.LogUtils;
import com.uratio.testdemo.Utils.ListNode;
import com.uratio.testdemo.Utils.TreeNode;
import com.uratio.testdemo.Utils.Utils;
import com.uratio.testdemo.thread.TestPrint;
import com.uratio.testdemo.thread.TestRun;

import java.util.HashMap;

public class ArithmeticActivity extends AppCompatActivity {
    private EditText et1, et2;
    private TextView tvShow;
    private int[][] array = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21,
            23, 26, 30}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tvShow = findViewById(R.id.tvShow);
    }

    public void clickCompare(View view) {
        switch (view.getId()) {
            case R.id.add:
                String addStr = Utils.addLargeNumbers(et1.getText().toString(), et2.getText().toString());
                tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "相加结果：" + addStr);
                break;
            case R.id.subtract:
                String s1 = compareToStr(et1.getText().toString(), et2.getText().toString());
                tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "对比结果：" + s1);
                break;
            case R.id.multiply:
                String mulStr = Utils.multiplyLargeNumbers(et1.getText().toString(), et2.getText().toString());
                tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "相乘结果：" + mulStr);
                break;
            case R.id.compareNum:
                int target = Integer.parseInt(et1.getText().toString());
                boolean result = Utils.isExist(array, target);
                tvShow.setText("et1=" + et1.getText().toString() + "是否在二维数组中：" + result);
                break;
            case R.id.compareTo:
                String s = Utils.subtractLargeNumbers(et1.getText().toString(), et2.getText().toString());
                tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "计算结果：" + s);
                break;
            /**
             * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
             * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
             * 示例1
             *      输入      [1,2,3,4,5,6,7],[3,2,4,1,6,5,7]
             *      返回值     {1,2,5,3,4,6,7}
             */
            case R.id.rebuild_treeNode:
                TreeNode treeNode = Utils.reConstructBinaryTree(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2,
                        1, 5, 3, 8, 6});
                tvShow.setText("重建二叉树结果：" + treeArr(treeNode, true));
                break;
            /**
             * 翻转链表
             */
            case R.id.reverse_linked_list:
                ListNode node = new ListNode(1);
                node.next = new ListNode(2);
                node.next.next = new ListNode(3);
                node.next.next.next = new ListNode(4);
                node.next.next.next.next = new ListNode(5);
                ListNode res = Utils.reverseKGroup(node, 4);
                StringBuilder sb = new StringBuilder();
                ListNode temp = res;
                while (temp != null) {
                    sb.append(temp.val);
                    temp = temp.next;
                }
                tvShow.setText("翻转链表结果：" + sb.toString());
                break;
            case R.id.last_word_length:
                String str = et1.getText().toString();
                System.out.println("111111");
                int res1 = Utils.lengthOfLastWord(str);
                System.out.println("******");
                System.out.println("222222");
                int res2 = Utils.lengthOfLastWord2(str);
                System.out.println("******");
                tvShow.setText("最后单词长度为：" + res1);
                break;
            case R.id.thread_print:
                new Thread(new TestRun(), "1").start();
                new Thread(new TestRun(), "2").start();
                new Thread(new TestRun(), "3").start();
                break;
            case R.id.thread_print2:
                new TestPrint().printNum();
                break;
            case R.id.table_size_for:
                System.out.println("5=" + tableSizeFor(5));
                System.out.println("6=" + tableSizeFor(6));
                System.out.println("7=" + tableSizeFor(7));
                System.out.println("8=" + tableSizeFor(8));
                System.out.println("9=" + tableSizeFor(9));
                System.out.println("11=" + tableSizeFor(11));
                System.out.println("12=" + tableSizeFor(12));
                System.out.println("13=" + tableSizeFor(13));
                break;
            case R.id.btn_hash:
                System.out.println("1 << 1=" + (1 << 1));
                System.out.println("1 << 2=" + (1 << 2));
                System.out.println("2^17=" + (1 << 17));
                System.out.println("2^17=" + hash(1 << 17));
                System.out.println("2^16+1=" + (1 << 16 + 1));
                System.out.println("2^16+1=" + hash(1 << 16 + 1));
                System.out.println("2^16=" + (1 << 16));
                System.out.println("2^16=" + hash(1 << 16));
                System.out.println("2^16-1=" + (1 << 16 - 1));
                System.out.println("2^16-1=" + hash(1 << 16 - 1));
                System.out.println("2^15=" + (1 << 15));
                System.out.println("2^15=" + hash(1 << 15));
                break;
        }
    }

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * Returns a power of two size for the given target capacity.
     */
    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private String compareToStr(String s1, String s2) {
        int compare = s1.compareTo(s2);
        if (compare == 0) {
            return "0";
        } else if (compare < 0) {
            return "-";
        } else {
            return "+";
        }
    }

    private String treeArr(TreeNode node, boolean isMid) {
        StringBuilder sb = new StringBuilder();
        if (isMid) sb.append(node.val);
        if (node != null) {
            if (node.left != null) {
                sb.append(node.left.val);
            }
            if (node.right != null) {
                sb.append(node.right.val);
            }
            if (node.left != null) {
                sb.append(treeArr(node.left, false));
            }
            if (node.right != null) {
                sb.append(treeArr(node.right, false));
            }
        }
        return sb.toString();
    }
}
