//234. Palindrome Linked List

class Solution {
    public boolean isPalindrome(ListNode head) {
        if(head==null && head.next==null) return true;

        ListNode fast=head,slow=head;
        while(fast!=null && fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        ListNode secondhalf=reverse(slow);

        ListNode firsthalf=head;
        while(secondhalf!=null){
            if(firsthalf.val!=secondhalf.val) return false;
            firsthalf=firsthalf.next;
            secondhalf=secondhalf.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head){
        ListNode prev=null,curr=head;
        while(curr!=null){
            ListNode temp=curr.next;
            curr.next=prev;
            prev=curr;
            curr=temp;
        }
        return prev;
    }
}
