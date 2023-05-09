package com.uratio.testdemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lang
 * @data 2023/5/4
 */
public class ArithmeticTest {

    /**
     * 3、无重复字符的最长子串
     *
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     *
     * 提示：
     *
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     */
    @Test
    public void lengthOfLongestSubstring() {
        String str = "pwwkew";
        System.out.println(lengthOfLongestSubstring1(str));
        System.out.println(lengthOfLongestSubstring2(str));
    }

    /**
     * 用时最少
     */
    private int lengthOfLongestSubstring1(String s) {
        int start = 0, end = 0;
        int length = 0, result = 0;
        int sSize = s.length();
        while (end < sSize) {
            char tmpChar = s.charAt(end);
            for (int index = start; index < end; index++) {
                if (s.charAt(index) == tmpChar) {
                    start = index + 1;
                    length = end - start;
                    break;
                }
            }
            end++;
            length++;
            result = result > length ? result : length;
        }

        return result;
    }

    /**
     * 自己写的
     */
    private int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.length() == 1) return 1;

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int left = 0;
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);

            result = Math.max(result, i - left + 1);
        }

        return result;
    }

    /**
     * 4、寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     *
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     *
     *
     * 示例 1：
     *
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 示例 2：
     *
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     */
    @Test
    public void findMedianSortedArrays() {
        int[] nums1 = new int[0];
        int[] nums2 = new int[]{2};
        System.out.println(findMedianSortedArrays1(nums1, nums2));
        System.out.println(findMedianSortedArrays2(nums1, nums2));
    }

    /**
     * leetcode官方解法，时间复杂度为 O(log(m+n))
     */
    private double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        int m = nums1.length;
        int n = nums2.length;

        //左侧满足条件的所有数
        int totalLeft = (m + n + 1) / 2;

        //在 nums1 区间 [0，m]里查找恰当的分割线
        //使得 nums1[i-1] <= nums2[j] && nums2[j-1] <= nums1[i]
        int left = 0;
        int right = m;

        while (left < right) {
            int i = left + (right - left + 1) / 2;
            int j = totalLeft - i;
            if (nums1[i - 1] > nums2[j]) {
                // 下一轮搜索区间[left, i-1]
                right = i - 1;
            } else {
                // 下一轮搜索区间[i, right]
                left = i;
            }
        }

        int i = left;
        int j = totalLeft - i;

        int nums1LeftMax = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = i == m ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = j == n ? Integer.MAX_VALUE : nums2[j];

        if ((m + n) % 2 == 1) {
            return Math.max(nums1LeftMax, nums2LeftMax);
        } else {
            return (double) (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2;
        }
    }

    /**
     * 此方法时间复杂度为 O(m+n) ，不是题中要求的 O(log(m+n))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] res = new int[m + n];
        int p1 = 0;
        int p2 = 0;
        int cur = 0;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                cur = nums2[p2++];
            } else if (p2 == n) {
                cur = nums1[p1++];
            } else if (nums1[p1] <= nums2[p2]) {
                cur = nums1[p1++];
            } else {
                cur = nums2[p2++];
            }
            res[p1 + p2 - 1] = cur;
        }
        if ((m + n) % 2 == 1) {
            return res[(m + n) / 2] * 1.0;
        } else {
            return (res[(m + n) / 2 - 1] + res[(m + n) / 2]) / 2.0;
        }
    }

    /**
     * 88、合并两个有序数组
     *
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     *
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     *
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     *
     *
     * 示例 1：
     *
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
     * 示例 2：
     *
     * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
     * 输出：[1]
     * 解释：需要合并 [1] 和 [] 。
     * 合并结果是 [1] 。
     * 示例 3：
     *
     * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
     * 输出：[1]
     * 解释：需要合并的数组是 [] 和 [1] 。
     * 合并结果是 [1] 。
     * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
     *
     *
     * 提示：
     *
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     *
     *
     * 进阶：你可以设计实现一个时间复杂度为 O(m + n) 的算法解决此问题吗？
     */
    @Test
    public void mergeIntArray() {
//        int[] res = merge(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
        int[] res = mergeIntArray1(new int[]{2, 0}, 1, new int[]{1}, 1);
        System.out.println(Arrays.toString(res));
    }

    private int[] mergeIntArray1(int[] nums1, int m, int[] nums2, int n) {
        int x = 0;
        int y = 0;
        int[] res = new int[m + n];
        int cur = 0;
        while (x < m || y < n) {
            if (x == m) {
                cur = nums2[y++];
            } else if (y == n) {
                cur = nums1[x++];
            } else if (nums1[x] <= nums2[y]) {
                cur = nums1[x++];
            } else {
                cur = nums2[y++];
            }

            res[x + y - 1] = cur;
        }
        for (int i = 0; i < m + n; i++) {
            nums1[i] = res[i];
        }
        return nums1;
    }

    /**
     * 5、最长回文子串
     *
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     *
     *
     * 示例 1：
     *
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * 示例 2：
     *
     * 输入：s = "cbbd"
     * 输出："bb"
     */
    @Test
    public void longestPalindrome() {
        String str = "babad";
        System.out.println(longestPalindrome1(str));
        System.out.println(longestPalindrome2(str));
    }

    /**
     * 动态规划 —— 方法求解
     * 时间复杂度为 O(n^2)
     *
     * 我们用 P(i,j)P(i,j)P(i,j) 表示字符串 sss 的第 iii 到 jjj 个字母组成的串（下文表示成 s[i:j]s[i:j]s[i:j]）是否为回文串：
     *
     * P(i,j) = true：如果子串 Si..Sj是回文串
     * P(i,j) = false：其他情况：①、s[i,j] 本身不是一个回文串
     * ②、i > j ，此时 s[i,j] 本身不合法
     *
     * 那么我们就可以写出动态规划的状态转移方程：
     * P(i,j)=P(i+1,j−1)∧(Si==Sj)
     * 也就是说，只有 s[i+1:j−1] 是回文串，并且 sss 的第 i 和 j 个字母相同时，s[i:j] 才会是回文串
     * 还需要考虑动态规划中的边界条件，即子串的长度为 1 或 2
     */
    public String longestPalindrome1(String s) {
        int length = s.length();
        if (length < 2) return s;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int left = 0;
        int len = 1;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }

        char[] charArr = s.toCharArray();

        for (int L = 2; L <= length; L++) {
            for (int i = 0; i < length; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= length) {
                    break;
                }

                if (charArr[i] != charArr[j]) {
                    dp[i][j] = false;
                } else {
                    // si 和 sj相同时
                    // j-i < 3时，dp[i][j]肯定是true，反之用动态规划公式求解
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > len) {
                    len = L;
                    left = i;
                }
            }
        }

        return s.substring(left, left + len);
    }

    /**
     * 最快，内存使用最少解
     * 时间复杂度为 O(n^2)
     */
    public String longestPalindrome2(String s) {
        String max = "";
        for (int i = 0; i < s.length(); ) {
            int start = i, end = i;
            // i 递增，从 start 向后找到最后相同的元素
            while (end < s.length() - 1 && s.charAt(end) == s.charAt(end + 1)) {
                end++;
            }
            i = end + 1;

            // 从上一步的 start 和 end 向前后扩展验证是否元素相同，直到不是回文串为止
            while (start > 0 && end < s.length() - 1 && s.charAt(start - 1) == s.charAt(end + 1)) {
                start--;
                end++;
            }
            // 对比每个回文串长度，并存储最大值
            int len = max.length();

            if (end - start + 1 >= len) {
                max = s.substring(start, end + 1);
            }
        }

        return max;
    }

    /**
     * 6、N 字形变换
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     *
     * 示例 1：
     *
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     * 示例 2：
     * 输入：s = "PAYPALISHIRING", numRows = 4
     * 输出："PINALSIGYAHRPI"
     * 解释：
     * P     I    N
     * A   L S  I G
     * Y A   H R
     * P     I
     * 示例 3：
     *
     * 输入：s = "A", numRows = 1
     * 输出："A"
     *
     * 提示：
     * 1 <= s.length <= 1000
     * s 由英文字母（小写和大写）、',' 和 '.' 组成
     * 1 <= numRows <= 1000
     */
    @Test
    public void convert() {
//        String str = "PAYPALISHIRING"; // 4
        String str = "ABC"; // 2
        System.out.println(convert1(str, 2));
        System.out.println(convert2(str, 2));
        System.out.println(convert3(str, 2));
    }

    private String convert1(String s, int numRows) {
        int len = s.length();
        if (len < 2 || numRows < 2) return s;

        int hLen = len / 2 + (len % 2 == 0 ? 0 : 1);
        char[][] dp = new char[numRows][hLen];
        int index = 0;
        for (int i = 0; i < hLen; i++) {
            int j = i % (numRows - 1);
            if (j == 0) {
                for (; j < numRows; j++) {
                    dp[j][i] = s.charAt(index++);

                    if (index == len) {
                        break;
                    }
                }
            } else {
                dp[numRows - j - 1][i] = s.charAt(index++);
            }
            if (index == len) {
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < hLen; j++) {
                if (dp[i][j] != 0) {
                    sb.append(dp[i][j]);
                }
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }
        return sb.toString();
    }

    /**
     * 官方解法，考虑周长
     */
    private String convert2(String s, int numRows) {
        int n = s.length(), r = numRows;
        if (r == 1 || r >= n) {
            return s;
        }
        int t = r * 2 - 2;
        int c = (n + t - 1) / t * (r - 1);
        char[][] mat = new char[r][c];
        for (int i = 0, x = 0, y = 0; i < n; ++i) {
            mat[x][y] = s.charAt(i);
            if (i % t < r - 1) {
                ++x; // 向下移动
            } else {
                --x;
                ++y; // 向右上移动
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (mat[i][j] != 0) {
                    sb.append(mat[i][j]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据规律依次变换坐标，填充 s 对应的索引的元素
     */
    private String convert3(String s, int numRows) {
        if (numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for (int i = 0; i < numRows; i++) rows.add(new StringBuilder());
        int i = 0, flag = -1;
        for (char c : s.toCharArray()) {
            rows.get(i).append(c);
            if (i == 0 || i == numRows - 1) flag = -flag;
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) res.append(row);
        return res.toString();
    }

    /**
     * 7、整数反转
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     */
    @Test
    public void reverse() {
        int x = 123;
//        int x = -123;
//        int x = 120;
        System.out.println(reverse1(x));
        System.out.println(reverse2(x));
    }

    public int reverse1(int x) {
        int res = 0;
        while(x != 0) {
            if(res < Integer.MIN_VALUE / 10 || res > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            res = res * 10 + digit;
        }
        return res;
    }

    public int reverse2(int x) {
        String s = x + "";
        boolean hasMinus = s.startsWith("-");
        if(hasMinus) {
            s = s.substring(1);
        }
        int len = s.length();
        StringBuilder sb = new StringBuilder();
        boolean canSave = false;
        for(int i = len - 1; i >= 0; i--) {
            if(canSave) {
                sb.append(s.charAt(i));
            } else {
                if(s.charAt(i) != 0) {
                    canSave = true;
                    sb.append(s.charAt(i));
                } else {
                    canSave = false;
                }
            }
        }
        try{
            return Integer.parseInt((hasMinus ? "-" : "") + sb.toString());
        } catch(Exception e) {
            return 0;
        }
    }
}
