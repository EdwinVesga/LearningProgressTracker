import java.util.ArrayList;
import java.util.LinkedList;

class ListOperations {
    public static void changeHeadsAndTails(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        String head = linkedList.getFirst();
        String tail = linkedList.getLast();

        linkedList.set(0, arrayList.get(0));
        linkedList.set(linkedList.size() - 1, arrayList.get(arrayList.size() - 1));

        arrayList.set(0, head);
        arrayList.set(arrayList.size() - 1, tail);
    }
}