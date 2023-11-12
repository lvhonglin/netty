package com.kunpeng.test.day7;

import java.util.HashMap;
import java.util.HashSet;

public class Test1 {
//    要求：给定一个字符串s，请你找出其中不含有重复字符的最长子串的长度。s="abc12rdsasbab23c"
//    注意：答题过程中不要切除当前页面，否则可能会导致断链，编写时注意编写规范。

    public int findLengthOfLongestSubString(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character,Integer> map = new HashMap<>();
        int max=0;
        int cur=0;
        for(int i=0;i<chars.length;i++){
            char charTmp=chars[i];
            if(map.containsKey(charTmp)){
                int left=i-cur;
                Integer index = map.get(charTmp);
                for(int j=left;j<=index;j++){
                    map.remove(chars[j]);
                    cur--;
                    if(chars[j]==chars[i]){
                        break;
                    }
                }
                map.put(charTmp,i);
                cur++;
            }else {
                cur++;
                map.put(charTmp,i);
            }
            max=Math.max(max,cur);
        }
        return max;
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        int baca = test1.findLengthOfLongestSubString("dadas1dad2fdfff");
        System.out.println(baca);
    }
}

