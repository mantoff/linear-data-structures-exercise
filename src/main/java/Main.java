
import implementations.ArrayDeque;

public class Main {
    public static void main(String[] args) {

        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(13);
        deque.add(14);
        deque.add(15);
        deque.add(16);
        deque.add(17);
        System.out.println(deque.capacity());
    }
}
