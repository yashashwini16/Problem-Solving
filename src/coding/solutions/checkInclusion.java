#567. Permutation in String

  class Solution {
    public boolean checkInclusion(String s1, String s2) {
          if (s1.length() > s2.length()) return false;
        int[] s1Freq = new int[26];
        int[] windowFreq = new int[26];
        // Build frequency map for s1
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }
        int windowSize = s1.length();
        for (int i = 0; i < s2.length(); i++) {
            // 1. EXPAND: Add the new character entering the window
            windowFreq[s2.charAt(i) - 'a']++;
            // 2. SHRINK: If window exceeds size, remove the leftmost character
            if (i >= windowSize) {
                windowFreq[s2.charAt(i - windowSize) - 'a']--;
            }
            // 3. CHECK: Do the frequencies match?
            if (Arrays.equals(s1Freq, windowFreq)) {
                return true;
            }
        }
        return false;
    }
        
    }
