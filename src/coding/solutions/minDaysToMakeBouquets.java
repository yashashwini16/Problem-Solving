//1482. Minimum Number of Days to Make m Bouquets

lass Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        if ((long) m * k > bloomDay.length) return -1;  // Not enough flowers
        
        int left = 1, right = Arrays.stream(bloomDay).max().getAsInt();
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canMake(bloomDay, mid, m, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canMake(int[] bloomDay, int day, int m, int k) {
        int bouquets = 0, consecutive = 0;
        
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                consecutive++;             // This flower has bloomed!
                if (consecutive == k) {    // Got k adjacent flowers
                    bouquets++;
                    consecutive = 0;       // Reset for next bouquet
                }
            } else {
                consecutive = 0;           // Chain broken
            }
        }
        
        return bouquets >= m;
    }
}
