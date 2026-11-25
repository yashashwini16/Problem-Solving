//1011. Capacity To Ship Packages Within D Days
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int left=0,right=0;
        for(int w:weights){
            left=Math.max(left,w);  //heaviest iteam
            right+=w;              // max capacity =everything in 1 day
        }

        while(left<right){
             int mid=left+(right-left)/2;
            if(canFinish(weights,mid,days)){
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return left;
        
    }
    private boolean canFinish(int[] weights,int capacity,int days){
        int needed=1;
        int currentNeed=0;
        for(int w:weights){
            if(currentNeed+w > capacity){
                needed++;
                currentNeed=0;
            }
            currentNeed+=w;
            
        }
        return needed<=days;
    }
}
