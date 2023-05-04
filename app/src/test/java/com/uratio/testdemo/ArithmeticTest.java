package com.uratio.testdemo;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lang
 * @data 2023/5/4
 */
public class ArithmeticTest {

    /**
     无重复字符的最长子串

     给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。

     示例 1:

     输入: s = "abcabcbb"
     输出: 3
     解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     示例 2:

     输入: s = "bbbbb"
     输出: 1
     解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     示例 3:

     输入: s = "pwwkew"
     输出: 3
     解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。


     提示：

     0 <= s.length <= 5 * 104
     s 由英文字母、数字、符号和空格组成
     */
    @Test
    public void lengthOfLongestSubstring() {
        String str = "pwwkew";
        System.out.println(test31(str));
        System.out.println(test32(str));
    }

    /**
     * 用时最少
     */
    private int test31(String s) {
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
    private int test32(String s) {
        if(s == null || s.length() == 0) return 0;
        if(s.length() == 1) return 1;

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int left = 0;
        int result = 0;
        for(int i = 0; i < s.length(); i++) {
            if(map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);

            result = Math.max(result, i - left + 1);
        }

        return result;
    }
}
