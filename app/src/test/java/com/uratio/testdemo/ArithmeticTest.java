package com.uratio.testdemo;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lang
 * @data 2023/5/4
 */
public class ArithmeticTest {

    /**
     * 3、无重复字符的最长子串
     * <p>
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * <p>
     * <p>
     * 提示：
     * <p>
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
     * <p>
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 示例 2：
     * <p>
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
     * <p>
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * <p>
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * <p>
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
     * 示例 2：
     * <p>
     * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
     * 输出：[1]
     * 解释：需要合并 [1] 和 [] 。
     * 合并结果是 [1] 。
     * 示例 3：
     * <p>
     * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
     * 输出：[1]
     * 解释：需要合并的数组是 [] 和 [1] 。
     * 合并结果是 [1] 。
     * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
     * <p>
     * <p>
     * 提示：
     * <p>
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     * <p>
     * <p>
     * 进阶：你可以设计实现一个时间复杂度为 O(m + n) 的算法解决此问题吗？
     */
    @Test
    public void mergeIntArray() {
//        int[] res = merge(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
        int[] res = merge(new int[]{2, 0}, 1, new int[]{1}, 1);
        System.out.println(Arrays.toString(res));
    }

    private int[] merge(int[] nums1, int m, int[] nums2, int n) {
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
}
