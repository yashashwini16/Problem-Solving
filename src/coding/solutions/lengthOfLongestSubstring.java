#Longest Substring Without Repeating Characters

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // While we have a duplicate, shrink from the left
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }

            // Window is now guaranteed to have all unique characters
            set.add(c);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
