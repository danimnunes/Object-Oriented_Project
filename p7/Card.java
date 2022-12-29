public class Card implements Comparable<Card> {

    private int _id;
    private Image _image;

    public Card(int id, Image image) {
        _id = id;
        _image = image;
    }

    public int getID() {
        return _id;
    }

    @Override
    public int compareTo(Card o) {
        return _id - o.getID();
    }
}