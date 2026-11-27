//410. Split Array Largest Sum

class Solution {
    public int splitArray(int[] nums, int k) {
        int left=0,right=0;
        for(int num:nums){
            left=Math.max(left,num);
            right+=num;
        }

        while(left<right){
            int mid=left+(right-left)/2;
            if(canSplit(nums,mid,k)){
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return left;
    }
    private boolean canSplit(int[] nums,int mid,int k){
        int subArray=1, sum=0;
        for(int num:nums){
            if(sum+num>mid){
                subArray++;
                sum=0;
            }
            sum+=num;
        }
        return subArray<=k;
    }
}
