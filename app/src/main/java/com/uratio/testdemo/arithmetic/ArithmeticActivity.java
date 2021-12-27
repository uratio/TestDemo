package com.uratio.testdemo.arithmetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uratio.testdemo.R;
import com.uratio.testdemo.utils.ListNode;
import com.uratio.testdemo.utils.LogUtils;
import com.uratio.testdemo.utils.TreeNode;
import com.uratio.testdemo.utils.Utils;
import com.uratio.testdemo.animlist.FlowLayout;
import com.uratio.testdemo.thread.TestPrint;
import com.uratio.testdemo.thread.TestRun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArithmeticActivity extends AppCompatActivity {
    private EditText et1, et2;
    private TextView tvShow;
    private FlowLayout flowLayout;
    private int[][] array = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21,
            23, 26, 30}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tvShow = findViewById(R.id.tvShow);
        flowLayout = findViewById(R.id.flow_layout);

        List<String> list = ArithmeticConfig.getList();
        for (int i = 0; i < list.size(); i++) {
            View view = View.inflate(this, R.layout.item_flow_layout_arithmetic, null);
            Button button = view.findViewById(R.id.btn);
            button.setText(list.get(i));
            button.setTag(list.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    TreeNode treeNode = null;
                    int[] arr = null;
                    String str = "";
                    switch (tag) {
                        case "计算合值":
                            String addStr = Utils.addLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "相加结果：" + addStr);
                            break;
                        case "计算差值":
                            String s1 = compareToStr(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "对比结果：" + s1);
                            break;
                        case "计算相乘":
                            String mulStr = Utils.multiplyLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "相乘结果：" + mulStr);
                            break;
                        case "判断输入值是否在二维数组中":
                            int target = Integer.parseInt(et1.getText().toString());
                            boolean result = Utils.isExist(array, target);
                            tvShow.setText("et1=" + et1.getText().toString() + "是否在二维数组中：" + result);
                            break;
                        case "对比输入数值大小":
                            String s = Utils.subtractLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "与et2=" + et2.getText().toString() + "计算结果：" + s);
                            break;
                        case "重建二叉树":
                            /**
                             * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
                             * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
                             * 示例1
                             *      输入      [1,2,3,4,5,6,7],[3,2,4,1,6,5,7]
                             *      返回值     {1,2,5,3,4,6,7}
                             */
                            treeNode = Utils.reConstructBinaryTree(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2,
                                    1, 5, 3, 8, 6});
                            tvShow.setText("重建二叉树结果：" + treeArr(treeNode, true));
                            break;
                        case "翻转链表":
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
                        case "最后单词长度":
                            str = et1.getText().toString();
                            LogUtils.e("111111");
                            int res1 = Utils.lengthOfLastWord(str);
                            LogUtils.e("******");
                            LogUtils.e("222222");
                            int res2 = Utils.lengthOfLastWord2(str);
                            LogUtils.e("******");
                            tvShow.setText("最后单词长度为：" + res1);
                            break;
                        case "线程顺序打印":
                            new Thread(new TestRun(), "1").start();
                            new Thread(new TestRun(), "2").start();
                            new Thread(new TestRun(), "3").start();
                            break;
                        case "线程顺序打印2":
                            new TestPrint().printNum();
                            break;
                        case "计算容量最近的2的幂数":
                            LogUtils.e("5=" + tableSizeFor(5));
                            LogUtils.e("6=" + tableSizeFor(6));
                            LogUtils.e("7=" + tableSizeFor(7));
                            LogUtils.e("8=" + tableSizeFor(8));
                            LogUtils.e("9=" + tableSizeFor(9));
                            LogUtils.e("11=" + tableSizeFor(11));
                            LogUtils.e("12=" + tableSizeFor(12));
                            LogUtils.e("13=" + tableSizeFor(13));
                            break;
                        case "计算hash值":
                            LogUtils.e("1 << 1=" + (1 << 1));
                            LogUtils.e("1 << 2=" + (1 << 2));
                            LogUtils.e("2^17=" + (1 << 17));
                            LogUtils.e("2^17=" + hash(1 << 17));
                            LogUtils.e("2^16+1=" + (1 << 16 + 1));
                            LogUtils.e("2^16+1=" + hash(1 << 16 + 1));
                            LogUtils.e("2^16=" + (1 << 16));
                            LogUtils.e("2^16=" + hash(1 << 16));
                            LogUtils.e("2^16-1=" + (1 << 16 - 1));
                            LogUtils.e("2^16-1=" + hash(1 << 16 - 1));
                            LogUtils.e("2^15=" + (1 << 15));
                            LogUtils.e("2^15=" + hash(1 << 15));
                            break;
                        case "设计LRU缓存结构":
                            int[] lru = ExecuteUtils.LRU(new int[][]{{1, 1, 1}, {1, 2, 2}, {1, 3, 2}, {2, 1}, {1, 4, 4}, {2, 2}}, 3);
                            LogUtils.e("lru[]=" + Arrays.toString(lru));
                            break;
                        case "最长无重复子数组":
                            int maxLength = ExecuteUtils.maxLength(new int[]{2, 2, 3, 4, 3});
                            LogUtils.e("最长无重复子数组长度=" + maxLength);
                            break;
                        case "在二叉树中找到两个节点的最近公共祖先":
                            treeNode = Utils.createNode(new int[]{3, 5, 1, 6, 2, 0, 8, -1, -1, 7, 4});
                            LogUtils.e("最近公共祖先=" + ExecuteUtils.lowestCommonAncestor(treeNode, 2, 8));
                            break;
                        case "最小的K个数":
                            ArrayList<Integer> leastNumbers = ExecuteUtils.getLeastNumbers(new int[]{4, 5, 1, 6, 2, 7, 3, 8}, 4);
                            LogUtils.e("最小的K个数=" + leastNumbers.toString());
                            break;
                        case "合并两个有序的数组":
                            arr = ExecuteUtils.mergeTwoArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0}, 8,
                                    new int[]{10, 14, 16}, 3);
                            LogUtils.e("A数组=" + Arrays.toString(arr));
                            break;
                        case "实现二叉树先序，中序和后序遍历":
                            treeNode = Utils.createNode(new int[]{44,18,10,22,1,38,2,40,-1,41,9,7,-1,14,21,-1,34,30,27,3,39,23,35,6,-1,17,25,36,-1,-1,19,16,-1,-1,12,20,11,-1,8,-1,28,24,-1,29,13,-1,37,31,-1,-1,-1,32,-1,-1,-1,4,-1,-1,-1,42,-1,-1,-1,15,43,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,33,-1,26});
                            int[][] ints = ExecuteUtils.threeOrders(treeNode);
                            LogUtils.e("实现二叉树先序，中序和后序遍历结果=" + Utils.toList(ints));
                            break;
                        case "快速排序":
                            int[] quickArr = {9,3,5,4,6,2,7,8};
                            int[] quickRes = ExecuteUtils.quickSort2(quickArr, 0, quickArr.length - 1);
                            LogUtils.e("快速排序结果=" + Arrays.toString(quickRes));
                            break;
                        case "寻找第K大":
                            arr = new int[]{1332802,1177178,1514891,871248,753214,123866,1615405,328656,1540395,968891,
                                1884022,252932,1034406,1455178,821713,486232,860175,1896237,852300,566715,1285209,
                                    1845742,883142,259266,520911,1844960,218188,1528217,332380,261485,1111670,16920,
                                    1249664,1199799,1959818,1546744,1904944,51047,1176397,190970,48715,349690,673887,
                                    1648782,1010556,1165786,937247,986578,798663};
                            LogUtils.e("寻找第K大结果=" + ExecuteUtils.findKth(arr, arr.length, 24));
                            break;
                        case "子数组的最大累加和问题":
                            arr = new int[]{1, -2, 3, 5, -2, 6, -1};
                            LogUtils.e("子数组的最大累加和结果=" + ExecuteUtils.maxSumOfSubArray(arr));
                            break;
                        case "反转字符串":
                            LogUtils.e("反转字符串结果=" + new StringBuilder("fsafdsad").reverse().toString());
                            break;
                        case "最长回文子串":
//                            str = "abc1234321ab";
                            str = "1234562";
                            LogUtils.e("最长回文子串的长度=" + ExecuteUtils.getLongestPalindrome(str, str.length()));
                            break;
                        case "表达式求值":
                            str = "3+2*3*4-1";
                            LogUtils.e("表达式求值：" + str + "=" + ExecuteUtils.solve(str));
                            break;
                    }
                }
            });
            flowLayout.addView(view);
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
