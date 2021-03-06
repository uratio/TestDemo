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
                    ListNode node;
                    switch (tag) {
                        case "????????????":
                            String addStr = Utils.addLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "???et2=" + et2.getText().toString() + "???????????????" + addStr);
                            break;
                        case "????????????":
                            String s1 = compareToStr(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "???et2=" + et2.getText().toString() + "???????????????" + s1);
                            break;
                        case "????????????":
                            String mulStr = Utils.multiplyLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "???et2=" + et2.getText().toString() + "???????????????" + mulStr);
                            break;
                        case "???????????????????????????????????????":
                            int target = Integer.parseInt(et1.getText().toString());
                            boolean result = Utils.isExist(array, target);
                            tvShow.setText("et1=" + et1.getText().toString() + "???????????????????????????" + result);
                            break;
                        case "????????????????????????":
                            String s = Utils.subtractLargeNumbers(et1.getText().toString(), et2.getText().toString());
                            tvShow.setText("et1=" + et1.getText().toString() + "???et2=" + et2.getText().toString() + "???????????????" + s);
                            break;
                        case "???????????????":
                            /**
                             * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                             * ??????????????????????????????{1,2,4,7,3,5,6,8}?????????????????????{4,7,2,1,5,3,8,6}?????????????????????????????????
                             * ??????1
                             *      ??????      [1,2,3,4,5,6,7],[3,2,4,1,6,5,7]
                             *      ?????????     {1,2,5,3,4,6,7}
                             */
                            treeNode = Utils.reConstructBinaryTree(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2,
                                    1, 5, 3, 8, 6});
                            tvShow.setText("????????????????????????" + treeArr(treeNode, true));
                            break;
                        case "????????????":
                            node = new ListNode(1);
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
                            tvShow.setText("?????????????????????" + sb.toString());
                            break;
                        case "??????????????????????????????":
                            node = new ListNode(2);
                            node.next = new ListNode(3);
                            node.next.next = new ListNode(5);
                            node.next.next.next = new ListNode(4);
                            node.next.next.next.next = new ListNode(1);
                            node.next.next.next.next.next = new ListNode(6);
                            node.next.next.next.next.next.next = new ListNode(3);
                            node.next.next.next.next.next.next.next = new ListNode(7);
                            ListNode result2 = Utils.deleteRepeat(node, 4);
                            StringBuilder sb2 = new StringBuilder();
                            while (result2 != null) {
                                sb2.append(result2.val);
                                result2 = result2.next;
                            }
                            tvShow.setText("?????????????????????????????????" + sb2.toString());
                            break;
                        case "??????????????????":
                            str = et1.getText().toString();
                            LogUtils.e("111111");
                            int res1 = Utils.lengthOfLastWord(str);
                            LogUtils.e("******");
                            LogUtils.e("222222");
                            int res2 = Utils.lengthOfLastWord2(str);
                            LogUtils.e("******");
                            tvShow.setText("????????????????????????" + res1);
                            break;
                        case "??????????????????":
                            new Thread(new TestRun(), "1").start();
                            new Thread(new TestRun(), "2").start();
                            new Thread(new TestRun(), "3").start();
                            break;
                        case "??????????????????2":
                            new TestPrint().printNum();
                            break;
                        case "?????????????????????2?????????":
                            LogUtils.e("5=" + tableSizeFor(5));
                            LogUtils.e("6=" + tableSizeFor(6));
                            LogUtils.e("7=" + tableSizeFor(7));
                            LogUtils.e("8=" + tableSizeFor(8));
                            LogUtils.e("9=" + tableSizeFor(9));
                            LogUtils.e("11=" + tableSizeFor(11));
                            LogUtils.e("12=" + tableSizeFor(12));
                            LogUtils.e("13=" + tableSizeFor(13));
                            break;
                        case "??????hash???":
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
                        case "??????LRU????????????":
                            int[] lru = ExecuteUtils.LRU(new int[][]{{1, 1, 1}, {1, 2, 2}, {1, 3, 2}, {2, 1}, {1, 4, 4}, {2, 2}}, 3);
                            LogUtils.e("lru[]=" + Arrays.toString(lru));
                            break;
                        case "????????????????????????":
                            int maxLength = ExecuteUtils.maxLength(new int[]{2, 2, 3, 4, 3});
                            LogUtils.e("??????????????????????????????=" + maxLength);
                            break;
                        case "??????????????????????????????????????????????????????":
                            treeNode = Utils.createNode(new int[]{3, 5, 1, 6, 2, 0, 8, -1, -1, 7, 4});
                            LogUtils.e("??????????????????=" + ExecuteUtils.lowestCommonAncestor(treeNode, 2, 8));
                            break;
                        case "?????????K??????":
                            ArrayList<Integer> leastNumbers = ExecuteUtils.getLeastNumbers(new int[]{4, 5, 1, 6, 2, 7, 3, 8}, 4);
                            LogUtils.e("?????????K??????=" + leastNumbers.toString());
                            break;
                        case "???????????????????????????":
                            arr = ExecuteUtils.mergeTwoArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0}, 8,
                                    new int[]{10, 14, 16}, 3);
                            LogUtils.e("A??????=" + Arrays.toString(arr));
                            break;
                        case "?????????????????????????????????????????????":
                            treeNode = Utils.createNode(new int[]{44,18,10,22,1,38,2,40,-1,41,9,7,-1,14,21,-1,34,30,27,3,39,23,35,6,-1,17,25,36,-1,-1,19,16,-1,-1,12,20,11,-1,8,-1,28,24,-1,29,13,-1,37,31,-1,-1,-1,32,-1,-1,-1,4,-1,-1,-1,42,-1,-1,-1,15,43,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,33,-1,26});
                            int[][] ints = ExecuteUtils.threeOrders(treeNode);
                            LogUtils.e("???????????????????????????????????????????????????=" + Utils.toList(ints));
                            break;
                        case "????????????":
                            int[] quickArr = {9,3,5,4,6,2,7,8};
                            int[] quickRes = ExecuteUtils.quickSort2(quickArr, 0, quickArr.length - 1);
                            LogUtils.e("??????????????????=" + Arrays.toString(quickRes));
                            break;
                        case "?????????K???":
                            arr = new int[]{1332802,1177178,1514891,871248,753214,123866,1615405,328656,1540395,968891,
                                1884022,252932,1034406,1455178,821713,486232,860175,1896237,852300,566715,1285209,
                                    1845742,883142,259266,520911,1844960,218188,1528217,332380,261485,1111670,16920,
                                    1249664,1199799,1959818,1546744,1904944,51047,1176397,190970,48715,349690,673887,
                                    1648782,1010556,1165786,937247,986578,798663};
                            LogUtils.e("?????????K?????????=" + ExecuteUtils.findKth(arr, arr.length, 24));
                            break;
                        case "?????????????????????????????????":
                            arr = new int[]{1, -2, 3, 5, -2, 6, -1};
                            LogUtils.e("?????????????????????????????????=" + ExecuteUtils.maxSumOfSubArray(arr));
                            break;
                        case "???????????????":
                            LogUtils.e("?????????????????????=" + new StringBuilder("fsafdsad").reverse().toString());
                            break;
                        case "??????????????????":
//                            str = "abc1234321ab";
                            str = "1234562";
                            LogUtils.e("???????????????????????????=" + ExecuteUtils.getLongestPalindrome(str, str.length()));
                            break;
                        case "???????????????":
                            str = "3+2*3*4-1";
                            LogUtils.e("??????????????????" + str + "=" + ExecuteUtils.solve(str));
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
