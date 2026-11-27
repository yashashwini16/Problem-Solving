//1283. Find the Smallest Divisor Given a Threshold

class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        int left=1,right=Arrays.stream(nums).max().getAsInt();

        while(left<right){
            int mid=left+(right-left)/2;

            if(canSum(nums,mid)<=threshold){
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return left;
        
    }
    private long canSum(int[] nums,int divisor){
        int sum=0;
        for(int num:nums){
            sum+=(num+divisor-1)/divisor;
        }
        return sum;
    }
}
