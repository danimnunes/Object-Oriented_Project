public class App {
    public static void main(String[] args) {
        Table tab = new Table(4);
        tab.setValue(0, 2);
        tab.setValue(1, 4);
        tab.setValue(2, 6);
        tab.setValue(3, 8);

        Iterator ite = tab.getIterator();
        while(ite.hasNext())
            System.out.println(ite.next());
    }
}