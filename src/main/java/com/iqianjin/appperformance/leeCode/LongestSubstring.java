package com.iqianjin.appperformance.leeCode;

import java.util.HashSet;
import java.util.Set;

//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
public class LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<=n;j++){
                if(allUnique(s, i, j))
                    ans = Math.max(ans, j-i);
            }
        }
        return ans;
    }

    public boolean allUnique(String s, int start, int end){
        Set<Character> set = new HashSet<>();
        for(int i=start;i<end;i++){
            Character ch = s.charAt(i);
            if(set.contains(ch))
                return false;
            set.add(ch);
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "pwwkew";
        LongestSubstring longestSubstring = new LongestSubstring();
        longestSubstring.lengthOfLongestSubstring(s);
    }
}
