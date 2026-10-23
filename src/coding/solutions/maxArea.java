//leetcode 11. Container With Most Water 
class Solution {
    public int maxArea(int[] height) {
        int left=0,right=height.length-1;
       int Sum=0;

        while(left<right){
            int sum=Math.min(height[left],height[right])*(right-left);
            Sum=Math.max(Sum,sum);
            if(height[left]<height[right]){
                left++;
            }else{
                right--;
            }
        }
        return Sum;
        
    }
}
