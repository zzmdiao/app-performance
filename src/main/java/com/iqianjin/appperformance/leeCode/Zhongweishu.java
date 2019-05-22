package com.iqianjin.appperformance.leeCode;

import java.util.Arrays;

public class Zhongweishu {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1Lenth = nums1.length;
        int nums2Lenth = nums2.length;
        float result = 0;
        int[] nums3 = Arrays.copyOf(nums1, nums1Lenth + nums2Lenth);
        System.arraycopy(nums2, 0, nums3, nums1Lenth, nums2Lenth);
        System.out.println(Arrays.toString(nums3));
        if (nums3.length % 2 == 0) {
            result = (float) (nums3[nums3.length / 2 - 1] + nums1[nums3.length / 2]) / 2;
        } else if (nums3.length == 1) {
            result = nums3[0];
        } else if (nums1 == null || nums1.length == 0 && nums2.length % 2 == 0) {
            result = (float) (nums3[nums3.length / 2 - 1] + nums1[nums3.length / 2]) / 2;
        } else if (nums1 == null || nums1.length == 0 && nums2.length % 2 != 0) {
            result = (float) nums3[(nums3.length) / 2];
        } else {
            result = (float) nums3[(nums3.length + 1) / 2];
        }
        return result;
    }

    //数字反转，其数值范围为 [−2**31,  2**31 − 1]
    public int reverse(int x) {
        if (x == 0) {
            return x;
        }
        long temp = (long) x;
        String s = new StringBuffer(Math.abs(temp) + "").reverse().toString();

        if (x > 0) {
            long newValue = Long.parseLong(s);
            if (newValue > Integer.MAX_VALUE) {
                return 0;
            } else {
                return (int) newValue;
            }
        } else {
            System.out.println(s);
            long newValue = Long.parseLong(s);
            if (-newValue < Integer.MIN_VALUE) {
                return 0;
            } else {
                return -(int) newValue;
            }
        }
    }

    //字符串转换整数 (atoi)
    public int myAtoi(String str) {
        str = str.trim();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(i == 0){
                if(c == '-'|| c =='+'||(c >= '0' &&c <= '9')){
                    result.append(c);
                }else {
                    break;
                }
            }else {
                if(c >= '0' && c <='9'){
                    result.append(c);
                }else {
                    break;
                }
            }
        }
        if(result.toString().equals("+") || result.toString().equals("-") || result.toString().equals("")){
            return 0;
        }
        int res;
        try {
            res = Integer.parseInt(result.toString());
        }catch (NumberFormatException e){
            if(result.toString().startsWith("-")){
                return Integer.MIN_VALUE;
            }else {
                return Integer.MAX_VALUE;
            }
        }
        return res;
    }

    //回文数
    public boolean isPalindrome(int x) {
        String str1 = String.valueOf(x);
        String str2 = new StringBuffer(str1).reverse().toString();
        if(str1.equals(str2)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        Zhongweishu zhongweishu = new Zhongweishu();
//        int ab = -2147483648;
//        System.out.println(Math.abs(ab));
//        int[] nums1= {1,2};
//        int[] nums2= {3,4};
//        System.out.println(zhongweishu.findMedianSortedArrays(nums1,nums2));
//        System.out.println(zhongweishu.reverse(-2147483648));

//        System.out.println(zhongweishu.myAtoi("-4193234234223"));
        System.out.println(zhongweishu.isPalindrome(10));
    }
}
