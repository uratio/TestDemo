package com.uratio.testdemo.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class Utils {

    private static final String TAG = "Utils_Log";

    public static void getAllAppNames(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        //PackageManager.GET_UNINSTALLED_PACKAGES==8192
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        //PackageManager.GET_SHARED_LIBRARY_FILES==1024
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //PackageManager.GET_META_DATA==128
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_META_DATA);
        //List<PackageInfo> list2=pm.getInstalledPackages(0);
        //List<PackageInfo> list2=pm.getInstalledPackages(-10);
        //List<PackageInfo> list2=pm.getInstalledPackages(10000);
        int j = 0;
        Log.i(TAG, "开始统计应用*******");
        for (PackageInfo packageInfo : list2) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(activity.getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName = packageInfo.packageName;
            Log.i(TAG, "应用:" + appName + " ::: " + packageName);

            j++;
        }
        Log.i(TAG, "应用的总个数:" + j);
    }

    /**
     * uninstall apk file
     *
     * @param packageName
     */
    public void uninstallAPK(Activity activity, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        activity.startActivity(intent);
    }

    public static String addLargeNumbers(String s1, String s2) {
        int l1 = s1.length() - 1;
        int l2 = s2.length() - 1;
        int maxL = Math.max(l1, l2);
        int[] sum = new int[maxL + 1];
        int carry = 0;
        while (l1 >= 0 || l2 >= 0) {
            int num1 = l1 >= 0 ? s1.charAt(l1--) - '0' : 0;
            int num2 = l2 >= 0 ? s2.charAt(l2--) - '0' : 0;
            int temp = num1 + num2 + carry;
            sum[maxL--] = temp % 10;
            carry = temp / 10;
        }
        StringBuilder sb = new StringBuilder();
        if (carry != 0) {
            sb.append(carry);
        }
        for (int i = 0; i < sum.length; i++) {
            sb.append(sum[i]);
        }
        return sb.toString();
    }

    public static String subtractLargeNumbers(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return "-" + subtract(s2, s1);
        } else if (s1.length() > s2.length()) {
            return subtract(s1, s2);
        } else {
            int compareTo = s1.compareTo(s2);
            if (compareTo == 0) {
                return "0";
            } else if (compareTo < 0) {
                return "-" + subtract(s2, s1);
            } else {
                return subtract(s1, s2);
            }
        }
    }

    private static String subtract(String s1, String s2) {
        int l1 = s1.length() - 1;
        int l2 = s2.length() - 1;
        int maxL = Math.max(l1, l2);
        int[] res = new int[maxL + 1];
        int borrow = 0;
        int k = maxL;
        while (l1 >= 0 || l2 >= 0) {
            int ten = 10;
            ten += l1 >= 0 ? s1.charAt(l1--) - '0' : 0;
            ten -= l2 >= 0 ? s2.charAt(l2--) - '0' : 0;
            ten -= borrow;
            res[k--] = ten % 10;
            borrow = ten / 10 == 0 ? 1 : 0;
        }
        int i = 0;
        if (i <= maxL && res[i] == 0) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = i; j <= maxL; j++) {
            sb.append(res[j]);
        }

        return sb.toString();
    }

    public static String multiplyLargeNumbers(String s1, String s2) {
        if ("0".equals(s1) || "0".equals(s2)) return "0";
        int l1 = s1.length();
        int l2 = s2.length();
        int maxL = l1 + l2;
        int[] res = new int[maxL];

        int i = 0, j = 0;
        for (i = l1 - 1; i >= 0; i--) {
            int carry = 0;
            for (j = l2 - 1; j >= 0; j--) {
                int num1 = s1.charAt(i) - '0';
                int num2 = s2.charAt(j) - '0';
                int temp = num1 * num2 + carry + res[i + j + 1];
                res[i + j + 1] = temp % 10;
                carry = temp / 10;
            }
            res[i + j + 1] = carry;
        }

        i = 0;
        if (i < maxL && res[i] == 0) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (; i < maxL; i++) {
            sb.append(res[i]);
        }

        return sb.toString();
    }

    public static boolean isExist(int[][] arr, int target) {
        if (arr == null || arr.length == 0 || arr[0] == null || arr[0].length == 0) return false;
        int i = 0, j = arr[0].length - 1;
        while (i < arr.length && j >= 0) {
            if (arr[i][j] < target) {
                i++;
            } else if (arr[i][j] > target) {
                j--;
            } else {
                return true;
            }
        }
        return false;
    }

    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) return null;
        TreeNode tree = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                tree.left = reConstructBinaryTree(copyOfRange(pre, 1, i + 1), copyOfRange(in, 0, i));
                tree.right = reConstructBinaryTree(copyOfRange(pre, i + 1, pre.length), copyOfRange(in,
                        i + 1, in.length));
                break;
            }
        }
        return tree;
    }

    public static int[] copyOfRange(int[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int[] copy = new int[newLength];
        System.arraycopy(original, from, copy, 0,
                Math.min(original.length - from, newLength));
        return copy;
    }
    /**
     *
     * @param head ListNode类
     * @param k int整型
     * @return ListNode类
     */
    public static ListNode reverseKGroup (ListNode head, int k) {
        // write code here
        if (head == null || head.next == null || k == 1) return head;
        int t = k - 1;
        ListNode p0 = null, p1 = head, p2 = head;
        while (p2 != null) {
            if (t != 0) {
                p2 = p2.next;
                t--;
            }
            if (t == 0 && p2 != null) {
                reverse(p1, p2);

                if (p0 == null) {
                    head = p2;
                } else {
                    p0.next = p2;
                }
                p0 = p1;
                p1 = p1.next;
                p2 = p1;
                t = k - 1;
            }
        }
        return head;
    }

    private static void reverse(ListNode head, ListNode tail) {
        ListNode p1 = head, p2 = head.next, p = head, last = tail.next;
        while (p2 != last && p2 != null) {
            p1.next = p2.next;
            p2.next = p;
            p = p2;
            p2 = p1.next;
        }
    }

    public static int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        if (end < 0) return 0;
        while (end >= 0 && s.charAt(end) == ' ') end--;
        int start = end;
        while (start >= 0 && s.charAt(start) != ' ') start--;
        return end - start;
    }

    public static int lengthOfLastWord2(String s) {
        String[] s1 = s.split(" ");
        if (s1 == null || s1.length < 1) return 0;
        return s1[s1.length - 1].length();
    }
}
