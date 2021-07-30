package com.uratio.testdemo.arithmetic;

import android.os.Build;

import com.uratio.testdemo.Utils.LogUtils;
import com.uratio.testdemo.Utils.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author lang
 * @data 2021/7/20
 */
public class ExecuteUtils {

    /**
     * 描述
     * 设计LRU缓存结构，该结构在构造时确定大小，假设大小为K，并有如下两个功能
     * set(key, value)：将记录(key, value)插入该结构
     * get(key)：返回key对应的value值
     * [要求]
     * set和get方法的时间复杂度为O(1)
     * 某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的。
     * 当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
     * 若opt=1，接下来两个整数x, y，表示set(x, y)
     * 若opt=2，接下来一个整数x，表示get(x)，若x未出现过或已被移除，则返回-1
     * 对于每个操作2，输出一个答案
     * <p>
     * 示例1
     * 输入：[[1,1,1],[1,2,2],[1,3,2],[2,1],[1,4,4],[2,2]],3
     * 返回值：[1,-1]
     * 说明：
     * 第一次操作后：最常使用的记录为("1", 1)
     * 第二次操作后：最常使用的记录为("2", 2)，("1", 1)变为最不常用的
     * 第三次操作后：最常使用的记录为("3", 2)，("1", 1)还是最不常用的
     * 第四次操作后：最常用的记录为("1", 1)，("2", 2)变为最不常用的
     * 第五次操作后：大小超过了3，所以移除此时最不常使用的记录("2", 2)，加入记录("4", 4)，并且为最常使用的记录，然后("3", 2)变为最不常使用的记录
     *
     * @param operators
     * @param k
     * @return
     */
    public static int[] LRU(int[][] operators, int k) {
        int count = 0;
        for (int[] operator : operators) {
            if (operator[0] == 2) {
                count++;
            }
        }
        int[] results = new int[count];
        count = 0;

        CusLruCache cache = new CusLruCache(k);
        for (int[] operator : operators) {
            switch (operator[0]) {
                case 1:
                    cache.set(operator[1], operator[2]);
                    break;
                case 2:
                    Integer value = cache.get(operator[1]);
                    results[count++] = value == null ? -1 : value;
                    break;
            }
        }
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            map.getOrDefault("key", -1);
        }
        ArrayList<Integer> list = new ArrayList<>();
//        Stack
        return results;
    }

    /**
     * 给定一个数组arr，返回arr的最长无重复元素子数组的长度，无重复指的是所有数字都不相同。
     * 子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
     * <p>
     * 备注： 1 < n < 10^5
     * <p>
     * 示例1
     * 输入：[2,3,4,5]
     * 返回值：4
     * 说明：[2,3,4,5]是最长子数组
     */
    public static int maxLength(int[] arr) {
        /**
         * 法一：
         * 循环：不存在存入集合中
         *      存在，说明有了产生了子数组，就对比出前后最大数量差值并记录，
         *      移除集合中第一项，再继续循环，直到移除了与当前重复的元素为止。
         * 最后输出 前后最大数量差值 和 历史子数组最大长度 中最大值
         */
//        Set<Integer> set = new HashSet<>();
//        int i = 0, j = 0;
//        int count = 0;
//        while (j < arr.length) {
//            if (!set.contains(arr[j])) {
//                set.add(arr[j]);
//                j++;
//            } else {
//                count = Math.max(count, j - i);
//                set.remove(arr[i++]);
//            }
//        }
//        return Math.max(count, j - i);

        /**
         * 法二：（备注了 0 < n < 100000）
         * 新建长度为 100000 的数组，
         * 循环：
         * 使用 arr[i] 作为 end[] 的索引，并比较end[arr[i]]与start值大小（有重复：则start会重新被赋值）
         * 在对比 重复位置 和 当前位置 的差值 ，用差值与历史长度比较，得出最大长度
         * 将索引值赋给 end[arr[i]]
         */

        int result = 0, start = 0;
        int[] end = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            if (start < end[num]) {
                start = end[num];
            }
            if (result < i - start + 1) {
                result = i - start + 1;
            }
            end[num] = i + 1;
        }
        return result;
    }

    /**
     * 给定一棵二叉树(保证非空)以及这棵树上的两个节点对应的val值 o1 和 o2，请找到 o1 和 o2 的最近公共祖先节点。
     * 注：本题保证二叉树中每个节点的val值均不相同。
     * <p>
     * 示例1
     * 输入：[3,5,1,6,2,0,8,#,#,7,4],5,1
     * 返回值：3
     */
    public static int lowestCommonAncestor(TreeNode root, int o1, int o2) {
        return CommonAncestor(root, o1, o2).val;
    }

    public static TreeNode CommonAncestor(TreeNode root, int o1, int o2) {
        if (root == null || root.val == o1 || root.val == o2) { // 超过叶子节点，或者root为p、q中的一个直接返回
            return root;
        }
        TreeNode left = CommonAncestor(root.left, o1, o2); // 返回左侧的p\q节点
        TreeNode right = CommonAncestor(root.right, o1, o2); // 返回右侧的p\q节点
        if (left == null) {  // 都在右侧
            return right;
        }
        if (right == null) { // 都在左侧
            return left;
        }
        return root; // 在左右两侧
    }

    /**
     * 给定一个数组，找出其中最小的K个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
     * 0 <= k <= input.length <= 10000
     * 0 <= input[i] <= 10000
     * <p>
     * 示例1
     * 输入：[4,5,1,6,2,7,3,8],4
     * 返回值：[1,2,3,4]
     * 说明：返回最小的4个数即可，返回[1,3,2,4]也可以
     * <p>
     * 示例2
     * 输入：[1],0
     * 返回值：[]
     */
    public static ArrayList<Integer> getLeastNumbers(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k == 0) return list;
        // 法一：以input每个value作为 record 中的 index，并记录对应index的个数，在循环取出前面 k 个值
        int[] record = new int[10000];
        for (int i = 0; i < input.length; i++) {
            record[input[i]]++;
        }
        for (int i = 0; i < 10000; i++) {
            if (record[i] != 0) {
                while (k > 0 && record[i] > 0) {
                    list.add(i);
                    k--;
                    record[i]--;
                }
            }
        }

        // 法二：先排序，在取值
//        for(int i=0;i<k;i++){
//            int min = input[i];
//            int minIndex = i;
//            for(int j=i+1;j<input.length;j++){
//                if(min>input[j]){
//                    min = input[j];
//                    minIndex = j;
//                }
//
//            }
//            input[minIndex] = input[i];
//            input[i] = min;
//        }
//        for(int i=0;i<k;i++){
//            list.add(input[i]);
//        }
        return list;
    }

    /**
     * 给出一个整数数组 A 和有序的整数数组 B，请将数组 B 合并到数组 A 中，变成一个有序的升序数组
     * 注意：
     * 1.可以假设 A 数组有足够的空间存放 B 数组的元素， A 和 B 中初始的元素数目分别为 m和 n，的数组空间大小为 m+n
     * 2.不要返回合并的数组，返回是空的，将数组 的数据合并到 A 里面就好了
     * 3. A 数组在[0,m-1]的范围也是有序的
     *
     * 例1:
     * A: [1,2,3,0,0,0]，m=3
     * B: [2,5,6]，n=3
     * 合并过后A为:
     * A: [1,2,2,3,5,6]
     */
    public static int[] mergeTwoArray(int A[], int m, int B[], int n) {
//        int len = m + n - 1;
//        int i = m - 1;
//        int j = n - 1;
//        while (i >= 0 && j >= 0) {
//            if (A[i] > B[j]) {
//                A[len--] = A[i--];
//            } else {
//                A[len--] = B[j--];
//            }
//        }
//        while (i >= 0) {
//            A[len--] = A[i--];
//        }
//        while (j >= 0) {
//            A[len--] = B[j--];
//        }

        int sum = m + n - 1;
        m--;
        n--;
        while(n >= 0 && m >= 0){
            if(A[m] > B[n]){
                A[sum] = A[m];
                m--;
            }else{
                A[sum] = B[n];
                n--;
            }
            sum--;
        }
        while(n >= 0){
            A[sum] = B[n];
            sum--;
            n--;
        }
        return A;
    }

    public static int[][] threeOrders(TreeNode root) {
        // write code here
        int[][] res = new int[3][];

        firstPrint(root);
        res[0] = toInt();
        list.clear();

        middlePrint(root);
        res[1] = toInt();
        list.clear();

        endPrint(root);
        res[2] = toInt();
        list.clear();

        return res;
    }

    private static ArrayList<Integer> list = new ArrayList<>();

    private static int[] toInt(){
        int[] res = new int[list.size()];
        for(int i=0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }

    private static void firstPrint(TreeNode node) {
        if(node != null) {
            list.add(node.val);
            firstPrint(node.left);
            firstPrint(node.right);
        }
    }

    private static void middlePrint(TreeNode node) {
        if(node != null) {
            middlePrint(node.left);
            list.add(node.val);
            middlePrint(node.right);
        }
    }

    private static void endPrint(TreeNode node) {
        if(node != null) {
            endPrint(node.left);
            endPrint(node.right);
            list.add(node.val);
        }
    }

    public static int[] quickSort2(int[] arr,int low,int high) {
        if (low < high) {
            int pivot = partition2(arr, low, high);
            quickSort2(arr, low, pivot - 1);
            quickSort2(arr, pivot + 1, high);
        }
        return arr;
    }

    private static int partition2(int[] arr,int low,int high) {
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] > pivot) {
                --high;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

    /**
     * 有一个整数数组，请你根据快速排序的思路，找出数组中第K大的数。
     *
     * 给定一个整数数组a,同时给定它的大小n和要找的K(1<=K<=n)，请返回第K大的数(包括重复的元素，不用去重)，保证答案存在。
     *
     * 示例1
     * 输入：[1,3,5,2,2],5,3
     * 返回值：2
     */
    public static int findKth(int[] a, int n, int K) {
        int[] arr = quickSort2(a, 0, n-1);
        LogUtils.e("arr=" + Arrays.toString(arr));
        return arr[arr.length - K];
    }
}
