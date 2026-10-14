#Leetcode 424 Longest Repeating Character Replacement

  Class Solution {
    public int characterReplacement(String s, int k) {
         int[] freq = new int[26]; // Frequency of each letter in current window
        int left = 0;
        int maxFreq = 0; // Count of the most frequent character in window
        int maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            // 1. EXPAND: Add current char to frequency map
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
            // 2. SHRINK: If replacements needed > k, window is invalid
            //    replacements needed = windowSize - maxFreq
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            // 3. RECORD: Window [left..right] is valid
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
        
    }
