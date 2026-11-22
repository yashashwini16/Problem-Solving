//33. Search in Rotated Sorted Array

public int search(int[] nums, int target) {
        int left=0,right=nums.length-1;

        while(left<=right){
            int mid=left+(right-left)/2;

            if(nums[mid]==target) return mid;

            if(nums[left]<=nums[mid]){
                if(target<nums[mid] && target>=nums[left]){
                    right=mid-1; //target is in left half
                }else{
                    left=mid+1; //target is in seond half
                }
            }else{
                if(target>nums[mid] && target<=nums[right]){
                    left=mid+1;
                }else{
                    right=mid-1;
                }
            }
        }
        return -1;
        
    }
