package com.uratio.testdemo.arithmetic;

import android.os.Build;

import com.uratio.testdemo.Utils.LogUtils;
import com.uratio.testdemo.Utils.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
     * <p>
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
        while (n >= 0 && m >= 0) {
            if (A[m] > B[n]) {
                A[sum] = A[m];
                m--;
            } else {
                A[sum] = B[n];
                n--;
            }
            sum--;
        }
        while (n >= 0) {
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

    private static int[] toInt() {
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private static void firstPrint(TreeNode node) {
        if (node != null) {
            list.add(node.val);
            firstPrint(node.left);
            firstPrint(node.right);
        }
    }

    private static void middlePrint(TreeNode node) {
        if (node != null) {
            middlePrint(node.left);
            list.add(node.val);
            middlePrint(node.right);
        }
    }

    private static void endPrint(TreeNode node) {
        if (node != null) {
            endPrint(node.left);
            endPrint(node.right);
            list.add(node.val);
        }
    }

    public static int[] quickSort2(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition2(arr, low, high);
            quickSort2(arr, low, pivot - 1);
            quickSort2(arr, pivot + 1, high);
        }
        return arr;
    }

    private static int partition2(int[] arr, int low, int high) {
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
     * <p>
     * 给定一个整数数组a,同时给定它的大小n和要找的K(1<=K<=n)，请返回第K大的数(包括重复的元素，不用去重)，保证答案存在。
     * <p>
     * 示例1
     * 输入：[1,3,5,2,2],5,3
     * 返回值：2
     */
    public static int findKth(int[] a, int n, int K) {
        int[] arr = quickSort2(a, 0, n - 1);
        LogUtils.e("arr=" + Arrays.toString(arr));
        return arr[arr.length - K];
    }

    /**
     * 给定一个数组arr，返回子数组的最大累加和
     * 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
     * 题目保证没有全为负数的数据
     * [要求]
     * 时间复杂度为O(n)O(n)，空间复杂度为O(1)O(1)
     * <p>
     * 示例1
     * 输入：[1, -2, 3, 5, -2, 6, -1]
     * 返回值：12
     * <p>
     * 解读：
     * 从头到尾扫描一遍，累加，如果累加到0或者负数，说明前面的和对于后面的和没有正向贡献，因此累加重新开始，
     * 没有减到0，说明对后面的还有正向贡献，因此继续累加。
     * 注意要分别记录已经产生的最大累加值和当前累加值
     */
    public static int maxSumOfSubArray(int[] arr) {
//         int maxSum = 0;
//         int sum = 0;
//         int start = 0;
//         for(int i=0;i<arr.length;i++){
//             if(sum <= sum + arr[i]) {
//                 sum = sum + arr[i];
//                 maxSum = sum > maxSum ? sum : maxSum;
//             } else {
//                 sum = 0;
//                 start++;
//                 i = start;
//             }
//         }
//         return maxSum;

        if (arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        int result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);
            result = Math.max(result, dp[i]);
        }
        return result;
    }

    /**
     * 最长回文子串：（轴对称的字符串）
     * 对于一个字符串，请设计一个高效算法，计算其中最长回文子串的长度。
     * 给定字符串A以及它的长度n，请返回最长回文子串的长度。
     *
     * 示例1
     * 输入："abc1234321ab",12
     * 回文子串有：343，23432，1234321
     * 返回值：7
     *
     * 动态规划：
     * 如果 A的下标 i 到 j 是回文串，则 dp[i][j] = true ；否则 dp[i][j] = false
     * 则 dp[i][j] = dp[i+1][j-1] && A[i] == A[j]
     * 则 要知道 dp[i][j] 的情况，只需要知道 dp[i+1][j-1] 的情况就可以
     *    如果 dp[i+1}[j-1] 是回文串，则 A[i] == A[j] 就可以确定 dp[i][j] 是回文串
     *    如果 dp[i+1}[j-1] 不是回文串，则 dp[i][j] 不可能为回文串，因为回文串需要是轴对称的字符串
     *
     * 注意：刨除两种情况，长度为 1 和 2 的 dp[i][j] 不能使用上面公式，需要单独那出来
     *       比如 dp[1][2]，我们需要知道 dp[1+1][2-1] = dp[2][1] 的值，显然这样不对，则单独判断
     *
     * @param A 字符串
     * @param n 字符串A的长度
     * @return
     */
    public static int getLongestPalindrome(String A, int n) {
        // write code here
        int maxLen = 0;
        String ans = "";
        boolean[][] P = new boolean[n][n];
        // 遍历所有长度（）
        for (int len = 1; len <= n; len++) {
            for (int start = 0; start < n; start++) {
                int end = start + len - 1;
                // 下标越界，结束循环
                if (end >= n) {
                    break;
                }
                P[start][end] = (len == 1 || len == 2 || P[start + 1][end - 1]) && A.charAt(start) == A.charAt(end);
                if (P[start][end] && len > maxLen) {
                    maxLen = len;
//                     ans = s.substring(start, end + 1);
                }
            }
        }
        return maxLen;


        /**
         * 中心扩展算法
         * 遍历长度，以每个 i 作为中心，查询最大回文子串长度（奇数）
         *          以每个 i 到 i+1 作为中心，查询最大回文子串长度（偶数）
         *          每次都对比并记录最大长度
         */
//        char[] chars = A.toCharArray();
//
//        int res = 0;
//        for (int i = 1; i < n; i++) {
//            res = Math.max(res, getCount(chars, i, i));
//            res = Math.max(res, getCount(chars, i, i + 1));
//        }
//        return res;
    }

    private static int getCount(char[] chars, int l, int r){
        while (l>=0 && r<chars.length && chars[l] == chars[r]){
            l--;
            r++;
        }
        return r - l - 1;
    }

    /**
     * 表达式求值
     * 请写一个整数计算器，支持加减乘三种运算和括号。
     *
     * 示例1
     * 输入："1+2"
     * 返回值：3
     *
     * 示例2
     * 输入："(2*(3-4))*5"
     * 返回值：-10
     *
     * 示例3
     * 输入："3+2*3*4-1"
     * 返回值：26
     *
     * 解读：
     * 1. 去空格；
     * 2. 遇到 '('：直接入opts栈；
     * 3. 遇到 ')'：将操作符栈中的符号都使用了，直到遇到 '('， 将 '('弹出；
     * 4. 遇到数字：直接入nums栈；
     * 5. 遇到操作符：先把opts栈中优先级比它高的操作符处理完，或者直到遇见 '('，再将它入栈；
     */
    private static Map<String, Integer> level = new HashMap<String, Integer>(){
        {
            put("(", 0);
            put("+", 1);
            put("-", 1);
            put("*", 2);
            put("/", 2);
            put(")", 3);
        }
    };

    public static int solve (String s) {
        // write code here
        if(s == null || s.length() == 0) return 0;

        Stack<String> oStack = new Stack<>();
        Stack<Integer> nStack = new Stack<>();
        StringBuilder nSB = new StringBuilder();

        for(int i=0;i<s.length();i++) {
            if(s.charAt(i) == ' ') continue;
            char c = s.charAt(i);

            if ((c >= '0' && c <= '9') || c == '.') {
                // 读取每一个数字
                nSB.append(c);
            } else {
                // 读取非数字 字符 时:
                if (nSB.length() > 0) {
                    // 将数字字符串加入 数字栈 中
                    nStack.push(Integer.valueOf(nSB.toString()));
                    // 清空数字字符容器，以便下次记录新数字串
                    nSB.delete(0, nSB.length());
                }

                String cStr = String.valueOf(c);
                if (oStack.empty()) {
                    oStack.push(cStr);
                } else {
                    switch (c) {
                        default:
                            // 当前运算符为加减乘除之一，要与栈顶运算符比较，判断是否要进行一次二元计算
                            compareAndCalc(oStack, nStack, cStr);
                            break;
                        case '(':
                            oStack.push(cStr);
                            break;
                        case ')':
                            calcBeforeNum(oStack, nStack, true);
                            break;
                        case '=':
                            calcBeforeNum(oStack, nStack, false);
                            // 当前运算符为等号，触发整个表达式剩余计算，并返回总的计算结果
                            return nStack.pop().intValue();
                    }
                }
            }
        }

        if (nSB.length() > 0) {
            nStack.push(Integer.valueOf(nSB.toString()));
        }
        calcBeforeNum(oStack, nStack, false);
        return nStack.pop().intValue();
    }

    private static void compareAndCalc(Stack<String> oStack, Stack<Integer> nStack, String cStr) {
        String peek = oStack.peek();
        if (level.get(cStr) > level.get(peek)) {
            oStack.push(cStr);
        } else {
            String pop = oStack.pop();
            Integer num2 = nStack.pop();
            Integer num1 = nStack.pop();

            nStack.push(calcResult(pop, num1, num2));

            if (oStack.empty()) {
                oStack.push(cStr);
            } else {
                compareAndCalc(oStack, nStack, cStr);
            }
        }
    }

    private static void calcBeforeNum(Stack<String> oStack, Stack<Integer> nStack, boolean isBracket) {
        String pop = oStack.pop();
        Integer num2 = nStack.pop();
        Integer num1 = nStack.pop();
        int calcResult = calcResult(pop, num1, num2);

        nStack.push(calcResult);

        if (isBracket) {
            if ("(".equals(oStack.peek())) {
                oStack.pop();
            } else {
                calcBeforeNum(oStack, nStack, true);
            }
        } else {
            if (!oStack.empty()) {
                calcBeforeNum(oStack, nStack, false);
            }
        }
    }

    private static int calcResult(String opt, int num1, int num2) {
        switch (opt) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                return 0;
        }
    }
}
