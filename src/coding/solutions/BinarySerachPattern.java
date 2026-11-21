//34. Find First and Last Position of Element in Sorted Array
class Solution {
    public int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums,target), findLast(nums,target)};
    }

    private int findFirst(int[] nums,int target){
        int left=0,right=nums.length-1,result=-1;
        while(left<=right){
            int mid=left+(right-left)/2;

            if(nums[mid]==target){
                result=mid;
                right=mid-1; //search in left loop
            }else if(nums[mid]<target){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        return result;
    }
    private int findLast(int[] nums,int target){
        int left=0,right=nums.length-1,result=-1;
         while(left<=right){
            int mid=left+(right-left)/2;

            if(nums[mid]==target){
                result=mid;
                left=mid+1; //serach in right loop
            }else if(nums[mid]<target){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        return result;
    }
}
