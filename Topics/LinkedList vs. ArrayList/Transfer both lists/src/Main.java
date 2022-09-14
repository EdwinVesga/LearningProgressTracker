import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class ListOperations {
    public static void transferAllElements(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        List temp = linkedList.stream().toList();
        linkedList.clear();
        linkedList.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(temp);
    }
}