public class Table implements Iterable {

    /**
     * Space for a fixed number of integers.
     */
    private int _vector[];

    private class TableIterator implements Iterator {
        private int i = 0;

        public boolean hasNext() {
            return i < _vector.length;
        }

        public int next() {
            return _vector[i++];
        }
    }

    public Iterator getIterator() {
        return new TableIterator();
    }

    /**
     * @param nInts
     *            number of integers to store.
     */
    public Table(int nInts) {
        _vector = new int[nInts];
    }

    /**
     * FIXME: insert checks to ensure position is within range.
     *
     * @param position
     *            position to define
     * @return value at position
     */
    public int getValue(int position) {
        return _vector[position];
    }

    /**
     * FIXME: insert checks to ensure position is within range.
     *
     * @param position
     *            position to define
     * @param value
     *            value to set
     */
    public void setValue(int position, int value) {
        _vector[position] = value;
    }

    /**
     * Set all positions to the same value.
     *
     * @param value
     *            value to set
     */
    public void setAll(int value) {
        for (int position = 0; position < _vector.length; position++)
            _vector[position] = value;
    }


}