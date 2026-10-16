#76. Minimum Window Substring

class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        // Frequency map of characters we NEED
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int required = need.size();  // Number of distinct chars to satisfy
        int formed = 0;              // How many distinct chars are currently satisfied
        // Frequency map of characters in current window
        Map<Character, Integer> windowFreq = new HashMap<>();
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0; // Start index of the best window
        for (int right = 0; right < s.length(); right++) {
            // 1. EXPAND: Add s[right] to window
            char c = s.charAt(right);
            windowFreq.put(c, windowFreq.getOrDefault(c, 0) + 1);
            // Check if this character's frequency is now fully satisfied
            if (need.containsKey(c) && windowFreq.get(c).intValue() == need.get(c).intValue()) {
                formed++;
            }
            // 2. SHRINK: While window is VALID, record answer and try to shrink
            while (formed == required) {
                // Record the answer
                int windowSize = right - left + 1;
                if (windowSize < minLen) {
                    minLen = windowSize;
                    minStart = left;
                }
                // Remove s[left] from window
                char leftChar = s.charAt(left);
                windowFreq.put(leftChar, windowFreq.get(leftChar) - 1);
                // Check if removing broke a satisfied character
                if (need.containsKey(leftChar)
                        && windowFreq.get(leftChar) < need.get(leftChar)) {
                    formed--;
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
        
    }
