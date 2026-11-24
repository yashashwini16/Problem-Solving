//875. Koko Eating Bananas

class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left=1;
        int right=Arrays.stream(piles).max().getAsInt();

        while(left<right){
            int mid=left+(right-left)/2;

            if(canFinish(piles,mid,h)){
                right=mid;   //can Finish,try slower speed
            }else{
                left=mid+1; //cant finish.Eat faster
            }
        }
        return left;
        
    }

    private static boolean canFinish(int[] piles,int speed,int h){
        int hours=0;
        for(int pile:piles){
            hours+=(pile+speed-1)/speed;
        }
        return hours<=h;
    }
}
