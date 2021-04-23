package com.ipermission.Algorithm;

import java.util.Arrays;
import java.util.TreeSet;

public class UniqueMossCode {
    public static void main(String[] args) {
        String[] words = {"gin", "zen", "gig", "msg"};
        int i = new Solution().uniqueMorseRepresentations(words);
        System.out.println(i);
    }




}
class Solution {

    public int uniqueMorseRepresentations(String[] words) {
        String[] mossCodeDict = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        TreeSet<String> mossCodeSet = new TreeSet<>();
        for (String word:words) {
            StringBuilder mossCodeBuffer = new StringBuilder();
            for (int i = 0; i<word.length(); i++){
                String mossCode = mossCodeDict[word.charAt(i)-'a'];
                mossCodeBuffer.append(mossCode);
            }
            mossCodeSet.add(mossCodeBuffer.toString());
        }
        return mossCodeSet.size();
    }
}