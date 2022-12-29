import java.util.TreeMap;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class Album {

    private TreeMap<Integer, Card> cards = new TreeMap<Integer, Card>();

    public void add(Card card) {
        cards.put(card.getID(), card);
    }

    public void remove(int id) {
        cards.remove(id);
    }

    public List<Card> getAll() {
        List<Card> lst = new LinkedList<Card>();
        lst.addAll(cards.values());
        Collections.sort(lst);
        return lst;

    }
}