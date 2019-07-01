package com.iqianjin.appperformance.leeCode;

import java.util.HashMap;
import java.util.Map;

//给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
//你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
public class TwoSumA {

    //暴力破解
    public int[] twoSum1(int[] nums, int target){
        for(int i=0;i< nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]==target-nums[i]){
                    return new int[] {i,j};
                }
            }
        }
        throw new IllegalArgumentException("No solution");
    }

    //一遍hash
    //把数组放入map，key是数组每个元素，value是数组下标
    //如果complement在map中，说明当前数组i元素+complement等于target，即是结果
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
//            map.forEach((key, value) -> {
//                System.out.println("key="+key);
//                System.out.println("value="+value);
//            });
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        TwoSumA twoSumA = new TwoSumA();
        int[] nums = {2, 18, 11, 7, 15};
        int[] result = twoSumA.twoSum1(nums,9);
        System.out.println("数组下标是："+result[0]+"和"+result[1]);
        int[] result2 = twoSumA.twoSum2(nums,9);
        System.out.println("数组下标是："+result2[0]+"和"+result2[1]);
    }
}
