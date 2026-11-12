package week08;

public class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }

    static ListNode fromArray(int... vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : vals) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    // Builds the list from vals, then points the last node's next at the node at index pos.
    // pos -1 leaves the list alone (no cycle). Don't call show on a list with a cycle: it never ends.
    static ListNode fromArrayWithCycle(int[] vals, int pos) {
        ListNode head = fromArray(vals);
        if (pos < 0 || head == null) {
            return head;
        }
        ListNode join = head;
        for (int i = 0; i < pos; i++) {
            join = join.next;
        }
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = join;
        return head;
    }

    static String show(ListNode head) { // e.g. [1,2,3]
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = head; n != null; n = n.next) {
            sb.append(n.val).append(n.next != null ? "," : "");
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ListNode head = fromArray(1, 2, 3);
        System.out.println(show(head));
    }
}
